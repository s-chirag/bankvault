package threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalMain {

    public static void main(String[] args) {

        ThreadLocal threadLocal = new ThreadLocal();

        threadLocal.set(Thread.currentThread().getName());



        Thread t1 = new Thread(() -> {
            threadLocal.set(Thread.currentThread().getName());
            threadLocal.remove();
            System.out.println("This is threadlocalName in t1>>"+ threadLocal.get());

        });

        t1.start();
        t1.setName("yavano thread");
        threadLocal.remove();
        System.out.println("This is threadlocalName >>"+ threadLocal.get());

        ExecutorService checkingThreadLocal = Executors.newFixedThreadPool(5);

        checkingThreadLocal.submit(() ->{
                threadLocal.set(Thread.currentThread().getName());
                threadLocal.remove();
        });

        for(int i=0; i<15; i++){

            checkingThreadLocal.submit((() -> {
                System.out.println("This is printing threadlocal names ig >>"+threadLocal.get());
            }));
        }
    }


}
