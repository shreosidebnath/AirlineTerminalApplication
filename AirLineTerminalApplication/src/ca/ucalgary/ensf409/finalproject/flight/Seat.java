package ca.ucalgary.ensf409.finalproject.flight;
import ca.ucalgary.ensf409.finalproject.flight.Constants;

public class Seat {

    private String seatNumber;
    private int flightFare;
    private Constants.SeatType seatType;
    //private Constants.BookingStatus bookingStatus;

    public Seat (int flightFare, Constants.SeatType seatType){
        //this.seatNumber = seatNumber;
        this.flightFare = flightFare;
        this.seatType = seatType;
    }

    public int getFlightFare(){
        return this.flightFare;
    }

}
