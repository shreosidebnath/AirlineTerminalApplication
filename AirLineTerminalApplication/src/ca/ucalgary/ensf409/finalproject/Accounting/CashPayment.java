package ca.ucalgary.ensf409.finalproject.accounting;

import java.util.Scanner;

public class CashPayment implements PaymentMethod{

    public boolean pay(double amount){
        System.out.println("--- Cash Payment ---");
        System.out.println("Please enter the amount you wish to pay in cash:");

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
