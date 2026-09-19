package threads;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread( () ->
        {
            System.out.println("The thread in main method");
            System.out.println("The thread name"+ Thread.currentThread().getName());
            System.out.println("The thread priority"+ Thread.currentThread().getPriority());

            throw new RuntimeException("i am sending exception intentionally");

        });


        thread.setName("My first thread");

        thread.setUncaughtExceptionHandler((t, e) -> System.out.println("a critical error happened in threads"));

        thread.start();
        System.out.println(thread.getName());
        thread.sleep(1000);
        thread.setName("My first thread");
        System.out.println("We are in thread "+thread.getName() +"before starting a new thread");



    }
}
