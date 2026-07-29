package io.bankvault.auth.collections;

public class Factorial {

    public int factorial(int n) {
        // your code here
        return 0;
    }

    public static void main(String[] args) {
        Factorial f = new Factorial();

        System.out.println(f.factorial(5));   // expect 120
        System.out.println(f.factorial(4));   // expect 24
        System.out.println(f.factorial(1));   // expect 1
        System.out.println(f.factorial(0));   // expect 1
    }
}
