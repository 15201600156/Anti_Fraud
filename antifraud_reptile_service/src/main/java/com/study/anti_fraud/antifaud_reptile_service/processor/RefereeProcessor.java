package com.study.anti_fraud.antifaud_reptile_service.processor;

import cn.hutool.core.collection.CollectionUtil;
import com.study.anti_fraud.antifaud_reptile_service.entity.WebSiteInfo;
import com.study.anti_fraud.antifaud_reptile_service.repository.WebSiteInfoRepository;
import com.study.anti_fraud.antifaud_reptile_service.untils.SnowflakeComponentUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.SpringContextUtil;
import com.study.anti_fraud.antifaud_reptile_service.untils.XPathUtil;
import javafx.beans.value.WritableListValue;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;

@Component
public class RefereeProcessor implements PageProcessor {
    //   Set<Integer> acceptStatusCode = new HashSet<>(Arrays.asList(200, 202, 401, 404, 406, 500, 301, 302, 303, 521));
//    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000).setAcceptStatCode(acceptStatusCode);


    private Site site = Site.me().setRetryTimes(1).setSleepTime(10000);
    //获取文本时的拼接
    private final static String TEXT = "//text()";

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {

//        JXDocument document = JXDocument.create(page.getHtml().getDocument());
//        String str = document.selNOne("//*[@id=\"mohe-mobilecheck\"]/div/div[1]/table/tbody/tr/td[2]/div[2]").asElement().text();

        String currentElementTextByRegex = XPathUtil.getCurrentElementTextByRegex(page.getHtml(), "//*[@id=\"mohe-mobilecheck\"]/div/div[1]/table/tbody/tr/td[2]/div[2]/allText()");
        System.out.println(currentElementTextByRegex);
//        List<Selectable> nodes = page.getHtml().xpath("//*[@class=\"result c-container xpath-log new-pmd\"]").nodes();
//
//        if (CollectionUtil.isNotEmpty(nodes)) {
//            List<WebSiteInfo> list = new ArrayList<>();
//            nodes.forEach(w -> {
//                WebSiteInfo webSiteInfo = new WebSiteInfo();
//                webSiteInfo.setId(SpringContextUtil.getBean(SnowflakeComponentUtil.class).getInstance().nextId());
//                String title = w.xpath("//*[@class=\"c-title t t tts-title\"]/a" + TEXT).toString();
//                webSiteInfo.setTitle(title);
//                String link = w.xpath("//*[@class=\"c-title t t tts-title\"]/a").links().get().toString();
//                webSiteInfo.setHref(link);
//                String description = w.xpath("//*[@class=\"content-right_8Zs40\"]" + TEXT).toString();
//                webSiteInfo.setDescription(description);
//                list.add(webSiteInfo);
//            });
//            if (CollectionUtil.isNotEmpty(list))
//                page.putField("website", list);
////        if (page.getResultItems().get("title") == null) {
////            // 如果是列表页，跳过此页，pipeline不进行后续处理
////            page.setSkip(true);
////        }
//        }


    }

    @Override
    public Site getSite() {
        return site;
    }
}
