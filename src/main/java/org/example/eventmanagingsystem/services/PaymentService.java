package org.example.eventmanagingsystem.services;

import org.example.eventmanagingsystem.models.Wallet;

public class PaymentService {

    public static boolean transferFunds(Wallet from, Wallet to, double amount) {
        if (from.getBalance().get() >= amount) {
            from.deductFunds(amount);
            to.addFunds(amount);
            return true;
        }
       return false;
    }
};