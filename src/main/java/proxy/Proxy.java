package proxy;

import org.apache.http.HttpHost;

/**
 * 代理类
 */
public class Proxy {

    private final HttpHost httpHost; //代理服务器
    private String userName; //用户名
    private String password; //密码

    public Proxy(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    public Proxy(HttpHost httpHost, String userName, String password) {
        this.httpHost = httpHost;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "httpHost=" + httpHost +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
