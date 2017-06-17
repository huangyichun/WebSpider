import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 * 爬虫测试
 */
public class HttpClientTest {

    @Test
    public void test_2() throws IOException {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpHost httpHost = new HttpHost("188.128.1.37", 8080);
        httpClientBuilder.setProxy(httpHost);
//      CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//      credentialsProvider.setCredentials(new AuthScope(httpHost), new UsernamePasswordCredentials("",""));
//      httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

        CloseableHttpClient httpClient = httpClientBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.get();
        HttpUriRequest request = requestBuilder.setCharset(Charset.forName("GBK"))
                .setUri("http://cs.scu.edu.cn/cs/xyxw/H9501index_1.htm").build();

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        System.out.println(response.getStatusLine().getStatusCode());
        String html = "";
        if (entity != null) {
            // 打印响应内容长度
            System.out.println("Response content length: " + entity.getContentLength());
            // 打印响应内容
            html = EntityUtils.toString(entity, "GBK");
            Document document = Jsoup.parse(html);
            document.setBaseUri("http://cs.scu.edu.cn");
            Elements elements = document.select("a");
            elements.forEach(element -> System.out.println(element.attr("abs:href")));

        }

        response.close();
        httpClient.close();
    }

    @Test
    public void test_1() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.xicidaili.com/free2016.txt");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();
        System.out.println(response.getStatusLine());
        if (entity != null) {
            // 打印响应内容长度
            System.out.println("Response content length: " + entity.getContentLength());
            // 打印响应内容
            System.out.println("Response content: " + EntityUtils.toString(entity));
        }

        response.close();
        httpClient.close();
//        httpClient.execute("http://api.xicidaili.com/free2016.txt",)
    }
}
