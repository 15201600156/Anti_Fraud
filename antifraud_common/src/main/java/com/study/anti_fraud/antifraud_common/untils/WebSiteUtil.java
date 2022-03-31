package com.study.anti_fraud.antifraud_common.untils;

public class WebSiteUtil {
    private static final String URL_FIRST = "http://";

    public static String getWebSiteUrl(String webSiteUrl) {
        if (webSiteUrl.toUpperCase().contains("HTTP://") || webSiteUrl.toUpperCase().contains("HTTPS://")) {
            return webSiteUrl;
        } else {
            return  URL_FIRST + webSiteUrl;
        }
    }

    /**
     * 检查是否为404页面
     */
    public static boolean check404(String result) {
        if(result.contains("<title>")&&result.contains("</title>")) {
            String str =result.substring(result.indexOf("<title>"), result.indexOf("</title>"));
            if(str.contains("404")||str.contains("403")||str.contains("Rejected")||str.contains("Apache Tomcat")
                    ||str.contains("不存在")||str.contains("服务器拒绝")||str.contains("服务器已拒绝")||str.contains("找不到")
                    ||str.contains("网站访问报错")||str.contains("未生效")||str.contains("建设中")||str.contains("HostGator")||str.contains("没有找到")){
                return true;
            }
        }
        return false;
    }
}
