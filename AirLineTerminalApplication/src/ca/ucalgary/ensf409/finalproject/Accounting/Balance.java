package ca.ucalgary.ensf409.finalproject.accounting;

public class Balance {
    private double amount;

    public void updateTotalFlightAgencyBalance(double amount){
        this.amount += amount;
        System.out.println("The Flight Agency's total balance is: " + this.amount);
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
}


