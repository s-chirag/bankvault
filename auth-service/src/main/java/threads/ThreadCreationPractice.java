package threads;

/**
 * THREAD CREATION PRACTICE — Section 2
 *
 * Plain Core Java. No framework, no Spring.
 * Run each one several times — some produce different output each run,
 * and noticing that IS the lesson.
 *
 * Uncomment one at a time in main().
 */
public class ThreadCreationPractice {

    public static void main(String[] args) throws Exception {
//         p1();
//         p2();
       //  p3();
//         p4();
      //      p5();
//         p6();
         p7();
//         p8();
////         p9();
//        p11();
    }

    // ─────────────────────────────────────────────────────────────
    // P1 — Three ways to create a thread
    //
    // Create and start three threads that each print
    //     "Hello from <thread name>"
    // using a DIFFERENT creation style for each:
    //   a) a class that extends Thread
    //   b) a class that implements Runnable
    //   c) a lambda
    //
    // Use Thread.currentThread().getName() to get the name.
    // Note what the default names look like.
    // ─────────────────────────────────────────────────────────────
    static void p1() {

        Thread t1 = new Thread(() ->
                System.out.println("Hello from t1 "+ Thread.currentThread().getName()));


        Thread t2 = new Thread(){

            @Override
            public void run(){
                System.out.println("Hello from t2 "+ Thread.currentThread().getName());
            }
        };

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("This is thread t3 "+  Thread.currentThread().getName());
            }
        });


        Thread t4 = new MyThread();

        Runnable t5 = new MyRunnable();
        t5.run();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    // ─────────────────────────────────────────────────────────────
    // P2 — Naming threads
    //
    // Create three threads named "worker-1", "worker-2", "worker-3".
    // Each prints its own name five times.
    //
    // Run it three or four times. Does the output come out in the same
    // order every time? Write down what you observe as a comment.
    // ─────────────────────────────────────────────────────────────
    static void p2() {

        Thread t1 = new Thread(() -> { int count = 0;
            while(count<=5){
                System.out.println("This is thread t1 " +Thread.currentThread().getName());
                count ++;
            }});
        Thread t2 = new Thread(() -> { int count = 0;
            while(count<=5){
                System.out.println("This is thread t2 " +Thread.currentThread().getName());
                count ++;
            }});

        Thread t3 = new Thread(() -> { int count = 0;
            while(count<=5){
                System.out.println("This is thread t3 " +Thread.currentThread().getName());
                count ++;
            }});

        t1.setName("worker thread 1");
        t2.setName("worker thread 2");
        t3.setName("worker thread 3");

        t1.start();
        t2.start();
        t3.start();

    }

    // ─────────────────────────────────────────────────────────────
    // P3 — The main thread is a thread too
    //
    // Print the name of the currently running thread from inside main().
    // Then start one new thread that prints its own name.
    //
    // Before running: what do you think main's thread is called?
    // ─────────────────────────────────────────────────────────────
    static void p3() {

        System.out.println("This is main thread i guess" + Thread.currentThread().getName());

        Thread myThread = new MyThread();

        myThread.start();


    }

    // ─────────────────────────────────────────────────────────────
    // P4 — join()
    //
    // Start a thread that sleeps 2 seconds, then prints "worker done".
    // After starting it, main should print "main done".
    //
    // Run it. Which prints first?
    // Now add t.join() before main's print. What changes, and why?
    //
    // Thread.sleep(2000) throws InterruptedException — you'll need
    // a try/catch inside the thread body.
    // ─────────────────────────────────────────────────────────────
    static void p4() throws Exception {

        Thread sleepingThread = new Thread(() -> {
            try {

                Thread.sleep(2000);
                System.out.println("wroker done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        sleepingThread.start();
        sleepingThread.join();
        System.out.println("main done");
//whta i learnt is join will make a thread execute first before the main thread is executed
    }

    // ─────────────────────────────────────────────────────────────
    // P5 — Daemon threads
    //
    // Start a thread that loops forever printing "still alive" every
    // 500ms. main() should print "main finished" and return immediately.
    //
    // Run it. Does the program exit?
    // Now call t.setDaemon(true) BEFORE t.start(). What changes?
    //
    // Explain in a comment what a daemon thread is.
    // ─────────────────────────────────────────────────────────────
    static void p5() {

        Thread daemonThead = new Thread(() -> {
            int count =1;
            while(count ==1){
                System.out.println("still alive!!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        daemonThead.setDaemon(true);

        daemonThead.start();
        System.out.println("main finished");

        // what i understood was if its non daemon thread it will still executes ecen if main thread is executed, if its daemon it will terminate along with main thread or after it
    }

    // ─────────────────────────────────────────────────────────────
    // P6 — Thread states
    //
    // Create a thread that sleeps for 1 second.
    // Print t.getState() at three points:
    //   - before start()
    //   - immediately after start()
    //   - after join()
    //
    // Write down the three states you see.
    // ─────────────────────────────────────────────────────────────
    static void p6() throws Exception {
        Thread threadState = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("the state of this thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("This is thread state before start>> "+threadState.getState());
        threadState.start();
        System.out.println("This is thread state just after start>> "+threadState.getState());
        threadState.join();
        System.out.println("This is thread state just after join>> "+threadState.getState());

        // the thread state is new when it hasnet started
        // the thread state is in runnable state when its in sleep and after started
        //the thread state is in t erminated after join is callled.
        // your code
    }

    // ─────────────────────────────────────────────────────────────
    // P7 — Interruption
    //
    // Start a thread that sleeps for 10 seconds then prints "finished".
    // From main, wait 1 second, then call t.interrupt().
    //
    // What happens? Catch InterruptedException inside the thread and
    // print "I was interrupted" instead.
    //
    // This is how you cancel a thread — there is no kill() method.
    // Look up why Thread.stop() was deprecated.
    // ─────────────────────────────────────────────────────────────
    static void p7() throws Exception {
        // your code

        Thread sleepiing = new Thread(() ->
        {
            try {
                Thread.sleep(10000);
                System.out.println("finished");
            } catch (InterruptedException e) {
                System.out.println("i was interrupted");
            }
        });

        sleepiing.start();
        Thread.sleep(1000);
        sleepiing.interrupt();
    }

    // ─────────────────────────────────────────────────────────────
    // P8 — Uncaught exceptions
    //
    // Start a thread whose body throws a RuntimeException.
    // Run it. Does the program crash? What does main do?
    //
    // Now attach a handler before starting:
    //     t.setUncaughtExceptionHandler((thread, ex) -> ...)
    // and print something useful.
    //
    // Why does this matter in production?
    // ─────────────────────────────────────────────────────────────
    static void p8() {

        Thread throwExThread = new Thread(() -> {
            System.out.println("This thread throws exception");
            throw new RuntimeException("This is real exp");
        });

     //   throwExThread.start();
        throwExThread.setUncaughtExceptionHandler((t, e) -> System.out.println("This is epected exception"));
        throwExThread.start();


    }

    // ─────────────────────────────────────────────────────────────
    // P9 — Ordering is not guaranteed
    //
    // Start 10 threads. Each prints its own index (0 to 9).
    // Run the program five times.
    //
    // Do you ever get 0..9 in order? Write down what you see.
    //
    // This is the single most important thing to internalise about
    // threads: you control WHAT runs, not WHEN.
    // ─────────────────────────────────────────────────────────────
    static void p9() {

        Thread t1 = new Thread(() -> {int count=0; while(count <=10){
                System.out.println("This is thread t1 index " + count + " :::" ); count++;}   });

        Thread t2 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t2 index " + count + " :::" ); count++;}   });

        Thread t3 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t3 index " + count + " :::" ); count++;}   });
        Thread t4 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t4 index " + count + " :::" ); count++;}   });
        Thread t5 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t5 index " + count + " :::" ); count++;}   });
        Thread t6 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t6 index " + count + " :::" ); count++;}   });
        Thread t7 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t7 index " + count + " :::" ); count++;}   });
        Thread t8 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t8 index " + count + " :::" ); count++;}   });
        Thread t9 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t9 index " + count + " :::" ); count++;}   });
        Thread t10 = new Thread(() -> {int count=0; while(count <=10){
            System.out.println("This is thread t10 index " + count + " :::" ); count++;}   });

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

    }

   public static void p11(){
        //this is checking my exception skills again

       Thread threadException = new Thread(() -> {
           System.out.println("this is inside thread exception>>");
           throw new RuntimeException("This is voluntary exception");
       });

       threadException.setUncaughtExceptionHandler((t, e) -> System.out.println("This exception is expected!!!!!"));

       threadException.start();
   }
}