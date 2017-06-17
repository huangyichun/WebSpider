package proxy;

import org.apache.http.HttpHost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单代理线程池，通过随机数随机获取代理服务器
 */
public class SimpleProxyPool implements ProxyPool{

    private List<Proxy> proxyList = new ArrayList<>();
    private final int size;

    public SimpleProxyPool() {
        init();
        size = proxyList.size();
    }

    public void init()  {
        InputStream is = getClass().getResourceAsStream("/proxy.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader buffReader = new BufferedReader(streamReader);
        String line;
        try {
            while((line = buffReader.readLine()) != null) {
                String host = line.split(":")[0];
                String port = line.split(":")[1];
                HttpHost httpHost = new HttpHost(host, Integer.parseInt(port));
                Proxy proxy = new Proxy(httpHost);
                proxyList.add(proxy);
            }
        } catch (IOException e) {
            System.out.println("未设置正确的代理文件");
            e.printStackTrace();
        }
    }

    @Override
    public Proxy getProxy() {
        int index = (int) (Math.random() * size);
        return proxyList.get(index);
    }

    @Override
    public boolean isEnable() {
        return true;
    }
}
