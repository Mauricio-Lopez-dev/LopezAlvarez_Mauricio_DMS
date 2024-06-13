/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 13, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...
 */

import java.nio.file.Path;
import java.util.*;

public class AvailabilityLog extends Motorcycle
{
    // Data field
    private int userInput;
    Scanner in = new Scanner(System.in);

    public AvailabilityLog(Path path, ArrayList<Motorcycle> motorcycleArrayList)
    {
        displayStatusByFilter(path, motorcycleArrayList);
    } // end constructor

    public int getUserInput()
    {
        return userInput;
    } // end acessor method

    public void setUserInput(int userInput)
    {
        int min = 1;
        int max = 4;

        while(userInput < min || userInput > max)
        {
            System.out.println("Error: Invalid option. Please try again.");
            System.out.print("Enter option: ");
            userInput = in.nextInt();
        }

        this.userInput = userInput;
    } // end mutator method


    public void displayStatusByFilter(Path path, ArrayList<Motorcycle> motorcycleArrayList)
    {
        boolean badInput;
        String result = "";
        result += "[1] In Stock\n";
        result += "[2] Out of Stock\n";
        result += "[3] Pending\n";
        result += "[4] Sold\n";


        do
        {
            try
            {
                System.out.println("Display motorcycles by availability:");
                System.out.println(result);
                System.out.print("Enter option: ");
                userInput = in.nextInt();
                setUserInput(userInput);
                displayStatus(getUserInput());
                badInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid input. Please try again.");
                in.nextLine();
                badInput = true;
            }
        }while(badInput);
    } // end displayStatusByFilter method

   public void displayStatus(int userInput)
   {

       switch(userInput)
       {
           case 1: // Display In Stock
               break;

           case 2: // Display Out of Stock
               break;

           case 3: // Display Pending
               break;

           case 4: // Display Sold
               break;
       } // end switch

   } // end displayStatus method
} // end AvailabilityLog class
