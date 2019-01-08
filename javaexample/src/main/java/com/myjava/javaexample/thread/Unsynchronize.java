package com.myjava.javaexample.thread;

public class Unsynchronize {
    public static void main(String[] args) {
        Bank bank = new Bank();
 
        for (int i = 0; i < Bank.MAX_ACCOUNT; i++) {
            Thread t = new Thread(new Transaction(bank, i));
            t.start();
        }
    }
}
/**
 * Account.java
 * This class represents an account in the bank
 * @author www.codejava.net
 */
 class Account {
    private int balance = 0;
 
    public Account(int balance) {
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
  * Bank.java
  * This class represents a bank that manages accounts and provides
  * money transfer function.
  * @author www.codejava.net
  */
 class Bank {
     public static final int MAX_ACCOUNT = 10;
     public static final int MAX_AMOUNT = 10;
     public static final int INITIAL_BALANCE = 100;
  
     private Account[] accounts = new Account[MAX_ACCOUNT];
  
     public Bank() {
         for (int i = 0; i < accounts.length; i++) {
             accounts[i] = new Account(INITIAL_BALANCE);
         }
     }
  
     public synchronized void transfer(int from, int to, int amount) {
         if (amount <= accounts[from].getBalance()) {
             accounts[from].withdraw(amount);
             accounts[to].deposit(amount);
  
             String message = "%s transfered %d from %s to %s. Total balance: %d\n";
             String threadName = Thread.currentThread().getName();
             System.out.printf(message, threadName, amount, from, to, getTotalBalance());
         }
     }
  
     public synchronized int   getTotalBalance() {
         int total = 0;
  
         for (int i = 0; i < accounts.length; i++) {
             total += accounts[i].getBalance();
         }
  
         return total;
     }
 }
 
 /**
  * Transaction.java
  * This class represents a transaction task that can be executed by a thread.
  * @author www.codejava.net
  */
 class Transaction implements Runnable {
     private Bank bank;
     private int fromAccount;
  
     public Transaction(Bank bank, int fromAccount) {
         this.bank = bank;
  
  
         this.fromAccount = fromAccount;
     }
  
     public void run() {
  
         while (true) {
             int toAccount = (int) (Math.random() * Bank.MAX_ACCOUNT);
  
             if (toAccount == fromAccount) continue;
  
             int amount = (int) (Math.random() * Bank.MAX_AMOUNT);
  
             if (amount == 0) continue;
  
             bank.transfer(fromAccount, toAccount, amount);
  
             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
 }
