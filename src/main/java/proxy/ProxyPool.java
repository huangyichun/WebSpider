package proxy;

/**
 * 代理池
 */
public interface ProxyPool {

    Proxy getProxy();
    boolean isEnable();
}
