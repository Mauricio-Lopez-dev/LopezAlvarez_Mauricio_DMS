/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...
 */
import java.util.*;

public class OrlandoHarleyDMS
{
    // Global Variable
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Variables
        int choice = 0;

        do
        {
            choice = getMenuOption();
            switch(choice)
            {
                case 1:
                    // Add Motorcycle
                    AddMotorcycle newMotor = new AddMotorcycle();
                    break;

                case 2:
                    // Remove Motorcycle
                    break;

                case 3:
                    // Update values
                    break;

                case 4:
                    // Availability status log
                    break;

                case 5:  // Exit System
                    System.out.println("Thank you for using Harley-Davidson DMS");
                    break;

                default:
                    System.out.println("Error: Invaild input. Please try again.");
                    break;
            }
        }while(choice != 5);
    } // end main

    public static int getMenuOption()
    {
        // Output - Welcoming Message
        System.out.println("\t\tWelcome to Orlando Harley-Davison DMS");
        System.out.println(" **************************************************");

        String option = "";
        option += "[1] Add a motorcycle to the inventory\n";
        option += "[2] Remove motorcycle from inventory\n";
        option += "[3] Update Values\n";
        option += "[4] Availability Status Log\n";
        option += "[5] Exit System\n";

        return getInput(option);
    } // end getMenuOption method

    public static int getInput(String option)
    {
        int choice = 0;
        boolean badInput = true;

        do
        {
            try
            {
                System.out.print(option + "Enter your option: ");
                choice = in.nextInt();
                badInput = false;
            }
            catch (Exception e)
            {
                System.out.println("Error: Invalid input");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
        return choice;
    } // end getInput method

} // end OrlandoHarleyDMS