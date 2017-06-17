package download;

import domain.Page;
import domain.Request;
import domain.Site;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 实现DownLoader
 */
public class HttpClientGetDownloader implements Downloader {

    private final int SUCCESS_CODE = 200;

    /**
     * 下载网页存储到Page中
     *
     * @param request request
     * @return
     */
    @Override
    public Page download(Request request, Site site) {
        String charset = null;
        if (site == null) {
            charset = "UTF-8";
        } else {
            charset = site.getCharset();
        }

        RequestBuilder requestBuilder = RequestBuilder.get();
        HttpUriRequest httpUriRequest = requestBuilder.setCharset(Charset.forName(charset))
                .setUri(request.getUrl())
                .build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        Page page = null;
        try {
            response = httpClient.execute(httpUriRequest);
            if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                HttpEntity entity = response.getEntity();
                String html = EntityUtils.toString(entity, charset);
                page = new Page(request,html);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return page;
    }
}
