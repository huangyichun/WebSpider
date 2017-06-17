package processor;

import domain.*;
import download.Downloader;
import download.HttpClientGetDownloader;
import org.junit.Before;
import org.junit.Test;
import selector.InformationSelector;

import java.util.List;

/**
 * InformationPageProcessor测试
 */
public class InformationPageProcessorTest {

    private Site site;
    private String helpUrlPattern;
    private String targetUrlPattern;
    private InformationSelector selector;

    @Before
    public void init() {
        site = new Site("http://cs.scu.edu.cn", "GBK");
        helpUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/H9501index.+?htm";
        targetUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/webinfo.+?htm";
        selector = new InformationSelector();
        selector.setTitleSelect("width=721 height=27 a> <DIV align=center>(.+?)</DIV> <DIV align=center>");
        selector.setCollegeSelect("valign=\"bottom\">来源： </SPAN>(.+?)<SPAN class=hangjc");
        selector.setTimeSelect("valign=\"bottom\">时间： </SPAN>(.+?)<SPAN");
        selector.setPicSeclect("src=\"(/cs/rootimages.+?jpg)\"");
        selector.setContentSelect("#BodyLabel");
    }

    @Test
    public void process() throws Exception {
        InformationPageProcessor pageProcessor = new InformationPageProcessor(site,
                helpUrlPattern, targetUrlPattern, selector);
        Downloader downloader = new HttpClientGetDownloader();
        Request request = new Request("http://cs.scu.edu.cn/cs/xyxw/webinfo/2017/06/1496156934396967.htm");
        Page page = downloader.download(request, site);
        pageProcessor.process(page);

        ResultItems resultItems = page.getResultItems();
        System.out.println(resultItems);

        List<Request> requests = page.getTargetRequests();
        requests.forEach(System.out::println);

        List<Request> helpRequest = page.getHelpRequest();
        helpRequest.forEach(System.out::println);
    }

}