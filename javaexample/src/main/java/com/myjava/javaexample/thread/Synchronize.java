package com.myjava.javaexample.thread;

public class Synchronize {
    public static void main(String[] args) {
        Bank1 Bank1 = new Bank1();
 
        for (int i = 0; i < Bank1.MAX_Account1; i++) {
            Thread t = new Thread(new Transaction1(Bank1, i));
            t.start();
        }
    }
}
/**
 * Account1.java
 * This class represents an Account1 in the Bank1
 * @author www.codejava.net
 */
 class Account1 {
    private int balance = 0;
 
    public Account1(int balance) {
        this.balance = balance;
    }
 
    public void withdraw(int amount) {
        this.balance -= amount;
    }
 
    public void deposit(int amount) {
        this.balance += amount;
    }
 
    public int getBalance() {
        return this.balance;
    }
}
 
 /**
  * Bank1.java
  * This class represents a Bank1 that manages Account1s and provides
  * money transfer function.
  * @author www.codejava.net
  */
 class Bank1 {
     public static final int MAX_Account1 = 10;
     public static final int MAX_AMOUNT = 10;
     public static final int INITIAL_BALANCE = 100;
  
     private Account1[] Account1s = new Account1[MAX_Account1];
  
     public Bank1() {
         for (int i = 0; i < Account1s.length; i++) {
             Account1s[i] = new Account1(INITIAL_BALANCE);
         }
     }
  
     public synchronized void transfer(int from, int to, int amount) {
         if (amount <= Account1s[from].getBalance()) {
             Account1s[from].withdraw(amount);
             Account1s[to].deposit(amount);
  
             String message = "%s transfered %d from %s to %s. Total balance: %d\n";
             String threadName = Thread.currentThread().getName();
             System.out.printf(message, threadName, amount, from, to, getTotalBalance());
         }
     }
  
     public synchronized int   getTotalBalance() {
         int total = 0;
  
         for (int i = 0; i < Account1s.length; i++) {
             total += Account1s[i].getBalance();
         }
  
         return total;
     }
 }
 
 /**
  * Transaction1.java
  * This class represents a Transaction1 task that can be executed by a thread.
  * @author www.codejava.net
  */
 class Transaction1 implements Runnable {
     private Bank1 Bank1;
     private int fromAccount1;
  
     public Transaction1(Bank1 Bank1, int fromAccount1) {
         this.Bank1 = Bank1;
  
  
         this.fromAccount1 = fromAccount1;
     }
  
     public void run() {
  
         while (true) {
             int toAccount1 = (int) (Math.random() * Bank1.MAX_Account1);
  
             if (toAccount1 == fromAccount1) continue;
  
             int amount = (int) (Math.random() * Bank1.MAX_AMOUNT);
  
             if (amount == 0) continue;
  
             Bank1.transfer(fromAccount1, toAccount1, amount);
  
             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
 }
