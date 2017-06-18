package scheduler;

import domain.Request;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 去重调度器
 * Created by huangyichun on 2017/6/17.
 */
public class DuplicateRemovedScheduler implements Scheduler {

    HashSet<String> visitedUrl = new HashSet<>();
    BlockingQueue<Request> queueUrl = new LinkedBlockingQueue<>();
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @Override
    public void push(Request request) {
        lock.lock();
        try{
            if (!visitedUrl.contains(request.getUrl())){
                visitedUrl.add(request.getUrl());
                queueUrl.offer(request);
                condition.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Request poll() {
        lock.lock();
        try {
            while (queueUrl.isEmpty()) {
                try {
                    boolean isEnd = condition.await(10, TimeUnit.SECONDS);
                    if(!isEnd) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + ": 爬虫结束");
                }
            }
            return queueUrl.poll();
        } finally {
            lock.unlock();
        }

    }
}
