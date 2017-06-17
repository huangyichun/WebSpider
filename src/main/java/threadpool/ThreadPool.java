package threadpool;


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
