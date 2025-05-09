package org.example.eventmanagingsystem.services;
import org.example.eventmanagingsystem.models.Wallet;

public class PaymentService {
    private static final double EPSILON = 1e-9;

    public static boolean transferFunds(Wallet from, Wallet to, double amount) {
        if (amount < EPSILON) {
            // Avoid transferring if amount is effectively zero
            return true;
        }

        double balance = from.getBalance().get();

        if (balance > amount || Math.abs(balance - amount) < EPSILON) {
            from.deductFunds(amount);
            to.addFunds(amount);
            return true;
        }

        return false;
    }

};