package processor;

import domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 新闻通知页面解析器
 */
public class InformationPageProcessor implements PageProcessor {

    private Site site;
    private String helpUrlPattern;
    private String targetUrlPattern;
    private InformationSelector selector;

    public InformationPageProcessor(Site site, String helpUrlPattern,
                                    String targetUrlPattern, InformationSelector selector) {
        this.site = site;
        this.helpUrlPattern = helpUrlPattern;
        this.targetUrlPattern = targetUrlPattern;
        this.selector = selector;
    }

    @Override
    public void process(Page page) {
        Request request = page.getRequest(); //当前页面的请求url
        List<Request> targetRequests = page.getTargetRequests();
        List<Request> helpRequests = page.getHelpRequest();
        Document document = Jsoup.parse(page.getHtml());
        document.setBaseUri(site.getDomain());
        if(request.getUrl().matches(targetUrlPattern)) {
            ResultItems resultItems = page.getResultItems();
            Map<String,Object> map = resultItems.getFields();
            if(selector.getTitleSelect() != null) {
                String title = select(page.getHtml(), selector.getTitleSelect());
                map.put("title", title);
            }
            if(selector.getTimeSelect() != null) {
                String time = select(page.getHtml(), selector.getTitleSelect());
                map.put("time", time);
            }
            if (selector.getPicSeclect() != null) {
                String pic = select(page.getHtml(), selector.getPicSeclect());
                map.put("pic", pic);
            }
            if (selector.getCollegeSelect() != null){
                String college = select(page.getHtml(), selector.getCollegeSelect());
                map.put("college", college);
            }
            if (selector.getContentSelect() != null) {
                String content = document.getElementById(selector.getContentSelect()).html();
                map.put("content", content);
            }

            map.put("url", request.getUrl());

        }else if (request.getUrl().matches(helpUrlPattern)) {//添加目标url
            Elements elements = document.select("a");
            elements.forEach(element -> {
                String url = element.attr("abs:href");
                if (url.matches(targetUrlPattern)) {
                    targetRequests.add(new Request(url));
                }else if(url.matches(helpUrlPattern)){
                    helpRequests.add(new Request(url));
                }
            });
        }
    }

    /**
     * 根据正则表达式获取相关信息
     * @param html
     * @param regex
     * @return
     */
    private String select(String html, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }else {
            throw new RuntimeException("正则表达式有误");
        }
    }

    public String getHelpUrlPattern() {
        return helpUrlPattern;
    }

    public void setHelpUrlPattern(String helpUrlPattern) {
        this.helpUrlPattern = helpUrlPattern;
    }

    public String getTargetUrlPattern() {
        return targetUrlPattern;
    }

    public void setTargetUrlPattern(String targetUrlPattern) {
        this.targetUrlPattern = targetUrlPattern;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
