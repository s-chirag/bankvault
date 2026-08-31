package io.bankvault.auth.algorithms;

public class Countdown {

    public void countdown(int n) {
        System.out.println(n);

        if(n<=0){
            return;
        }

        countdown(n-1);

    return;


    }

    public static void main(String[] args) {
        Countdown c = new Countdown();

        c.countdown(5);
        System.out.println("---");
        c.countdown(1);
        System.out.println("---");
        c.countdown(0);   // just "Done"
    }

}
