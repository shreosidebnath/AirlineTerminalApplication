package ca.ucalgary.ensf409.finalproject.user;
import java.util.*;
import java.security.*;
import java.sql.*;
import java.time.*;

public class Validation {
    

    public static String hasher(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();

            for(byte b : resultByteArray){
                stringBuilder.append((String.format("%02x", b)));
            }

            return stringBuilder.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        System.out.println("ERROR: YOUR PASSWORD WAS SET AS NOTHING");
        return "";
    }

    public static boolean verifyAdmin(String userName, String password){
        boolean fate = false;
        password = hasher(password);
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT Admin FROM users WHERE username = \""+userName+"\" AND password = \""+password+"\"";
			ResultSet result = statement.executeQuery(query);

            while(result.next()){
                String verify = result.getString(1);
                fate = Integer.parseInt(verify)==1;
                break;
            }
            connect.close();
			
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
		}

        return fate;
    }

    public static void createLogID(String action){
        final String nano = String.valueOf(LocalDateTime.now().getNano());
        final String second = String.valueOf(LocalDateTime.now().getSecond());
        final String minute = String.valueOf(LocalDateTime.now().getMinute());
        final String hour = String.valueOf(LocalDateTime.now().getHour());
        final String day = String.valueOf(LocalDateTime.now().getDayOfMonth());
        final String month = String.valueOf(LocalDateTime.now().getMonthValue());
        final String year = String.valueOf(LocalDateTime.now().getYear());

        String logId = "N"+nano+":S"+second+":M"+minute+":H"+hour+":D"+day+":M"+month+":Y"+year+"A";

        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
            String query = "INSERT INTO log (logId, action, time, date) VALUES (?,?,?,?)";

            PreparedStatement myStatement = connect.prepareStatement(query);
			myStatement.setString(1, logId);
            myStatement.setString(2, action);
            myStatement.setString(3, hour+":"+minute+":"+second);
            myStatement.setString(4, month+", "+day+"/"+year);
            myStatement.executeUpdate();

            connect.close();
			
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
		}

        
    }

    public static boolean verifyEmployee(String userName, String password){
        boolean fate = false;
        password = hasher(password);
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT employee FROM users WHERE username = \""+userName+"\" AND password = \""+password+"\"";
			ResultSet result = statement.executeQuery(query);

            while(result.next()){
                String verify = result.getString(1);
                fate = Integer.parseInt(verify)==1;
                break;
            }
            connect.close();
			
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
		}

        return fate;
    }

    public static boolean verifyCashier(String userName, String password) {
        boolean fate = false;
        password = hasher(password);
        try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:APTicketDB.db");
			Statement statement = connect.createStatement();
            String query = "SELECT cashier FROM users WHERE username = \""+userName+"\" AND password = \""+password+"\"";
			ResultSet result = statement.executeQuery(query);

            while(result.next()){
                String verify = result.getString(1);
                fate = Integer.parseInt(verify)==1;
                break;
            }
            connect.close();
			
		}catch(SQLException e) {
			System.out.println("Something went wrong: "+e.getMessage());
		}

        return fate;
    }

}

