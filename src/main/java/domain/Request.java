package domain;

/**
 * 请求URL封装
 */
public class Request {

    private String url;
    private String method;

    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return url.equals(request.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
