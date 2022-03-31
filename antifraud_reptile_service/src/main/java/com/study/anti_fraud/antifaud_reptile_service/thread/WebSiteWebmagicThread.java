package com.study.anti_fraud.antifaud_reptile_service.thread;

import com.study.anti_fraud.antifaud_reptile_service.pipeline.ReptilePipeline;
import com.study.anti_fraud.antifaud_reptile_service.processor.RefereeProcessor;
import com.study.anti_fraud.antifaud_reptile_service.untils.SpringContextUtil;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;

public class WebSiteWebmagicThread implements Runnable {

    private static final String chromeDriverPath = "E:\\organizational_process_assets\\reptile\\chromedriver_win32\\chromedriver.exe";
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

    public WebSiteWebmagicThread(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }
    @Override
    public void run() {
        RefereeProcessor refereeProcessor = (RefereeProcessor) SpringContextUtil.getBean(RefereeProcessor.class);
        ReptilePipeline reptilePipeline = (ReptilePipeline) SpringContextUtil.getBean(ReptilePipeline.class);

        Request request = new Request(webSiteUrl);
        Spider.create(refereeProcessor)
                // 从https://www.zhihu.com/explore开始抓
                //.addUrl("https://wenshu.court.gov.cn/website/wenshu/181217BMTKHNT2W0/index.html?pageId=7c7444636fd485e9c5ed46aa96ae87c8&s8=02")
                .addRequest(request)
                // 抓取到的数据存数据库
                .addPipeline(reptilePipeline)
                // 开启2个线程抓取
//                .thread(1)
                .setDownloader(
                        new SeleniumDownloader(chromeDriverPath)
                )
                // 异步启动爬虫
                .start();
    }
}
