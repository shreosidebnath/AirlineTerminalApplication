package ca.ucalgary.ensf409.finalproject.accounting;

import ca.ucalgary.ensf409.finalproject.flight.Constants;

public class Ticket {

    private Constants.TicketStatus ticketStatus = Constants.TicketStatus.OPEN;
    private PaymentMethod paymentMethod;
    private Notification notification;

    public boolean processPayment(double flightFare){
        boolean processPaymentSuccess = paymentMethod.pay(flightFare);
        if(processPaymentSuccess){
            createNotification();
        }
        return processPaymentSuccess;
    }

    public void createNotification(){
        notification = new Notification().sendNotification();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }

}
