package domain;

import download.Downloader;
import download.HttpClientGetDownloader;
import org.junit.Before;
import org.junit.Test;
import pipeline.ConsolePipeline;
import pipeline.Pipeline;
import processor.InformationPageProcessor;
import processor.PageProcessor;
import scheduler.DuplicateRemovedScheduler;
import scheduler.Scheduler;
import selector.InformationSelector;

import static org.junit.Assert.*;

/**
 * Spider测试
 * Created by huangyichun on 2017/6/17.
 */
public class SpiderTest {

    private Site site;
    private Request firstRequest;
    private Downloader downloader;
    private PageProcessor process;
    private Pipeline pipeline;
    private Scheduler scheduler;
    private String helpUrlPattern;
    private String targetUrlPattern;
    private InformationSelector selector;

    @Before
    public void initData() {
        site = new Site("http://cs.scu.edu.cn", "GBK");
        helpUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/H9501index.+?htm";
        targetUrlPattern = "http://cs.scu.edu.cn/cs/xyxw/webinfo.+?htm";
        selector = new InformationSelector();
        selector.setTitleSelect("width=721 height=27 a> <DIV align=center>(.+?)</DIV> <DIV align=center>");
        selector.setCollegeSelect("valign=\"bottom\">来源： </SPAN>(.+?)<SPAN class=hangjc");
        selector.setTimeSelect("valign=\"bottom\">时间： </SPAN>(.+?)<SPAN");
        selector.setPicSeclect("src=\"(/cs/rootimages.+?jpg)\"");
        selector.setContentSelect("#BodyLabel");
        firstRequest = new Request("http://cs.scu.edu.cn/cs/xyxw/H9501index_1.htm");
        downloader = new HttpClientGetDownloader();
        process = new InformationPageProcessor(site,
                helpUrlPattern, targetUrlPattern, selector);
        pipeline = new ConsolePipeline();
        scheduler = new DuplicateRemovedScheduler();
    }

    @Test
    public void run() throws Exception {

        Spider spider = new Spider(site, firstRequest, downloader,
                process, pipeline, scheduler);
        spider.run();
    }

}