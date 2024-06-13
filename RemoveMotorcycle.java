/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...Creates the functionality of removing a motorcycle from the file. Prompts the user to input
 *            a choice of how to locate the motorcycle. Option selected by the user will search the motorcycle
 *            with its corresponding attributes.
 */
import java.time.Year;
import java.util.*;
import java.nio.file.*;

public class RemoveMotorcycle extends Motorcycle
{
    // Data Fields
    static Scanner in = new Scanner(System.in);
    private int choice;
    private int year;
    private int motorcycleID;

    public RemoveMotorcycle(Path path)
    {
        removeOption(path);
    } // end default constructor

    public int getChoice()
    {
        return choice;
    } //end accessor method

    public void setChoice(int choice)
    {
        int min = 1;
        int max = 2;

        while (choice < min || choice > max)
        {
            System.out.println("Error: Invalid option");
            System.out.print("Please enter an option: ");
            choice = in.nextInt();
        } // end while loop

        this.choice = choice;
    } // end mutator method

    public int getYear()
    {
        return year;
    } // end accessor method

    public void setYear(int year)
    {
        int maxYear = 1990;
        while(year > Year.now().getValue() || year < maxYear)
        {
            System.out.println("Error: Invalid option");
            System.out.println("Enter numeric model number: ");
            year = in.nextInt();
        }
        this.year = year;
    } // end mutator method

    public int getMotorcycleID()
    {
        return motorcycleID;
    } // end accessor method

    public void setMotorcycleID(int motorcycleID)
    {
        while (motorcycleID < 1 || motorcycleID > 30)
        {
            System.out.println("Error: Invalid option");
            System.out.print("Enter motorcycle ID: ");
            motorcycleID = in.nextInt();
        }
        this.motorcycleID = motorcycleID;
    } // end mutator method

    /*
     * Method name: removeOption()
     * Purpose:...Prompts the user to choose an option of how to locate the motorcycle. Based on the option it
     *            prompt the user to input the id or id and year of motorcycle.
     * Arguments: Path path
     * Return value: None; no return value for this method
     */
    public void removeOption(Path path)
    {
        // Variables
        boolean badInput;

        do
        {
            try
            {
                String option = "How would you like to remove the motorcycle?\n";
                option += "[1]. ID number\n";
                option += "[2]. ID number & Year";

                System.out.println(option);
                System.out.print("Enter option: ");
                choice = in.nextInt();
                setChoice(choice);

                System.out.print("Enter motorcycle ID: ");
                motorcycleID = in.nextInt();
                setMotorcycleID(motorcycleID);

                if(getChoice() == 2)
                {
                    System.out.print("Enter the year:");
                    year = in.nextInt();
                    setYear(year);
                }

                switch(getChoice())
                {
                    case 1: // Remove object by ID
                        removeObject(path, getMotorcycleID());
                        break;

                    case 2: // Remove by ID and Year
                        removeObject(path, getMotorcycleID(), getYear());
                        break;
                } // end switch
                badInput = false;
            }
            catch (Exception e)
            {
                System.out.println("Error: Invalid input. Please try again...");
                badInput = true;
                in.nextLine();
            }
        }while (badInput);
    } // end removeMotorcycle method
} // end RemoveMotorcycle class