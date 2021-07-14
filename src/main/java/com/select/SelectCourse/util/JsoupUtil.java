package com.select.SelectCourse.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class JsoupUtil {

    private static Document getDocument(String u) throws IOException {
        URL url = new URL(u);
        Document document = Jsoup.parse(url, 30000);
        return document;
    }

    public String getDLUTNews() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<br />");

        Document document = getDocument("http://news.dlut.edu.cn/dgyw/dgyw.htm");
        Elements elements = document.getElementsByClass("itemlist");
        //进行处理
        for (Element element : elements) {
            String html = element.select("div[class=txt]").html();
            int index = html.indexOf("..");
            stringBuilder.append(html, 0, index);
            stringBuilder.append("http://news.dlut.edu.cn");
            stringBuilder.append(html.substring(index + 2));
            stringBuilder.append(element.select("div[class=cb]"));
            stringBuilder.append("<hr /> ");
        }
        return stringBuilder.toString();
    }

}
