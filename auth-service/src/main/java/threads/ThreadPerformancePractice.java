package threads;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * THREADS PRACTICE — Performance and Race Conditions
 *
 * Only T4 matters right now. T1, T2, T3 are optional.
 *
 * T4 is the important one — it shows a race condition happening live.
 * Run it five times and write down every result.
 */
public class ThreadPerformancePractice {

    static final int SIZE = 100_000_000;
    static int[] data;

    public static void main(String[] args) throws Exception {
        data = new int[SIZE];
        Arrays.fill(data, 1);

        // t1();
        t5();
    }

    // ─────────────────────────────────────────────────────────────
    // T1 — optional baseline (single-threaded sum, timed)
    // ─────────────────────────────────────────────────────────────
    static void t1() {
        long start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("sum=" + sum + "  time=" + (end - start) + "ms");
    }

    // ─────────────────────────────────────────────────────────────
    // T4 — The broken counter
    //
    // Four threads each increment sharedSum 25 million times.
    // Total increments = 100 million, so the answer should be 100000000.
    //
    // Your job:
    //   1. Fill in the two comments — start all threads, then join all
    //   2. Run it FIVE times
    //   3. Write down every result as a comment at the bottom
    //
    // Do NOT fix it. Just observe what happens and why.
    // ─────────────────────────────────────────────────────────────
    static AtomicLong sharedSum = new AtomicLong(0);

    static long sharedSum1 = 0;


    static void t4() throws Exception {
        //     sharedSum1 = 0;   // reset between runs
        int numThreads = 4;
        Thread[] workers = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < 25_000_000; j++) {
                    //   sharedSum.incrementAndGet();    // four threads hitting the same variable
                    synchronized(ThreadPerformancePractice.class) {
                        sharedSum1++;
                    }
                }
            });
        }

        // TODO: start all workers (one line per thread, or a loop)
        workers[1].start();
        workers[0].start();
        workers[3].start();
        workers[2].start();

        System.out.println("This is interruption "+ workers[0].isInterrupted());
        if(workers[0].isInterrupted()){
            workers[0].interrupt();
        }
        // TODO: join all workers (one line per thread, or a loop)
        workers[0].join();
        workers[1].join();
        workers[2].join();
        workers[3].join();


        System.out.println("Expected: 100000000");
        System.out.println("Got:      " + sharedSum);        System.out.println("Got:      " + sharedSum1);

    }

//    static void t4() throws Exception {
//        //     sharedSum1 = 0;   // reset between runs
//        int numThreads = 4;
//        Thread[] workers = new Thread[numThreads];
//
//        for (int i = 0; i < numThreads; i++) {
//            workers[i] = new Thread(() -> {
//                for (int j = 0; j < 25_000_000; j++) {
//                    //   sharedSum.incrementAndGet();    // four threads hitting the same variable
//                    synchronized(ThreadPerformancePractice.class) {
//                        sharedSum1++;
//                    }
//                }
//            });
//        }
//
//        // TODO: start all workers (one line per thread, or a loop)
//        workers[1].start();
//        workers[0].start();
//        workers[3].start();
//        workers[2].start();
//
//        System.out.println("This is interruption "+ workers[0].isInterrupted());
//        if(workers[0].isInterrupted()){
//            workers[0].interrupt();
//        }
//        // TODO: join all workers (one line per thread, or a loop)
//        workers[0].join();
//        workers[1].join();
//        workers[2].join();
//        workers[3].join();
//
//
//        System.out.println("Expected: 100000000");
//        System.out.println("Got:      " + sharedSum);        System.out.println("Got:      " + sharedSum1);
//
//    }



    static void t5() throws Exception {
             sharedSum1 = 0;   // reset between runs
        int numThreads = 4;
        Thread[] workers = new Thread[numThreads];

        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < numThreads; i++) {

            workers[i] = new Thread(() -> {
                for (int j = 0; j < 25_000_000; j++) {
                    try {
                        semaphore.acquire();

                            //   sharedSum.incrementAndGet();    // four threads hitting the same variable
                            //         synchronized(ThreadPerformancePractice.class) {
                            try {
                                sharedSum1++;

                            } finally {
                                semaphore.release();

                            }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //         }
                }
            });

        }

        // TODO: start all workers (one line per thread, or a loop)
        workers[1].start();
        workers[0].start();
        workers[3].start();
        workers[2].start();

        System.out.println("This is interruption "+ workers[0].isInterrupted());
        if(workers[0].isInterrupted()){
            workers[0].interrupted();
        }
        // TODO: join all workers (one line per thread, or a loop)
        workers[0].join();
        workers[1].join();
        workers[2].join();
        workers[3].join();


        System.out.println("Expected: 100000000");
        System.out.println("Got:      " + sharedSum);        System.out.println("Got:      " + sharedSum1);



    }
    public static void t6(){

        Thread t1 = new Thread(() -> {outer(); });
        t1.start();
        Thread t2 = new Thread(() -> {outer(); });
        t2.start();
        Thread t3 = new Thread(() -> {outer(); });
        t3.start();
        Thread t4 = new Thread(() -> {outer(); });
        t4.start();
        Thread t5 = new Thread(() -> {outer(); });
        t5.start();
        Thread t6 = new Thread(() -> {outer(); });
        t6.start();


    }

    static synchronized   void outer() {
        System.out.println("in outer" + Thread.currentThread().getName());
        try { Thread.sleep(10); } catch (InterruptedException e) {}
        inner();
    }

    static synchronized void inner() {
        System.out.println("in inner"+ Thread.currentThread().getName());
    }
    // Write your five results here as comments after you run it:
    // Run 1:
    // Run 2:
    // Run 3:
    // Run 4:
    // Run 5:
}