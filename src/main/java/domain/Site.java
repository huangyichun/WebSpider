package domain;

import proxy.ProxyPool;

import java.util.*;

/**
 * 主页信息
 */
public class Site {

    private final String DEFAULT_CAHRSET = "UTF-8";

    private String domain;//网站主页

    private String userAgent;

    private ProxyPool proxyPool;

    public ProxyPool getProxyPool() {
        return proxyPool;
    }

    public void setProxyPool(ProxyPool proxyPool) {
        this.proxyPool = proxyPool;
    }

    private Map<String, Map<String,String>> cookies = new HashMap<>();

    private List<Request> startRequests = new ArrayList<Request>();

    private Map<String, String> headers = new HashMap<String, String>();

    private String charset = "UTF-8"; //网站编码

    private int timeOut = 5000;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public List<Request> getStartRequests() {
        return startRequests;
    }

    public void setStartRequests(List<Request> startRequests) {
        this.startRequests = startRequests;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Site(String domain, String charset) {
        this.domain = domain;
        this.charset = charset;
    }
    //    private int cycleRetryTimes = 0;

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Map<String, Map<String, String>> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, Map<String, String>> cookies) {
        this.cookies = cookies;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
