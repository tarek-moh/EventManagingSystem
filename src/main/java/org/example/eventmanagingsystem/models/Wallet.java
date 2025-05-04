package org.example.eventmanagingsystem.models;

public class Wallet {
      private  double balance;

    public Wallet() {
        this.balance =0;
    }

     public Wallet(double balance){
         this.balance = balance;
     }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void addFunds(double amount) throws IllegalArgumentException {
         if (amount > 0){
             balance += amount;
             return;
         }
         throw new IllegalArgumentException("Funds provided is negative, Funds cannot be negative");

    }

    public Boolean deductFunds(double amount) throws IllegalArgumentException {
         if(amount<0){
             throw new IllegalArgumentException("Funds provided is negative, Funds cannot be negative");
         }

         if(balance >= amount){
             balance-= amount;
          return true;
         }
         else{
             return false;
         }

    }
}

