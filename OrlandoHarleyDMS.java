/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...Client requests a Database Management System(DMS) for Orlando Harley-Davidson. Features that will be
 *            implemented in the software are adding a file and store a list of motorcycles, remove a motorcycle,
 *            update values to a motorcycle, display availability status log, and exit option to safely
 *            shut down the software. Upon running the software it will prompt the user to input the absolute path
 *            to the file the user wants to load. User's choice will be handled through a switch statement.
 */
import java.nio.file.Path;
import java.util.*;

public class OrlandoHarleyDMS
{
    // Global Variable
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Variables and Objects
        int choice;
        ArrayList<Motorcycle> motorcycleList = new ArrayList<>();
        Motorcycle obj = new Motorcycle();
        Path path = obj.locateFile();

        do
        {
            choice = getMenuOption();
            switch(choice)
            {
                case 1: // Add Motorcycle
                    if(motorcycleList.isEmpty()) {
                        motorcycleList.add(new AddMotorcycle(path));
                        displayAllMotorcycles(motorcycleList);
                    }
                    else {
                        motorcycleList.clear();
                        motorcycleList.add(new AddMotorcycle(path));
                        displayAllMotorcycles(motorcycleList);
                    }
                    break;


                case 2: // Remove Motorcycle
                    if(!motorcycleList.isEmpty())
                    {
                        System.out.println("\nCurrent list of Motorcycles:");
                        System.out.println("ID, Year, Make, Model, Price, Status");
                        displayAllMotorcycles(motorcycleList);
                        motorcycleList.clear();
                        motorcycleList.add(new RemoveMotorcycle(path));
                        displayAllMotorcycles(motorcycleList);
                    }
                    else
                    {
                        System.out.println("Motorcycle List is empty");
                        System.out.println("Use option 1 to add motorcycles to the list");
                    }
                    break;

                case 3: // Update values
                    if(!motorcycleList.isEmpty())
                    {
                        UpdateValues uV = new UpdateValues(path);
                        motorcycleList.clear();
                        motorcycleList.add(uV);
                    }
                    else
                    {
                        System.out.println("Motorcycle List is empty");
                        System.out.println("Use option 1 to add motorcycles to the list");
                    }
                    break;

                case 4: // Availability status log
                    AvailabilityLog aL = new AvailabilityLog(path);
                    break;

                case 5:  // Exit System
                    System.out.println("Thank you for using Harley-Davidson DMS");
                    break;

                default: // Error
                    System.out.println("Error: Invalid input. Please try again.");
                    break;
            }
        }while(choice != 5);
    } // end main

    /*
     * Method name: getMenuOption()
     * Purpose:...This method build the menu options and stored into a string.
     * Arguments: Zero arguments for this method.
     * Return value: The return value is an integer. To obtain the return value it will
     *               call the getInput(option) method with variable option as its passing argument.
     */
    public static int getMenuOption()
    {
        // Output - Welcoming Message
        System.out.println("********************MENU***************************");

        String option = "";
        option += "[1] Add a motorcycle to the inventory\n";
        option += "[2] Remove motorcycle from inventory\n";
        option += "[3] Update Values\n";
        option += "[4] Availability Status Log\n";
        option += "[5] Exit System\n";

        return getInput(option);
    } // end getMenuOption method

    /*
     * Method name: getInput()
     * Purpose:...This method prompts the user to enter their choice of feature.
     * Arguments: String option; the menu is displayed from the argument String option.
     * Return value: The return value is the result from choice entered by the user of type int.
     */
    public static int getInput(String option)
    {
        int choice = 0;
        boolean badInput;

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

    /*
     * Method name: displayMessage()
     * Purpose:...This methods displays a message to the user
     * Arguments: Zero arguments
     * Return value: None; no return value for this method
     */
    public static void displayMessage(String message)
    {
        System.out.println(message);
    } // end displayMessage method

    /*
     * Method name: displayAllMotorcycles()
     * Purpose:...Iterate through each object stored and call displayMessage to output all objects to the user
     * Arguments: ArrayList<Motorcycle> list
     * Return value: None; no return value for this method
     */
    public static void displayAllMotorcycles(ArrayList<Motorcycle> list)
    {
        for(Motorcycle motorcycle : list)
        {
           displayMessage(motorcycle.toString());
        }
    } // end displayAllMotorcycles method
} // end OrlandoHarleyDMS