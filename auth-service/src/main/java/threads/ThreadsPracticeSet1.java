package threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * THREADS PRACTICE — SET 1 (straightforward)
 *
 * One concept per problem. Nothing twisted yet.
 * Uncomment one at a time in main().
 *
 * Covers: AtomicInteger, volatile, synchronized, ReentrantLock,
 *         tryLock, ReadWriteLock, Semaphore, ExecutorService,
 *         Callable/Future, invokeAll, wait/notify.
 */
public class ThreadsPracticeSet1 {

    public static void main(String[] args) throws Exception {
   //      p1();
   //      p2();
    //     p3();
    //     p4();
    //     p5();
     ///    p6();
//         p7();
//         p8();
     //    p9();
//         p10();
         p11();
        // p12();
    }

    // ═══════════════════════════════════════════════════════════
    // ATOMICS
    // ═══════════════════════════════════════════════════════════

    /**
     * P1 — AtomicInteger
     *
     * Three threads each increment a shared AtomicInteger 1000 times.
     * Print the final value.
     *
     * Expected: 3000, every single run.
     *
     * Use incrementAndGet(). Join all three before printing.
     */
    static AtomicInteger counter = new AtomicInteger(0);

    static void p1() throws Exception {




        Thread t1 = new Thread(() -> {
            for(int i=0; i<1000; i++){
                counter.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=0; i<1000; i++){
                counter.incrementAndGet();
            }
        });

        Thread t3 = new Thread(() -> {
            for(int i=0; i<1000; i++){
                counter.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("this is couter >>"+ counter);
    }

    /**
     * P2 — AtomicInteger methods
     *
     * Starting from 10, print the result of each of these in order:
     *   incrementAndGet()
     *   getAndIncrement()
     *   addAndGet(5)
     *   getAndSet(100)
     *   compareAndSet(100, 200)   — then print get()
     *
     * Single threaded. The point is noticing which return the
     * value BEFORE the operation and which return it AFTER.
     *
     * Expected: 11, 11, 17, 17, true, 200
     */
    static void p2() throws InterruptedException {

        AtomicInteger count = new AtomicInteger();
        count.set(10);
        Thread t1 = new Thread(() -> {
            for(int i=0; i<1; i++){
                System.out.println(count.incrementAndGet());
                System.out.println(count.getAndIncrement());
                System.out.println(count.addAndGet(5));
                System.out.println(count.getAndSet(100));
                System.out.println(count.compareAndSet(100,200));
                System.out.println(count.get());
            }
        });
        t1.start();
        t1.join();


    }

    // ═══════════════════════════════════════════════════════════
    // VOLATILE
    // ═══════════════════════════════════════════════════════════

    /**
     * P3 — The volatile flag
     *
     * Start a thread that loops while a boolean flag is true,
     * incrementing a local counter. After 100ms, main sets the
     * flag to false. Print how many iterations it managed.
     *
     * Run it WITHOUT volatile first. Does it stop?
     * Then add volatile. Does it stop now?
     *
     * Write down what you observed.
     */
    static volatile  boolean  running = true;      // add volatile, then remove it, compare
     static int  count = 0;

    static void p3() throws Exception {
        // your code


        Thread t1 = new Thread(() -> {
            while(running==true){
        //        System.out.println(count);
                count++;
            }
            System.out.println("stopped after " + count + " iterations");
        });
        t1.start();
        Thread.currentThread().sleep(100);
        running=false;



    }

    // ═══════════════════════════════════════════════════════════
    // SYNCHRONIZED
    // ═══════════════════════════════════════════════════════════

    /**
     * P4 — synchronized block vs method
     *
     * Write a BankAccount class with a balance field, a deposit(int)
     * method and a withdraw(int) method. Make both thread-safe.
     *
     * Start 100 threads that each deposit 10, and 100 that each
     * withdraw 10. Start balance 0.
     *
     * Expected: final balance 0, every run.
     *
     * Do it with a synchronized METHOD first.
     * Then redo it with a synchronized BLOCK on a private lock object.
     * Why is the private lock object considered better practice?
     */
    static void p4() throws Exception {
        BankAccount bz = new BankAccount();
        bz.setBalance(0);
        System.out.println("This is balance before >>"+ bz.getBalance());
        Thread[] thread = new Thread[100];

        for(int i=0; i<100;i++){
            thread[i] = new Thread(() -> bz.deposit(10));
        }
        for(Thread t:thread){
            t.start();
        }
        for(Thread t:thread){
            t.join();
        }
        System.out.println("This is balance after deposit>>"+ bz.getBalance());

        Thread[] withdrawlll= new Thread[100];

        for(int i=0; i<100;i++){
            withdrawlll[i] = new Thread(() -> bz.withdrawl(10));
        }
        for(Thread tt:withdrawlll){
            tt.start();
        }
        for(Thread tt:withdrawlll){
            tt.join();
        }

        System.out.println("This is balance after >>"+ bz.getBalance());


    }

    // ═══════════════════════════════════════════════════════════
    // REENTRANT LOCK
    // ═══════════════════════════════════════════════════════════

    /**
     * P5 — ReentrantLock basics
     *
     * Same as P1 but use a plain int guarded by a ReentrantLock
     * instead of AtomicInteger.
     *
     * Three threads, 1000 increments each. Expected: 3000.
     *
     * The unlock MUST be in a finally block.
     */
    static final ReentrantLock lock = new ReentrantLock();
    static int lockedCounter = 0;

    static void p5() throws Exception {

        Thread t1 = new Thread(() -> {
            lock.lock();

            try {
                for (int i = 0; i < 1000; i++) {
                    lockedCounter++;
                }
            }
            finally{
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();

            try {
                for (int i = 0; i < 1000; i++) {
                    lockedCounter++;
                }
            }

             finally{
                lock.unlock();
            }
        });

        Thread t3 = new Thread(() -> {
            lock.lock();

            try {
                for (int i = 0; i < 1000; i++) {
                    lockedCounter++;
                }
            }

             finally{
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("this is couter >>"+ lockedCounter);




    }

    /**
     * P6 — tryLock with timeout
     *
     * Two threads. Thread A acquires the lock and holds it for 2 seconds
     * (sleep while holding it).
     *
     * Thread B tries tryLock(500, TimeUnit.MILLISECONDS).
     * It should fail — print "B could not get the lock".
     *
     * Then change B's timeout to 3 seconds. Now it should succeed.
     */

    static  void printp6(){
        System.out.println("this thread >"+ Thread.currentThread().getName());
    }

    static void p6() throws Exception {
        // your code
//        Thread t1 = new Thread(() -> {
//            try {
//                lock.tryLock(2000,TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("This is thread t1");
//
//        });
//
//        Thread t2 = new Thread(() -> {
//            try {
//                lock.tryLock(500,TimeUnit.MILLISECONDS);
//                System.out.println("THread 2 did not get the locl");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        });

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("A got the lock, holding 2s");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println("B got the lock");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("B could not get the lock");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


//        Thread t3 = new Thread(() -> {
//            lock.tryLock();
//
//            try {
//                for (int i = 0; i < 1000; i++) {
//                    lockedCounter++;
//                }
//            }
//            finally{
//                lock.unlock();
//            }
//        });
//
        t1.start();
   //     Thread.sleep(100);      // let A actually acquire the lock

        t2.start();
        t1.join();

        t2.join();
//        t3.start();
//        t3.join();
//        System.out.println("this is couter >>"+ lockedCounter);

    }

    // ═══════════════════════════════════════════════════════════
    // READ WRITE LOCK
    // ═══════════════════════════════════════════════════════════

    /**
     * P7 — ReadWriteLock
     *
     * A shared int value.
     *
     * Start 5 reader threads. Each acquires the READ lock, prints
     * "reader N reading", sleeps 500ms, then releases.
     *
     * Start 1 writer thread that acquires the WRITE lock, prints
     * "writer writing", sleeps 500ms, then releases.
     *
     * Watch the output. Do the readers overlap with each other?
     * Does the writer overlap with anything?
     */
    static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    static int sharedValue = 0;

    static void itsReading() throws InterruptedException {
        rwLock.readLock().lock();
        try {
        System.out.println("This thread is reading >> "+ Thread.currentThread().getName() +" is reading");
        Thread.sleep(500);
        } finally {
        rwLock.readLock().unlock();
        }
    }
    static void itsWriting() throws InterruptedException {
        rwLock.writeLock().lock();
        try {
        System.out.println("This thread is writing >> "+ Thread.currentThread().getName() +" is writer");
        Thread.sleep(500);
    } finally     {
        rwLock.writeLock().unlock();    }    }

    static void p7() throws Exception {
        // your code

        Thread t1 = new Thread(() -> {try {itsReading(); } catch (InterruptedException e) {throw new RuntimeException(e);}});
        Thread t2 = new Thread(() -> {try {itsReading();} catch (InterruptedException e) {throw new RuntimeException(e);}});
        Thread t3 = new Thread(() -> {try {itsReading();} catch (InterruptedException e) {throw new RuntimeException(e);}});
        Thread t4 = new Thread(() -> {try {itsReading();} catch (InterruptedException e) {throw new RuntimeException(e);}});
        Thread t5 = new Thread(() -> {try {itsReading();} catch (InterruptedException e) {throw new RuntimeException(e);}});
        Thread t6 = new Thread(() -> {try {itsWriting();} catch (InterruptedException e) {throw new RuntimeException(e);}});

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

t6.start();


    }

    // ═══════════════════════════════════════════════════════════
    // SEMAPHORE
    // ═══════════════════════════════════════════════════════════

    /**
     * P8 — Semaphore as a limiter
     *
     * You have 10 threads but only 3 "database connections".
     *
     * Use a Semaphore with 3 permits. Each thread acquires a permit,
     * prints "thread N got a connection", sleeps 1 second, prints
     * "thread N releasing", then releases.
     *
     * Watch the output — you should never see more than 3 threads
     * holding a connection at once.
     */
    static Semaphore semaphore = new Semaphore(3);
    static int semaphonmeNum = 0;
    static void getDb() throws InterruptedException {
        semaphore.acquire();
        try{
        System.out.println("Thread number >>"+ Thread.currentThread().getName());
        semaphonmeNum++;
        Thread.sleep(1000);
            System.out.println("This is count number >>"+ semaphonmeNum);
            System.out.println("permits available: " + semaphore.availablePermits());
        }
        finally {
            System.out.println(Thread.currentThread().getName() + " releasing");
            semaphore.release();
        }
    }

    static void p8() throws Exception {
        // your code


//        Thread[] dbThread = new Thread[10];
//        for(int i=0; i<10; i++){
//
//            dbThread[i] = new Thread(() -> {
//                try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}
//            });
//        }
//
//        for(int j=0; j<10; j++){
//            dbThread[j].start();
//        }
//        for(int k=0; k<10; k++){
//            dbThread[k].join();
//        }

        Thread t1= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t2= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t3= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t4= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t5= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t6= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t7= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t8= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t9= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t10= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});
        Thread t11= new Thread(() -> {try { getDb();} catch (InterruptedException e) {  throw new RuntimeException(e);}});

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();
        t11.join();


    }

    // ═══════════════════════════════════════════════════════════
    // EXECUTOR SERVICE
    // ═══════════════════════════════════════════════════════════

    /**
     * P9 — Fixed thread pool
     *
     * Create a fixed thread pool of 3 threads.
     * Submit 10 Runnable tasks. Each prints which thread is running it
     * and sleeps 200ms.
     *
     * Shut the pool down properly and wait for completion.
     *
     * Watch the thread names in the output — you should only ever see
     * 3 distinct names for 10 tasks.
     */
    static void p9() throws Exception {
        // your code
        ExecutorService executerServices = Executors.newFixedThreadPool(3);



            for(int i=0; i<10; i++){
                final int taskId = i;
                executerServices.submit(() -> {
                System.out.println("Thread ruinning ::"+ taskId+" this tasl " +Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executerServices.shutdown();

    }

    /**
     * P10 — Callable and Future
     *
     * Submit a Callable that sleeps 1 second then returns 42.
     *
     * Print "submitted" immediately after submitting.
     * Then call future.get() and print the result.
     *
     * Notice: "submitted" prints instantly, then there's a pause.
     * Why? What does get() do?
     *
     * Expected: "submitted", 1 second pause, then 42.
     */
    static void p10() throws Exception {
        // your code

        ExecutorService executorCallable = Executors.newFixedThreadPool(3);

        Future<Integer> future = executorCallable.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 41;
        });
        System.out.println("submitted!!!");
 //       future.get();
        System.out.println(future.get());

        executorCallable.shutdown();
    }

    /**
     * P11 — Multiple Futures
     *
     * Submit 5 Callables. Callable N returns N squared, after
     * sleeping for N * 200ms.
     *
     * Collect the Futures in a List, then loop through and print
     * each result.
     *
     * Expected: 1, 4, 9, 16, 25
     *
     * Then try invokeAll(...) instead — it takes a Collection of
     * Callables and returns a List of Futures, blocking until all
     * are done. Which is cleaner?
     */
    static void p11() throws Exception {
        // your code

        ExecutorService executorCallable = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> callable = new ArrayList<>();
        for(int i=0; i<5; i++) {
            final var  n = i;
            callable.add(() -> {
                Thread.sleep(n * 200);
                return n * n;
            });
       //     System.out.println(future.get());

        }
        List<Future<Integer>> futures = executorCallable.invokeAll(callable);

        for (Future<Integer> f : futures) {
            System.out.println(f.get());
        }

        executorCallable.shutdown();

    }

    // ═══════════════════════════════════════════════════════════
    // WAIT / NOTIFY
    // ═══════════════════════════════════════════════════════════

    /**
     * P12 — Producer / Consumer with wait and notify
     *
     * A shared LinkedList<Integer> acting as a queue, max size 5.
     *
     * Producer thread: adds numbers 1 to 10. If the queue is full,
     * wait(). After adding, notify().
     *
     * Consumer thread: removes numbers. If the queue is empty,
     * wait(). After removing, notify().
     *
     * Both must be inside synchronized blocks on the same object.
     * Use while (condition) wait(), NOT if (condition) wait().
     *
     * Work out why 'while' rather than 'if' before you run it.
     */
    static void p12() throws Exception {
        // your code

        LinkedList<Integer> queue = new LinkedList<>();

        Thread t1 = new Thread(() -> {
            for (int i=0; i<10;i++){
                queue.add(i);
            }
        });







    }
}