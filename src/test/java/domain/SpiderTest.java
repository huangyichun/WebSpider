package domain;

import processor.InformationPageProcessor;
import processor.PageProcessor;
import selector.InformationSelector;

/**
 * Spider测试
 * Created by huangyichun on 2017/6/17.
 */
public class SpiderTest {

    public static void main(String[] args) {
        Site site = new Site("http://cs.scu.edu.cn", "GBK");
        String helpUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/H9501index.+?htm";
        String targetUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/webinfo.+?htm";

        InformationSelector selector = new InformationSelector();
        selector.setTitleSelect("width=721 height=27 a> <DIV align=center>(.+?)</DIV> <DIV align=center>")
                .setCollegeSelect("valign=\"bottom\">来源： </SPAN>(.+?)<SPAN class=hangjc")
                .setTimeSelect("valign=\"bottom\">时间： </SPAN>(.+?)<SPAN")
                .setPicSeclect("src=\"(/cs/rootimages.+?jpg)\"")
                .setContentSelect("#BodyLabel");

        Request firstRequest = new Request("http://cs.scu.edu.cn/cs/xyxw/H9501index_1.htm");
        PageProcessor process = new InformationPageProcessor(site,
                helpUrlPattern, targetUrlPattern, selector);

        Spider spider = new Spider(site, firstRequest, process);
        spider.run();
    }
}