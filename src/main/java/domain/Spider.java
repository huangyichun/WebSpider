package domain;

import download.Downloader;
import pipeline.Pipeline;
import processor.PageProcessor;
import scheduler.Scheduler;
import threadpool.DefaultThreadPool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 爬虫开始页面
 */
public class Spider implements Runnable {

    private Site site;
    private Request firstRequest;
    private Downloader downloader;
    private PageProcessor process;
    private Pipeline pipeline;
    private Scheduler scheduler;
    private DefaultThreadPool executorService = new DefaultThreadPool(10);

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Spider(Site site, Request firstRequest, Downloader downloader, PageProcessor process,
                  Pipeline pipeline, Scheduler scheduler) {
        this.site = site;
        this.firstRequest = firstRequest;
        this.downloader = downloader;
        this.process = process;
        this.pipeline = pipeline;
        this.scheduler = scheduler;
        scheduler.push(firstRequest);
    }

    @Override
    public void run() {
        executorService.execute(() -> {
            boolean running = true;
            while (running) {
                Request request = scheduler.poll();
                if (request == null) {
                    running = false;
                } else {
                    Page page = downloader.download(request, site);
                    process.process(page);
                    List<Request> targetUrl = page.getTargetRequests();
                    List<Request> helpUrl = page.getHelpRequest();
                    targetUrl.forEach(request1 -> scheduler.push(request1));
                    helpUrl.forEach(request1 -> scheduler.push(request1));
                    ResultItems resultItems = page.getResultItems();
                    pipeline.process(resultItems);
                }
            }
        });
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Request getFirstRequest() {
        return firstRequest;
    }

    public void setFirstRequest(Request firstRequest) {
        this.firstRequest = firstRequest;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public PageProcessor getProcess() {
        return process;
    }

    public void setProcess(PageProcessor process) {
        this.process = process;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }



    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
