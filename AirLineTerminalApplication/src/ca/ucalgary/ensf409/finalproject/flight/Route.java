package ca.ucalgary.ensf409.finalproject.flight;

import java.util.ArrayList;

public class Route {

    private String airline;
    private String airlineID;
    private String sourceAirport;
    private String sourceAirportID;
    private String destinationAirport;
    private String destinationAirportID;
    private String codeshare;
    private String stops;
    private String equipment;

    public Route(ArrayList<String> routeInfo){
        this.airline = routeInfo.get(0);
        this.airlineID = routeInfo.get(1);
        this.sourceAirport = routeInfo.get(2);
        this.sourceAirportID = routeInfo.get(3);
        this.destinationAirport = routeInfo.get(4);
        this.destinationAirportID = routeInfo.get(5);
        this.codeshare = routeInfo.get(6);
        this.stops = routeInfo.get(7);
        this.equipment = routeInfo.get(8);
    }

    public void display(){
        System.out.println("Airline: " + this.airline + ", AirlineID: " + this.airlineID + ", sourceAirport: " + this.sourceAirport + ", sourceAirportID: " + this.sourceAirportID +
          ", destinationAirport: " + this.destinationAirport + ", destinationAirportID: " + this.destinationAirportID + ", codeshare: " + this.codeshare + ", stops: " +
          this.stops + ", equipment: " + this.equipment
        );
    }

    public String getAirline(){
        return this.airline;
    }

    public String getAirlineID(){
        return this.airlineID;
    }

    public String getSourceAirport(){
        return this.sourceAirport;
    }
}
