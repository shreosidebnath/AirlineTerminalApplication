package ca.ucalgary.ensf409.finalproject.user;
import java.util.*;
import java.sql.*;

public class Admin extends Employee{

    public static void adminTableSelection(Scanner input, String userName){
        System.out.print("\n\n\n---------------Admin-Table-Selection---------------\n"+
        "Please select from the following\n"+
        "Please select the table you wish to interact with"+
        "\n(1) Airlines \t(2) Airports\t(3) Countries"+
        "\n(4) Log      \t(5) Planes  \t(6) Routes"+
        "\n(7) Schedules\t(8) Users\t(9) Reservations"+
        "\n(10) Password Hasher\t(11) Logout\n\n\n"+
        "Choice: ");
        String selection = "";

        int choice = Integer.parseInt(input.nextLine());

        switch(choice){
            case 1:
                selection = "airlines";break;
            case 2:
                selection = "airports";break;
            case 3:
                selection = "countries";break;
            case 4: 
                selection = "log"; break;
            case 5:
                selection = "planes";break;
            case 6:
                selection = "routes";break;
            case 7: 
                selection = "schedules";break;
            case 8:
                selection = "users";break;
            case 9:
                selection = "reservations";break;
            case 10:
                Scanner password = new Scanner(System.in);
                System.out.print("Please enter a password: ");
                String passwordHashed = Validation.hasher(password.nextLine());
                System.out.print("Your Hashed Password is: "+passwordHashed);
                break;
            case 11:
                UserInterface.exitScreen(userName);break;
            default:
                System.out.println("Sorry that is an invlaid input please try again");
                adminTableSelection(input, userName);
        }
        if(choice == 11){
            UserInterface.selectionScreen();
        }else if(choice == 10){
            adminTableSelection(input, userName);
        }else{
            adminSelection(input, userName, selection);
        }
    }

    public static void adminSelection(Scanner input, String userName, String selection){
        System.out.print("\n\n\n---------------Admin-Selection-Menu---------------\n"+
        "Please select from the following\n"+
        "Please select if want to do the following to the records\n(1) GET/view\n(2) POST/create\n(3) PUT/update\n(4) DELETE/remove\n(5) Back\n\n"+
        "Choice: ");

        int choice = Integer.parseInt(input.nextLine());

        if(choice == 1){
            get(input, userName, selection);
        }else if(choice == 2){
            post(input, userName, selection);
        }else if(choice == 3){
            put(input, userName, selection);
        }else if(choice == 4){
            delete(input, userName, selection);
        }else if (choice == 5){
            adminTableSelection(input, userName);
        }else{
            System.out.println("That is not a valid input please try again");
            adminSelection(input, userName, selection);
        }
    }

    public static void get(Scanner input, String userName, String selection){
        Validation.createLogID(userName+" actioned [viewing table "+selection+"]");
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM "+selection;
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
			adminSelection(input, userName, selection);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            adminSelection(input, userName, selection);
		}

    }

    public static void post(Scanner input, String userName, String selection){
        Validation.createLogID(userName+" actioned [creating a new record for table "+selection+"]");
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM "+selection;
			ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnSize = metaData.getColumnCount();

            query = "INSERT INTO "+selection+" ("+metaData.getColumnLabel(1);
            for(int i = 1; i <= columnSize-1; i++){
                query+=","+metaData.getColumnLabel(i+1);
            }query+=") VALUES (?";
            for(int i = 1; i <= columnSize-1; i++){
                query+=",?";
            }query+=")";
            PreparedStatement myStatement = connect.prepareStatement(query);

            for(int i = 1; i <= columnSize; i++){
                myStatement.setString(i, metaData.getColumnLabel(i));
            } 

            System.out.println("Please input your new record values");
            for(int i = 1; i <= columnSize; i++){
                System.out.print(metaData.getColumnLabel(i)+": ");
                myStatement.setString(i, input.nextLine());
            } 

            myStatement.executeUpdate();
            connect.close();
			adminSelection(input, userName, selection);
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
            adminSelection(input, userName, selection);
		}
    }

    public static void put(Scanner input, String userName, String selection){
        Validation.createLogID(userName+" actioned [updating a new record for table "+selection+"]");

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM "+selection;
			ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnSize = metaData.getColumnCount();
            
            System.out.println("The following are columns");
            
            String text = "| "; query = "UPDATE "+selection+" SET ";
            for(int i = 1; i <= columnSize; i++){
                text += metaData.getColumnLabel(i)+" | ";
            }System.out.println(text);

            System.out.println("----The-change----");
            System.out.print("Enter the column you would like to change: ");
            query+= input.nextLine();
            System.out.print("Enter the new value intended: ");
            query+= " = \""+input.nextLine()+"\"";
            System.out.println("----Find-The-Change----");
            System.out.print("Enter the column you would like to filter for: ");
            query+= " WHERE "+input.nextLine();
            System.out.print("Enter the value you would like to filter for that corelates to the column above: ");
            query+= " = \""+input.nextLine()+"\"";
            statement.execute(query);
            System.out.println("\n\nChange Successful\n");
            adminSelection(input, userName, selection);

		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage()+" Please try your query again\n"+
            "NOTE: Please check your capitalization, spelling and spaces");
            adminSelection(input, userName, selection);
		}
    }

    public static void delete(Scanner input, String userName, String selection){
        Validation.createLogID(userName+" actioned [deleting a record for table "+selection+"]");

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT * FROM "+selection;
			ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnSize = metaData.getColumnCount();
            
            System.out.println("The following are columns");
            
            String text = "| "; query = "DELETE FROM "+selection;
            for(int i = 1; i <= columnSize; i++){
                text += metaData.getColumnLabel(i)+" | ";
            }System.out.println(text);

            System.out.println("----Find-The-Deletion-Data----");
            System.out.print("Enter the column you would like to filter for: ");
            query+= " WHERE "+input.nextLine();
            System.out.print("Enter the value you would like to filter for that correlates to the column above: ");
            query+= " = \""+input.nextLine()+"\"";
            statement.execute(query);
            System.out.println("\n\nChange Successful\n");
            adminSelection(input, userName, selection);

		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage()+" Please try your query again\n"+
            "NOTE: Please check your capitalization, spelling and spaces");
            adminSelection(input, userName, selection);
		}
    }
}

