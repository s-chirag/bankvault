package threads;

public class BankAccount {

    private int balance;

    Object lock = new Object();
    public  int deposit(int amount){
        synchronized (lock) {
            balance = balance + amount;
            return balance;
        }
    }

    public  int withdrawl(int amount){
        synchronized (lock) {
            balance = balance - amount;
            return balance;
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
