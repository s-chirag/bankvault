package threads;

public class MyThread extends Thread{

    public void run(){
        System.out.println("this is thread from extending thread class t4"+ Thread.currentThread().getName());
    }
}
