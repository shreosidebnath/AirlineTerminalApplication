package ca.ucalgary.ensf409.finalproject.user;
import java.util.Scanner;

public class UserInterface {
    public static void selectionScreen(){
        Scanner input = new Scanner(System.in);
        int choice;
        System.out.print("\n\n\n-----------------------------MAIN-MENU-----------------------------\n\n\n");
        System.out.print("Hello welcome to the main menu. Please select which type of user you are:\n");
        System.out.print("1) Customer Booking\t2) Administrator\t3) Cashier\n4) Exit\nChoice: ");
        
        try{
            choice = Integer.parseInt(input.nextLine());
            boolean flag;
            String userName, password;
            switch(choice){
                case 1:
                    System.out.print("\n\n\n-----------------------------EMPLOYEE-LOGIN-----------------------------\n\n\nPlease log in\n");
                    System.out.print("Username: ");
                    userName = input.nextLine();
                    System.out.print("Password: ");
                    password = input.nextLine();

                    flag = Validation.verifyEmployee(userName, password);
                    if(flag){
                        System.out.println("\nLOG IN SUCCESFUL");
                        employeeMenu(input, userName);
                    }else{
                        System.out.println("\nLOG IN FAILED, PLEASE RETURN TO MAIN MENU");
                        selectionScreen();
                    }
                    break;
                case 2:
                    System.out.print("\n\n\n-----------------------------ADMIN-LOGIN-----------------------------\n\n\nPlease log in\n");
                    System.out.print("Username: ");
                    userName = input.nextLine();
                    System.out.print("Password: ");
                    password = input.nextLine();

                    flag = Validation.verifyAdmin(userName, password);
                    if(flag){
                        System.out.println("\nLOG IN SUCCESFUL");
                        adminMenu(input, userName);
                    }else{
                        System.out.println("\nLOG IN FAILED, PLEASE RETURN TO MAIN MENU");
                        selectionScreen();
                    }
                    break;
                case 3:
                    System.out.print("\n\n\n-----------------------------CASHIER-LOGIN-----------------------------\n\n\nPlease log in\n");
                    System.out.print("Username: ");
                    userName = input.nextLine();
                    System.out.print("Password: ");
                    password = input.nextLine();

                    flag = Validation.verifyCashier(userName, password);
                    if(flag){
                        System.out.println("\nLOG IN SUCCESFUL");
                        cashierMenu(input, userName);
                    }else{
                        System.out.println("\nLOG IN FAILED, PLEASE RETURN TO MAIN MENU");
                        selectionScreen();
                    }
                        break;
                case 4: 
                    UserInterface.exitScreen("No Logged in User");break;
                default:
                    //TODO
                    

            }
        }catch(Exception e){
            System.out.println("ERROR: Please only input a valid number only\nPLEASE TRY AGAIN ");
            selectionScreen();
        }

        
        System.out.println("\n\n\n");
    }

    public static void exitScreen(String userName){
        System.out.print("\n\n\n-----------------------------EXIT-----------------------------\n\n\n");
        System.out.println("Thank you for using Airline Terminal Application\nYOU HAVE BEEN LOGGED OUT");
        Validation.createLogID(userName+" LOGGED OUT");

    }

    public static void adminMenu(Scanner input, String userName){
        System.out.print("\n\n\n-----------------------------ADMIN-MENU-----------------------------\n\n\n");
        System.out.print("Welcome to the Admin Menu\n");
        Admin.adminTableSelection(input, userName);
    }

    public static void employeeMenu(Scanner input, String userName){
        System.out.print("\n\n\n-----------------------------EMPLOYEE-MENU-----------------------------\n\n\n");
        System.out.print("Welcome to the Employee Menu\n");
        Employee.employeeSelection(input, userName);
    }

    public static void cashierMenu(Scanner input, String userName){
        System.out.print("\n\n\n-----------------------------CASHIER-MENU-----------------------------\n\n\n");
        System.out.print("Welcome to the Cashier Menu\n");
        Cashier.cashierMenu(input, userName);
    }
}
