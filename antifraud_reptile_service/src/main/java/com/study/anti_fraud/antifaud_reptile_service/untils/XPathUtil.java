package com.study.anti_fraud.antifaud_reptile_service.untils;

import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.List;

public class XPathUtil {


    public static final String HREF = "href";

    /**
     * 获取当前HTML节点下的标签的文本内容（仅限xpath匹配的标签的文本值，不包含标签内套标签的文本值）
     * @param jxNode
     * @param regex
     * @return
     */
    public static String getCurrentElementTextOnlyElementByRegex(JXNode jxNode, String regex) {
        return jxNode.selOne(regex).asElement().text();
    }

    /**
     * 获取当前HTML节点下的所有文本内容
     *
     * @param jxNode
     * @param regex
     * @return
     */
    public static String getCurrentElementTextByRegex(JXNode jxNode, String regex) {
        return jxNode.selOne(regex).asElement().text();
    }

    /**
     * 获取当前HTML节点下的a的链接地址
     *
     * @param jxNode
     * @param regex
     * @return
     */
    public static String getCurrentElementHrefByRegex(JXNode jxNode, String regex) {
        return jxNode.selOne(regex).asElement().attr(HREF);
    }


    public static List<JXNode>  getCurrentElementsHtmlByRegex(JXDocument jxDocument, String regex) {
        return jxDocument.selN(regex);
    }

}
