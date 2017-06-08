import org.apache.http.HttpHost;

/**
 * 代理类
 */
public class Proxy {

    private final HttpHost httpHost;
    private String userName;
    private String password;

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

    @Override
    public String toString() {
        return "Proxy{" +
                "httpHost=" + httpHost +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
