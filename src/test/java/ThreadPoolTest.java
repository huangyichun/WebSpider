
import threadpool.DefaultThreadPool;
import threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {

        ThreadPool threadPool = new DefaultThreadPool(5);
        List<Runnable> list = new ArrayList<>();
        AtomicLong aLong = new AtomicLong(0);
        for(int i=0; i<100; ++i){
            Runnable runnable = () -> {
                long num = aLong.getAndIncrement();
                for(int i1 = 0; i1 <100; ++i1){
                    System.out.println(Thread.currentThread().getName()+" 第" + num +"个job值:" + i1);
                }
            };
            list.add(runnable);
        }
        while(list.size() > 0){
            Runnable runnable = list.remove(0);
            threadPool.execute(runnable);
        }

        threadPool.shutdown();
    }

}
