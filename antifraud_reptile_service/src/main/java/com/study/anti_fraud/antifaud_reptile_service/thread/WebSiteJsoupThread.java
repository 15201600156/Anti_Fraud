package com.study.anti_fraud.antifaud_reptile_service.thread;

import cn.hutool.core.collection.CollectionUtil;
import com.study.anti_fraud.antifaud_reptile_service.config.KafkaProducer;
import com.study.anti_fraud.antifaud_reptile_service.entity.WebSiteInfo;
import com.study.anti_fraud.antifaud_reptile_service.repository.WebSiteInfoRepository;
import com.study.anti_fraud.antifaud_reptile_service.untils.SnowflakeComponentUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.SpringContextUtil;
import com.study.anti_fraud.antifraud_common.untils.PhantomTools;
import com.study.anti_fraud.antifraud_common.untils.WebSiteUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.XPathUtil;
import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyf
 */
@Slf4j
public class WebSiteJsoupThread implements Runnable {
    /**
     * 5s超时
     */
    private static final int TIMEOUT = 5000;
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    private String webSiteUrl;

    public WebSiteJsoupThread(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(webSiteUrl).timeout(TIMEOUT).get();

            boolean flag = WebSiteUtil.check404(document.html());
            if (!flag) {
                JXDocument root = JXDocument.create(document);
                List<JXNode> nodes = XPathUtil.getCurrentElementsHtmlByRegex(root,"//*[@class=\"result c-container xpath-log new-pmd\"]");
                if (CollectionUtil.isNotEmpty(nodes)) {
                    List<WebSiteInfo> webSiteInfos = new ArrayList<>();
                    nodes.forEach(node -> {
                        WebSiteInfo webSiteInfo = new WebSiteInfo();
                        webSiteInfo.setId(SpringContextUtil.getBean(SnowflakeComponentUtil.class).getInstance().nextId());
                        String title = XPathUtil.getCurrentElementTextByRegex(node,"//*[@class=\"c-title t t tts-title\"]/a");
                        webSiteInfo.setTitle(title);

                        String href =XPathUtil.getCurrentElementHrefByRegex(node,"//*[@class='c-title t t tts-title']/a");
                        webSiteInfo.setHref(href);

                        String description = XPathUtil.getCurrentElementTextByRegex(node,"//*[@class=\"content-right_8Zs40\"]");
                        webSiteInfo.setDescription(description);


                        log.info(webSiteInfo.toString());

                        webSiteInfos.add(webSiteInfo);
                    });
                    WebSiteInfoRepository webSiteInfoRepository = SpringContextUtil.getBean(WebSiteInfoRepository.class);
                    webSiteInfoRepository.saveAll(webSiteInfos);
                    PhantomTools.printUrlScreen2jpg(webSiteUrl);
                } else {
                    log.info("采用Jsoup没有抓取到，放入消息队列当中，采用HTMLUnit进行抓取");
                    KafkaProducer kafkaProducer = SpringContextUtil.getBean(KafkaProducer.class);
                    kafkaProducer.send(KafkaConstant.WEBSITE_HTMLUNIT_TOPIC, this.webSiteUrl);
                }

            } else {
                log.info("相当于死链");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("网站地址：" + webSiteUrl + ",出错了");
        }
    }
}
