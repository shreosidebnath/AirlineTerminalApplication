package ca.ucalgary.ensf409.finalproject.flight;
import ca.ucalgary.ensf409.finalproject.accounting.Balance;
import ca.ucalgary.ensf409.finalproject.accounting.BankTransfer;
import ca.ucalgary.ensf409.finalproject.accounting.CashPayment;
import ca.ucalgary.ensf409.finalproject.accounting.Ticket;

import java.util.*;


public class Reservation {

    private String reservationNumber;
    private FlightInstance flightInstance;
    private Constants.TicketStatus ticketStatus;
    private Ticket correspondingTicket;
    private Balance correspondingBalance;
    private ArrayList<Seat> seats;
    private double reservationTotalFare;
    //private Map<User.Customer, Seat> flightSeatMap;

    public Reservation(){
        correspondingTicket = new Ticket();
        correspondingBalance = new Balance();
        seats = new ArrayList<Seat>();
        reservationTotalFare = 0;
    }

    public void addSeat(Seat seat){
        seats.add(seat);
        reservationTotalFare += seat.getFlightFare();
    }

    public void purchaseTicket(int selectedPaymentMethod){
        if(selectedPaymentMethod  == 1)
            correspondingTicket.setPaymentMethod(new CashPayment());
        else if(selectedPaymentMethod == 2)
            correspondingTicket.setPaymentMethod(new BankTransfer());

        boolean processPaymentSuccess = correspondingTicket.processPayment(reservationTotalFare);
        if(processPaymentSuccess) {createNewBalance(reservationTotalFare);}
    }

    public void createNewBalance(double amount){
        correspondingBalance.setAmount(amount);
    }

    public double getReservationTotalFare(){
        return this.reservationTotalFare;
    }
}
