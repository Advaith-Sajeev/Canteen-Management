package main;
import java.util.Scanner;

class AmritaEWallet {
    private double balance;

    public AmritaEWallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deductAmount(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class PaymentModule {
    public static void processPayment(AmritaEWallet amritaEWallet, Scanner scanner) {
        System.out.print("Enter the amount to be deducted from your Amrita e-Wallet: Rs. ");

        double paymentAmount = scanner.nextDouble();
        scanner.nextLine();

        if (paymentAmount > 0) {
            if (amritaEWallet.deductAmount(paymentAmount)) {
                System.out.println("Payment Successful!");
                System.out.println("Transaction ID: " + generateTransactionID());
                System.out.println("Timestamp: " + System.currentTimeMillis());
            } else {
                System.out.println("Insufficient funds in your Amrita e-Wallet.");
                System.out.println("Please add funds or modify your order.");
            }
        } else {
            System.out.println("Invalid payment amount. Please enter a valid amount greater than zero.");
        }
    }

    private static String generateTransactionID() {

        return "TXN" + System.currentTimeMillis();
    }
}

public class CanteenPaymentSystem {
    public static void start() {

        double initialWalletBalance = 1000.0;
        AmritaEWallet amritaEWallet = new AmritaEWallet(initialWalletBalance);
        Scanner scanner = new Scanner(System.in);

        double totalCost = 0.0;


        System.out.println("Welcome to the Canteen Payment System!");
        System.out.println("User's Amrita e-Wallet Balance: Rs. " + amritaEWallet.getBalance());
        PaymentModule.processPayment(amritaEWallet, scanner);


        System.out.println("Remaining Amrita e-Wallet Balance: Rs. " + amritaEWallet.getBalance());
        System.out.println("Thank you for ordering food!");


        CanteenSlotAllotmentSystem.start();
        scanner.close();
    }
}
