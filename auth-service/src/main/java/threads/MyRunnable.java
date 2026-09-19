package threads;

public class MyRunnable implements Runnable{


    @Override
    public void run() {
        System.out.println("This is thread extending runnable > " + Thread.currentThread().getName());
    }
}
