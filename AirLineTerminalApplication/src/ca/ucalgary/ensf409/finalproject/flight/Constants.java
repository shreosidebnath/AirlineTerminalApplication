package ca.ucalgary.ensf409.finalproject.flight;

public class Constants {

    public enum EmployeeType{
        EMPLOYEE,
        ADMIN,
        CASHIER,
        CUSTOMER
    }

    public enum AirlineActiveStatus{
        Y,
        N
    }

    public enum FlightStatus{
        DEPARTED,
        ARRIVED,
        DELAYED
    }

    public enum SeatType {
        FIRST_CLASS,
        BUSINESS_CLASS,
        PREMIUM_ECONOMY_CLASS,
        ECONOMY_CLASS
    }

    public enum BookingStatus{
        BOOKED,
        OPEN
    }

    public enum TicketStatus{
        OPEN,
        BOOKED
    }

}
