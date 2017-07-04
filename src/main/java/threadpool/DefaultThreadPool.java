package threadpool;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认线程池
 */
public class DefaultThreadPool <Job extends Runnable> implements ThreadPool<Job> {

    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    //线程池默认个数
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //线程池最小个数
    private static final int MIN_WOKER_NUMBERS = 1;
    private AtomicLong threadNum = new AtomicLong();
    //工作线程列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    //存储工作队列
    private final LinkedList<Job> jobs = new LinkedList<>();
    //默认工作线程个数
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //互斥访问工作队列
    private ReentrantLock mainLock = new ReentrantLock();
    private Condition condition = mainLock.newCondition();
    //互斥修改工作线程列表
    private ReentrantLock workLock = new ReentrantLock();

    public DefaultThreadPool() {
        initializerWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS :
                num < MIN_WOKER_NUMBERS ? MIN_WOKER_NUMBERS : num;
        initializerWorkers(workerNum);
    }

    private void initializerWorkers(int num){
        for(int i=0; i<num; ++i){
            Worker worker = new Worker();
            workers.add(worker);
        }
    }

    @Override
    public void execute(Job job) {
        if(job != null){
            mainLock.lock();
            condition.signal();
            try {
                jobs.addLast(job);
            } finally {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    @Override
    public void addWorkers(int num) {
       workLock.lock();
        try {
            if(num + this.workerNum > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializerWorkers(num);
            this.workerNum += num;
        } finally {
            workLock.unlock();
        }
    }

    @Override
    public void removeWorker(int num) {
        workLock.lock();
        try {
            if(num > workerNum){
                throw new IllegalArgumentException("删除的线程个数超出总个数");
            }
            int count = 0;
            while(count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    ++count;
                }
            }
        } finally {
            workLock.unlock();
        }
    }

    private final class Worker implements Runnable{
        final Thread thread;
        private volatile boolean running = true;

        public Worker() {
            thread = new Thread(this, "ThreadPool-Worker-"+threadNum.getAndIncrement());
            thread.start();
        }
        @Override
        public void run() {
            while (running){
                Job job = null;
                mainLock.lock();
                try {
                    while(jobs.isEmpty()){
                        condition.await();
                    }
                    job = jobs.removeFirst();
                } catch (InterruptedException e) {
                    //感知外接对WorkerThread进行中断任务
                    Thread.currentThread().interrupt();
                    System.out.println("停止当前线程");
                    return;
                }finally {
                    mainLock.unlock();
                }
                if(job != null){
                    job.run();
                }
            }
        }

        /**
         * 关闭改线程
         */
        public void shutdown(){
            running = false;
            thread.interrupt();
        }
    }
}
