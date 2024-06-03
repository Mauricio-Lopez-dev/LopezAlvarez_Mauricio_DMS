/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...
 */


import java.time.Year;
import java.util.*;

public class AddMotorcycle extends Motorcycle implements AvailabilityStatusLog
{
    // Data Fields
    enum brandAbbrevation {HD, KAW, HON, SUZ, BMW, IND, DUC };

    int year;
    int make;
    String model;
    int category;
    double price;
    String modelNumber;
    String[] availabilityStatus;
    Scanner in = new Scanner(System.in);


    public AddMotorcycle()
    {
        createMotorcycle();
    } // end default constructor

    public int getYear()
    {
        return year;
    } // end accessor method

    public void setYear(int year)
    {
        // Constants
        final int minYear = 1990;
        final int maxYear = Year.now().getValue();

        while(year < minYear || year > maxYear)
        {
            System.out.println("Year must be within range from " + minYear + " to " + maxYear + ": ");
            System.out.print("Enter year:");
            year = in.nextInt();
        } // end while
        this.year = year;
    } // end mutator method

    public int getMake()
    {
        return make;
    } // end accessor method

    public void setMake(int make)
    {
        // Constants
        final int minMakeOption = 1;
        final int maxMakeOption = 7;

        while(make < minMakeOption || make > maxMakeOption)
        {
            System.out.println("Error: not an option. Please try again...");
            System.out.print("Enter make option: ");
            make = in.nextInt();
        } // end while
        this.make = make;
    } // end mutator method

    public String getModel()
    {
        return model;
    } // end accessor method

    public void setModel(String model)
    {

        while(model.length() == 0)
        {
            System.out.println("Error: Cannot be null. Please try again...");
            System.out.print("Enter model: ");
            model = in.nextLine();
        }

        // Capitalization - First Letter
        String[] array = model.split(" ");
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < array.length; i++)
        {
            temp.append(Character.toUpperCase(array[i].charAt(0)))
                    .append(array[i].substring(1)).append(" ");
        }
        model = temp.toString();

        this.model = model;
    } // end mutator method

    public int getCategory()
    {
        return category;
    } // end accessor method

    public void setCategory(int category)
    {
        this.category = category;
    } // end mutator method

    public double getPrice()
    {
        return price;
    } // end accessor method

    public void setPrice(double price)
    {
        this.price = price;
    } // end mutator method

    public String getModelNumber()
    {
        return modelNumber;
    } // end accessor method

    public void setModelNumber(String modelNumber)
    {
        this.modelNumber = modelNumber;
    } // end mutator method

    public String[] getAvailabilityStatus()
    {
        return availabilityStatus;
    } // end accessor method

    public void setAvailabilityStatus(String[] availabilityStatus)
    {
        this.availabilityStatus = availabilityStatus;
    } // end mutator method

    public String toString()
    {
        String result = "";


        return result;
    } // end toString method

    public void createMotorcycle()
    {
        boolean badInput = true;
        do
        {
            try
            {
                // Input - Year
                System.out.println("Year range acceptance 1990 - current year");
                System.out.print("Enter the year: ");
                year = in.nextInt();
                setYear(year);

                // Input - Make
                System.out.println("Selection of Brands:");
                setMake(getOption());

                // Input - Model
                in.nextLine();
                System.out.print("Enter the model: ");
                model = in.nextLine();
                setModel(model);
                //  System.out.println("Model: " + getModel()); May Delete SOON

                // Input - Category

                // Input - Price

                // Input - Model Number

                // Input - Availability status

                badInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid Input. Try again...");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
    } // end createMotorcycle method

    public int getOption()
    {
        String option = "";
        option += "[1] " + brandAbbrevation.HD  + " (Harley-Davidson)\n";
        option += "[2] " + brandAbbrevation.KAW + " (Kawasaki)\n";
        option += "[3] " + brandAbbrevation.HON + " (Honda)\n";
        option += "[4] " + brandAbbrevation.SUZ + " (Suzuki)\n";
        option += "[5] " + brandAbbrevation.BMW + " (Bayerische Motoren Werke)\n";
        option += "[6] " + brandAbbrevation.IND + " (Indian)\n";
        option += "[7] " + brandAbbrevation.DUC + " (Ducati)\n";

        return getUserInput(option);
    } // end getMakeOption method

    public int getUserInput(String option)
    {
        boolean badInput = true;

        do
        {
            try
            {
                System.out.print(option + "Enter your option: ");
                make = in.nextInt();
                badInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid Input. Try again...");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
        return make;
    } // end getUserInput method


} // end AddMotorcycle class
