package org.example.eventmanagingsystem.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Wallet {
    private final DoubleProperty balance = new SimpleDoubleProperty();

    public Wallet() {
        this.balance.set(0);
    }

     public Wallet(double balance){
         this.balance.set(balance);
     }

    public void setBalance(double balance) {
        this.balance.set(balance) ;
    }

    public DoubleProperty getBalance() {
        return balance;
    }

    public void addFunds(double amount) throws IllegalArgumentException {
         if (amount > 0){
             balance.set(balance.get() + amount) ;
             return;
         }
         throw new IllegalArgumentException("Funds provided is negative, Funds cannot be negative");

    }

    public Boolean deductFunds(double amount) throws IllegalArgumentException {
         if(amount<0){
             throw new IllegalArgumentException("Funds provided is negative, Funds cannot be negative");
         }

         if(balance.get() >= amount){
             balance.set(balance.get() - amount);
          return true;
         }
         else{
             return false;
         }

    }

    public DoubleProperty balanceProperty() {
        return balance;
    }
}

