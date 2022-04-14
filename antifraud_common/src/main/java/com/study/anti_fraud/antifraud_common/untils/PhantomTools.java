package com.study.anti_fraud.antifraud_common.untils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class PhantomTools {
    private static String tempPath = "D:/home/";// 图片保存目录
    private static String BLANK = " ";
    // 下面内容可以在配置文件中配置
    private static String binPath = "E:\\organizational_process_assets\\Anti_Fraud\\assert\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";// 插件引入地址
    private static String jsPath = "E:\\organizational_process_assets\\Anti_Fraud\\assert\\phantomjs-2.1.1-windows\\bin\\rasterize.js";// js引入地址

    // 执行cmd命令
    public static String cmd(String imgagePath, String url) {
        return binPath + BLANK + jsPath + BLANK + url + BLANK + imgagePath;
    }

    //关闭命令
    public static void close(Process process, BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (process != null) {
            process.destroy();
            process = null;
        }
    }

    /**
     * @param url
     * @throws IOException
     */
    public static void printUrlScreen2jpg(String url) throws IOException, InterruptedException {
        try {
            String imgagePath = tempPath + "/" + System.currentTimeMillis() + ".png";//图片路径
            //Java中使用Runtime和Process类运行外部程序
            Process process = Runtime.getRuntime().exec(cmd(imgagePath, url));
            InputStream inputStream = process.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sbf = new StringBuffer();
            String line="";
            while ((line = reader.readLine()) != null) {
                sbf.append(line);
            }
            close(process, reader);
            System.out.println("success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

        }

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://www.cnblogs.com/it1042290135/p/15883131.html";
        PhantomTools.printUrlScreen2jpg(url);

    }
}
