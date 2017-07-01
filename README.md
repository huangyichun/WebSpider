# WebSpider
## 该爬虫总体架构如下:

![image.png-158.1kB][2]

#### WebSpider由四个组件(Downloader、PageProcessor、Scheduler、Pipeline)构成，核心代码非常简单，主要是将这些组件结合并完成多线程的任务。这意味着，Spider中，你基本上可以对爬虫的功能做任何定制。
- Downloader模块:</br>
    该模将URL封装成Request，负责下载网页，同时支持使用代理服务器进行页面下载，只需在Site（网站信息）中设置代理服务器池即可。该模块对于下载的页面封装成Page，提供给PageProcessor解析。
- PageProcessor模块：</br>
    该模块负责解析Page信息，并将解析出的内容提供给Scheduler和Pipeline，在这四个组件中，PageProcessor对于每个站点每个页面都不一样，是需要使用者定制的部分。
- Scheduler模块:</br>
   该模块负责管理待抓取的URL，以及一些去重的工作。除非特殊需求，无需使用者实现。
- Pipeline模块:</br>
   该模块负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebSpider默认提供了“输出到控制台”方案。
#### 用于数据流转的对象
- Request,</br>
Request是对URL地址的一层封装，一个Request对应一个URL地址。
它是PageProcessor与Downloader交互的载体，也是PageProcessor控制Downloader唯一方式。
- Page</br>
Page代表了从Downloader下载到的一个页面的内容，定义一个String类型来存储下载到的页面。此外Page还包含resultItems，targetRequests，helpRequest
其中resultItems是PageProcessoor解析Page得到的，他是我们要抓取的数据。targetRequests代表的是要抓取数据所在的页面，helpRequest代表的是找到targetRequest的url。例如:抓取新闻，首先要找到新闻所在的列表页，这个列表页所在的url就是一个helpRequest，而具体新闻页面就是一个targetRequest。

- ResultItems</br>
ResultItems相当于一个Map，它保存PageProcessor处理的结果，供Pipeline使用。

#### 其他对象
- Site</br>
     Site保存网站的相关信息，其中包括网站的主页，网站编码，代理池，请求头等
- Proxy</br>
    代理服务器对象，保存代理服务器地址和端口号，以及登录的用户名和密码。
- ProxyPool</br>
    代理服务器池接口，WebSpider实现了简单的代理服务器池，用户可以在resource文件下的proxy.txt中将代理服务器地址和端口复制进去，通过调用SimpleProxyPoll的getProxy方法可以随机使用其中的一个代理服务器进行页面下载。
#### 自定义线程池
  该爬虫使用了自定义的线程池，抽象接口为:ThreadPool，提供了执行，结束，添加工作线程和减少工作线程的基本功能。并且实现了一个默认的线程池DefaultThreadPool，该线程池主要是为了更加深入理解多线程而写的，使用者可以将其替换成Java默认的线程池，不影响框架使用。</br>
线程池接口:
```
    /**
     * 自定义线程池
     */
    public interface ThreadPool <Job extends Runnable>{
        //执行一个job，这个job需要实现Runnable接口
        void execute(Job job);
        //关闭线程池
        void shutdown();
        //增加工作线程
        void addWorkers(int num);
        //减少工作线程
        void removeWorker(int num);
    }
```
默认线程池
```
    package threadpool;
    
    import java.util.*;
    import java.util.concurrent.atomic.AtomicLong;
    import java.util.concurrent.locks.Condition;
    import java.util.concurrent.locks.ReentrantLock;
    
    /**
     * 默认线程池
     * 该线程池在初始化固定的线程数，用Worker进行封装
     * 且提供一个线程安全的队列，存储Worker。
     * 同时提供一个线程不安全的工作队列，让线程池中的线程互斥无限循环的获取队列中的Job
     * 如果队列为空，则等待。如果队列中有新的Job，则唤醒等待线程。
     */
    public class DefaultThreadPool <Job extends Runnable> implements ThreadPool<Job> {
    
        //线程池最大限制数
        private static final int MAX_WORKER_NUMBERS = 20;
        //线程池默认个数
        private static final int DEFAULT_WORKER_NUMBERS = 5;
        //线程池最小个数
        private static final int MIN_WOKER_NUMBERS = 1;
        //记录线程个数
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
            private volatile boolean running = true;//控制线程运行
    
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
```

#### 程序运行
使用者可以运行Test的mode包下的SpiderTest的main方法，可以在控制台中打印出四川大学计算机学院400条新闻。



[2]: http://static.zybuluo.com/huangyichun/9iybxvwxpcyp4qg1ht7x5w7c/image.png
