package proxy;

import org.junit.Test;

/**
 * Created by huangyichun on 2017/6/12.
 */
public class TestSimpleProxyPool {

    @Test
    public void test(){
        SimpleProxyPool simpleProxyPool = SimpleProxyPool.getInstance();
        Proxy proxy = simpleProxyPool.getProxy();
        System.out.println(proxy);
    }
}
