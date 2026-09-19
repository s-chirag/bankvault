package threads;

import java.util.concurrent.*;
import java.util.function.Consumer;

public class ExecutorServices {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2), new CustomThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());


        for(int i =0; i<=4; i++){

            threadPoolExecutor.submit(() -> {
                try {
                    Thread.sleep(100);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("Thasl process by >>"+ Thread.currentThread().getName());

            });

            threadPoolExecutor.shutdown();

        }
    }


    static class CustomThreadFactory implements ThreadFactory{
        @Override
         public Thread  newThread(Runnable r){

            Thread th = new Thread(r);
            th.setPriority(Thread.NORM_PRIORITY);
            return th;
        }
    }
}
