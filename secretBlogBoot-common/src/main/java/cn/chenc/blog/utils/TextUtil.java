package cn.chenc.blog.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * 　@description: 文本转化工具
 * 　@author 陈_C
 * 　@date 2020/5/17 15:55
 *
 */
public class TextUtil {

    /**
     * @description: markdown转html
     * @param [md]
     * @return java.lang.String
     * @throws
     * @author 陈_C
     * @date 2020/5/17 陈_C
     */
    public static String mdToHtml(String md) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(md);
        String html = renderer.render(document);
        return html;
    }

    /**
     * @description: html转普通文本
     * @param [html]
     * @return java.lang.String
     * @throws
     * @author 陈_C
     * @date 2020/5/17 陈_C
     */
    public static String htmlToText(String html) {
        if (StringUtils.isEmpty(html)) {
            return "";
        }
        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");
        String newHtml = document.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim());
        return result;
    }

    /**
     * @description: md转text
     * @param [md]
     * @return java.lang.String
     * @throws
     * @author 陈_C
     * @date 2020/5/17 陈_C
     */
    public static String mdToText(String md){
        String html=mdToHtml(md);
        String text=htmlToText(html);
        return text;
    }


}
