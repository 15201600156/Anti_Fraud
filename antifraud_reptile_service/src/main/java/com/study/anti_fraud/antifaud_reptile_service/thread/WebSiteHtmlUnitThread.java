package com.study.anti_fraud.antifaud_reptile_service.thread;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.study.anti_fraud.antifaud_reptile_service.config.KafkaProducer;
import com.study.anti_fraud.antifaud_reptile_service.entity.WebSiteInfo;
import com.study.anti_fraud.antifaud_reptile_service.repository.WebSiteInfoRepository;
import com.study.anti_fraud.antifaud_reptile_service.untils.HtmlUnitUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.SnowflakeComponentUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.SpringContextUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.XPathUtil;
import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import com.study.anti_fraud.antifraud_common.untils.PhantomTools;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyf
 */
@Slf4j
public class WebSiteHtmlUnitThread implements Runnable {


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

    public WebSiteHtmlUnitThread(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    @Override
    public void run() {
        WebClient web = HtmlUnitUtil.createWeb();
        try {
            HtmlPage page = web.getPage(webSiteUrl);
            String html = page.asXml();
            JXDocument root = JXDocument.create(Jsoup.parse(html));
            List<JXNode> nodes = XPathUtil.getCurrentElementsHtmlByRegex(root,"//*[@class=\"result c-container xpath-log new-pmd\"]");
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
        } catch (Exception e) {
            e.printStackTrace();
            KafkaProducer kafkaProducer = SpringContextUtil.getBean(KafkaProducer.class);
            kafkaProducer.send(KafkaConstant.WEBSITE_WEBMAGIC_TOPIC, this.webSiteUrl);
            log.info("网站地址：" + webSiteUrl + ",出错了");
        } finally {
            HtmlUnitUtil.closeWeb(web);
        }
    }
}
