package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载的页面
 */
public class Page {

    private Request request;

    private ResultItems resultItems = new ResultItems();

    private List<Request> targetRequests = new ArrayList<>();

    private List<Request> helpRequest = new ArrayList<>();

    private String html;

    public Page(Request request, String html) {
        this.request = request;
        this.html = html;
    }

    public List<Request> getHelpRequest() {
        return helpRequest;
    }

    public void setHelpRequest(List<Request> helpRequest) {
        this.helpRequest = helpRequest;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public ResultItems getResultItems() {
        return resultItems;
    }

    public void setResultItems(ResultItems resultItems) {
        this.resultItems = resultItems;
    }

    public List<Request> getTargetRequests() {
        return targetRequests;
    }

    public void setTargetRequests(List<Request> targetRequests) {
        this.targetRequests = targetRequests;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
