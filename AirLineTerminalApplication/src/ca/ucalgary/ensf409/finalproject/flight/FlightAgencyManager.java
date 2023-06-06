package ca.ucalgary.ensf409.finalproject.flight;

import ca.ucalgary.ensf409.finalproject.user.*;
import ca.ucalgary.ensf409.finalproject.flight.*;
import ca.ucalgary.ensf409.finalproject.accounting.*;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlightAgencyManager {

    private ArrayList<Employee> employees;
    private ArrayList<Reservation> reservations;
    private Balance totalFlightAgencyBalance;


    public void initiateFlightAgency(){
        employees = new ArrayList<Employee>();
        reservations = new ArrayList<Reservation>();
        totalFlightAgencyBalance = new Balance();

    }


    public void updateTotalFlightAgencyBalance(Reservation reservation){
        totalFlightAgencyBalance.updateTotalFlightAgencyBalance(reservation.getReservationTotalFare());
    }

    public static void main(String[] args){

        System.out.println("---in main---");
        Employee customer = new Employee();
        customer.searchFlights("ASF", "KZN");

    }

}
