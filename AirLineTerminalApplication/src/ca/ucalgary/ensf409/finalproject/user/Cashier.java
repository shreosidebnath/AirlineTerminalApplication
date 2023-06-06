package ca.ucalgary.ensf409.finalproject.user;

import java.util.Scanner;
import java.sql.*;

public class Cashier extends Employee{
    public static void cashierMenu(Scanner input, String userName) {

        System.out.print("\n\n\n-----------------------------CASHIER-SELECTION-TABLE-----------------------------\n\n\n");
        Validation.createLogID(userName+" actioned [accessing reservation table]");
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM reservations";
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
            cashierMenu(input, userName);
		}
        System.out.println("\n Please select from the following: ");
        System.out.println("(1) Pay fees\t(2) Exit");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(input.nextLine());

        switch(choice){
            case 1:
                finalizePayment( input, userName);break;
            case 2:
                UserInterface.selectionScreen();break;
            default:
                System.out.println("Invalid selection try again");
                cashierMenu(input, userName);
        }
    
    }

    public static void finalizePayment(Scanner input, String userName){
        System.out.print("Please enter the ticket number you hold: ");
        String ticketNumber = input.nextLine();

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT seatType FROM reservations WHERE ticketNumber = "+ticketNumber;
			ResultSet result = statement.executeQuery(query);
            String seat = "economyCap";
            while(result.next()){
                seat = result.getString(1);
                break;
            }
            connect.close();
            createBalance(input, userName, seat, ticketNumber);
            
			cashierMenu(input, userName);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            cashierMenu(input, userName);
		}
    }

    public static void createBalance(Scanner input, String userName, String seat, String ticketNumber){
        int price = 0;
        if(seat == "firstCap"){
            price = 5000;
        }else if(seat == "businessCap"){
            price = 2500;
        }else if(seat == "premEcoCap"){
            price = 1000;
        }else{
            price = 500;
        }
        System.out.println(seat);
        System.out.println("How would you like to pay\n(1) Bank Transfer (2) Cash");
        System.out.println("Choice: ");
        int choice = Integer.parseInt(input.nextLine());

        switch(choice){
            case 1:
                System.out.println("You have now paid your ticket via Bank Transfer for "+price);break;
            case 2:
                System.out.println("You have now paid your ticket via Bank Cash for "+price);break;
            default:
                System.out.println("Invalid selection try again");
                cashierMenu(input, userName);
        }
        try{
        Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
		Statement statement = connect.createStatement();
        String query = "UPDATE reservations SET paid = \'1\' WHERE ticketNumber = "+ticketNumber;
        statement.execute(query);
        Validation.createLogID(userName+" finalized ticket purchase for "+ticketNumber);
        connect.close();
        }catch(SQLException e) {
            System.out.println("Something went wrong: "+e.getMessage());
            cashierMenu(input, userName);
        }
    }

}
