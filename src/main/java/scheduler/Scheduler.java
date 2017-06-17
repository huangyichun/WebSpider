package scheduler;

import domain.Request;

/**
 * Created by huangyichun on 2017/6/15.
 */
public interface Scheduler {

    /**
     * add a url to fetch
     * @param request request
     */
    public void push(Request request);

    /**
     * get an url to crawl
     * @return the url to crawl
     */
    public Request poll();
}
