package ca.ucalgary.ensf409.finalproject.flight;

import java.lang.reflect.Array;
import ca.ucalgary.ensf409.finalproject.user.*;
import  java.util.*;
import java.sql.*;

public class Schedule {
    private String sourceAirport;
    private String destinationAirport;
    private String start;
    private String end;
    private String daysOfOperation;
    private String departure;
    private String arrival;
    private String flightNumber;
    private String aircraftType;
    private String flightDuration;

    public Schedule(ArrayList<String> scheduleInfo){
        this.sourceAirport = scheduleInfo.get(0);
        this.destinationAirport = scheduleInfo.get(1);
        this.start = scheduleInfo.get(2);
        this.end = scheduleInfo.get(3);
        this.daysOfOperation = scheduleInfo.get(4);
        this.departure = scheduleInfo.get(5);
        this.arrival = scheduleInfo.get(6);
        this.flightNumber = scheduleInfo.get(7);
        this.aircraftType = scheduleInfo.get(8);
        this.flightDuration = scheduleInfo.get(9);
    }

    public void display(){
        System.out.println("sourceAirport: " + this.sourceAirport + ", destinationAirport: " + this.destinationAirport + ", start: " + this.start + ", end: " + this.end +
          ", daysOfOperation: " + this.daysOfOperation + ", departure: " + this.departure + ", arrival: " + this.arrival + ", flightNumber: " +
          this.flightNumber + ", aircraftType: " + this.aircraftType + ", flightDuration: " + this.flightDuration
        );
    }

    public String getAircraftType(){
        return this.aircraftType;
    }

    public static void displaySchedule(String userName, Scanner input){
        System.out.println("\n\n\n---------------SCHEDUALE---------------\n");
        System.out.println("SEAT TYPES: FIRST_CLASS\nBUSINESS_CLASS\nPREMIUM_ECONOMY_CLASS\nECONOMY_CLASS\n\n\nSCHEDULE\n");
        Validation.createLogID(userName+" actioned [viewing table Scheduale]");
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM schedules";
			ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnSize = metaData.getColumnCount();

            for(int i = 1; i <= columnSize; i++){
                System.out.print(metaData.getColumnLabel(i)+" | ");
            } System.out.println();

            while(result.next()){
                for(int i = 1; i <= columnSize; i++){
                    System.out.print(result.getString(i)+" | ");
                }System.out.println();
            }
            connect.close();
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            Employee.employeeSelection(input, userName);
		}

    }
    }

