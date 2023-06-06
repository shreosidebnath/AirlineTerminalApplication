package ca.ucalgary.ensf409.finalproject.accounting;

import java.util.*;

public class BankTransfer implements PaymentMethod{

    private String cardholderName;
    private String cardNumber;
    private Date expiryDate;

    public boolean pay(double amount){
        System.out.println("--- Bank Transfer ---");
        System.out.println("Please enter the amount you wish to transfer:");

        Scanner scn = new Scanner(System.in);
        int paymentAmount = scn.nextInt();

        if(paymentAmount == (int)amount){
            System.out.println("Payment Successful");
            return true;
        }
        //homeAddress, country, city, state, postalCode

        return false;
    }
}
