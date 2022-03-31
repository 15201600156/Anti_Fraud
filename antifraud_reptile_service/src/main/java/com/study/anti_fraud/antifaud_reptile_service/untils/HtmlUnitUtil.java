package com.study.anti_fraud.antifaud_reptile_service.untils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnitUtil {

    public static WebClient createWeb() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);// 支持https
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(50000);
        // webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        // webClient.getOptions().setUseInsecureSSL(true);// 接受任何主机连接 无论是否有有效证书
        return webClient;
    }

    public static void closeWeb(WebClient webClient) {
        webClient.getCurrentWindow().getJobManager().removeAllJobs();
        webClient.close();
    }
}
