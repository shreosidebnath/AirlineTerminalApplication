package ca.ucalgary.ensf409.finalproject.user;

import java.util.Scanner;
import ca.ucalgary.ensf409.finalproject.flight.*;
import ca.ucalgary.ensf409.finalproject.flight.Constants.SeatType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Employee extends Person{
    private String username;
    private String password;


    public static void employeeSelection(Scanner input, String userName){
        System.out.print("\n\n\n---------------EMPLOYEE-TABLE-SELECTION---------------\n"+
        "Please select from the following\n"+
        "Please select the table you wish to interact with"+
        "\n(1) Search for Ticket \t(2) Reserve Ticket\t(3) Print Ticket"+
        "\n(4) Logout\n\n\n"+
        "Choice: ");
        String selection = "";

        int choice = Integer.parseInt(input.nextLine());
        String reservationID;

        switch(choice){
            case 1:    
                searchTicket(userName, input);break;
            case 2:
                reserveTicket(userName, input);break;
            case 3:
                printTicket(userName, input);break;
            case 4: 
                UserInterface.exitScreen(userName);
                UserInterface.selectionScreen();break;
            default:
                System.out.println("Sorry that is an invalid input please try again");
                employeeSelection(input, userName);

        }if(choice == 4){
            UserInterface.exitScreen(userName);
        }else{
            employeeSelection(input, userName);
        }
    }

    public void searchFlights(String inputSourceAirport, String inputDestAirport){
        ArrayList<Route> routes = new ArrayList<Route>();
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        ArrayList<Plane> planes = new ArrayList<Plane>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM routes WHERE sourceAirport = \""+inputSourceAirport+"\" AND destinationAirport = \""+inputDestAirport+"\"";
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            ArrayList<String> stringToConvert = new ArrayList<String>();
            while (result.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    String valueOfColumn = result.getString(i);
                    stringToConvert.add(valueOfColumn);
                }
                System.out.println("");
                Route route = new Route(stringToConvert);
                routes.add(route);
            }
            connect.close();

        }catch(SQLException e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }

        for(int i=0; i < routes.size(); i++){
            System.out.print(i+1 + " --- ");
            routes.get(i).display();
        }


        System.out.println("\nPlease choose which ROUTE you would like to book:");

        Scanner scn = new Scanner(System.in);
        int selectedRoute = scn.nextInt();
        System.out.println(selectedRoute);

        String scheduleValue = routes.get(selectedRoute-1).getAirline() + routes.get(selectedRoute-1).getAirlineID() + routes.get(selectedRoute-1).getSourceAirport();

        try {
            Connection connect = DriverManager.getConnection("jdbc:sqlite:/Users/aasimag/Documents/FlightAgencyManager.db");
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM schedule WHERE flightNumber = \""+scheduleValue+"\"";
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            ArrayList<String> stringToConvert = new ArrayList<String>();
            while (result.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    String valueOfColumn = result.getString(i);
                    stringToConvert.add(valueOfColumn);
                }
                System.out.println("");
                Schedule schedule = new Schedule(stringToConvert);
                schedules.add(schedule);
            }
            connect.close();

        }catch(SQLException e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }

        for(int i=0; i < schedules.size(); i++){
            System.out.print(i+1 + " --- ");
            schedules.get(i).display();
        }

        System.out.println("Please choose which FLIGHT you would like to book:");
        int selectedPlane = scn.nextInt();
        //System.out.println(selectedTime);

        String planeValue = schedules.get(selectedPlane-1).getAircraftType();
        //System.out.println(scheduleValue);

        try {
            Connection connect = DriverManager.getConnection("jdbc:sqlite:/Users/aasimag/Documents/FlightAgencyManager.db");
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM planes WHERE IATA = \""+planeValue+"\"";
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            ArrayList<String> stringToConvert = new ArrayList<String>();
            while (result.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    String valueOfColumn = result.getString(i);
                    stringToConvert.add(valueOfColumn);
                }
                System.out.println("");
                Plane plane = new Plane(stringToConvert);
                planes.add(plane);
            }
            connect.close();

            for(int i=0; i < planes.size(); i++){
                System.out.print(i+1 + " --- ");
                planes.get(i).display();
            }

        }catch(SQLException e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }

        System.out.println("Please choose which SEAT TYPE you would like to book:");
        System.out.println("1 - First Class");
        System.out.println("2 - Business Class");
        System.out.println("3 - Premium Economy Class");
        System.out.println("4 - Economy Class");
        int selectedSeatType = scn.nextInt();

        System.out.println("Please choose how many PASSENGERS you are booking for:");
        int selectedNumberOfSeats = scn.nextInt();

        int numOfSeats = 0;
        Constants.SeatType seatType = Constants.SeatType.FIRST_CLASS;
        switch(selectedSeatType) {
            case 1:
                numOfSeats = planes.get(0).getFirstCap();
                seatType = Constants.SeatType.FIRST_CLASS;
                break;
            case 2:
                numOfSeats = planes.get(0).getBusinessCap();
                seatType = Constants.SeatType.BUSINESS_CLASS;
                break;
            case 3:
                numOfSeats = planes.get(0).getPremEcoCap();
                seatType = Constants.SeatType.PREMIUM_ECONOMY_CLASS;
                break;
            case 4:
                numOfSeats = planes.get(0).getEconomyCap();
                seatType = Constants.SeatType.ECONOMY_CLASS;
                break;
        }

        int remainingSeats = numOfSeats - selectedNumberOfSeats;
        //CHANGE DB TO REFLECT REMAINING SEATS

        createReservation(selectedNumberOfSeats, seatType);

    }

    public void createReservation(int selectedNumberOfSeats, Constants.SeatType seatType){
        Reservation customerReservation = new Reservation();

        for(int i=0; i < selectedNumberOfSeats; i++){
            Seat seat = new Seat(1000, seatType);
            customerReservation.addSeat(seat);
        }

        System.out.println("Your total fare is $" + customerReservation.getReservationTotalFare() + ". Please select how you would like to pay:");
        System.out.println("1. Cash Payment");
        System.out.println("2. Bank Transfer");

        Scanner scn = new Scanner(System.in);
        int selectedPaymentMethod = scn.nextInt();

        customerReservation.purchaseTicket(selectedPaymentMethod);
    }

    public void updateReservation(){

    }

    public void cancelReservation(){

    }

    public static void searchTicket(String userName, Scanner input){
        System.out.println("Please enter the ticket you would like to search for");
        System.out.print("Reservation #: ");
        String reservationID = input.nextLine();
        Validation.createLogID(userName+" actioned [search ticket "+reservationID+"]");

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM reservations WHERE reservationNumber = \'"+reservationID+"\'";
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
			employeeSelection(input, userName);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            employeeSelection(input, userName);
		}

    }

    public static void reserveTicket(String userName, Scanner input){
        Schedule.displaySchedule(userName, input);
        System.out.println("Please select the type of seat you would like to book: ");
        System.out.print("(1) First Class\t(2) Business Class\t(3) Premium Economy Class\n(4) Economy Class\t(5) Exit");
        System.out.print("\nChoice: ");
        int choice = Integer.parseInt(input.nextLine());
        String seatType = "economyCap";
        Validation.createLogID(userName+" actioned [booking ticket]");
        switch(choice){
            case 1:
                seatType = "firstCap";break;
            case 2: 
                seatType = "businessCap";break;
            case 3:
                seatType = "premEcoCap";break;
            case 4: 
                seatType = "economyCap";break;
            case 5:
                Validation.createLogID(userName+" actioned [booking ticket FAILED]");
                employeeSelection(input, userName);break;
            default:
                Validation.createLogID(userName+" actioned [booking ticket FAILED]");
                System.out.println("Sorry that is an invalid input please try again");
                employeeSelection(input, userName);
        }
            bookTicket(userName, input, seatType);
         
    }

    public static void bookTicket(String userName, Scanner input, String seatType){
        System.out.print("Enter flight number: ");
        String flightNumber = input.nextLine();
        
        try {
            Validation.createLogID(userName+" actioned [booking ticket "+flightNumber+"]");
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT "+seatType+" FROM schedules WHERE scheduleID = "+flightNumber;
			ResultSet result = statement.executeQuery(query);
            boolean fate = false;
            int ammount = 0;

            while(result.next()){
                ammount = Integer.parseInt(result.getString(1));
                fate = (ammount > 0);
                break;
            }
            if(fate){
                statement = connect.createStatement();
                query = "UPDATE schedules SET "+seatType+" = \'"+(ammount-1)+"\' WHERE scheduleID = \'"+flightNumber+"\'";
                statement.execute(query);

                final String second = String.valueOf(LocalDateTime.now().getSecond());
                final String minute = String.valueOf(LocalDateTime.now().getMinute());
                final String hour = String.valueOf(LocalDateTime.now().getHour());
                final String day = String.valueOf(LocalDateTime.now().getDayOfMonth());
                final String month = String.valueOf(LocalDateTime.now().getMonthValue());
                final String year = String.valueOf(LocalDateTime.now().getYear());

                String number = second+minute+hour+day+month+year;
                statement = connect.createStatement();
                query = "INSERT INTO reservations (scheduleID, paid, seatType, ticketNumber) VALUES (\'"+flightNumber+"\',0,\'"+seatType+"\',\'"+number+"\')";
                statement.execute(query);
                connect.close();
            }else{
                System.out.println("Sorry, "+seatType+" is fully booked please try booking with another seat type");
            }
			employeeSelection(input, userName);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            employeeSelection(input, userName);
		}
    }

    

    public static void printTicket(String userName, Scanner input){
        System.out.println("Please enter the ticket you would like to search for");
        System.out.print("Ticket Number #: ");
        String reservationID = input.nextLine();
        Validation.createLogID(userName+" actioned [printing ticket "+reservationID+"]");

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT seatType FROM reservations WHERE ticketNumber = \'"+reservationID+'\'';
			ResultSet result = statement.executeQuery(query);
            String seatType = "NULL";
            while(result.next()){
                seatType = result.getString(1);
                break;
            }

            query = "SELECT * FROM schedules";
            result = statement.executeQuery(query);

            ResultSetMetaData metaData = result.getMetaData();
            int columnSize = metaData.getColumnCount();
            
            ArrayList<String> columns = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            
            for(int i = 1; i <= columnSize-4; i++){
                columns.add(metaData.getColumnLabel(i));
            } System.out.println();

            while(result.next()){
                for(int i = 1; i <= columnSize-4; i++){
                    values.add(result.getString(i));
                }
            }

            query = "SELECT paid FROM reservations WHERE ticketNumber = \'"+reservationID+'\'';
			result = statement.executeQuery(query);
            String paid = "NULL";
            while(result.next()){
                paid = result.getString(1);
                break;
            }

            System.out.println("\n\n\n---------------TICKET---------------\n");
            System.out.println("Ticket Number :"+reservationID);
            for(int i = 0; i <= columns.size()-1; i++){
                System.out.println(columns.get(i)+" : "+values.get(i));
            }System.out.println("Seat type : "+seatType);
            System.out.println("Paid (1 YES | 0 NO) : "+paid);
            System.out.println("\n------------------------------------");
            connect.close();
			employeeSelection(input, userName);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            employeeSelection(input, userName);
		}

   }
}




