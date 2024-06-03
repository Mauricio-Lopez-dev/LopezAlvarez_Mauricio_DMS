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
    int year;
    int make;
    String model;
    int category;
    double price;
    String modelNumber;
    String[] availabilityStatus;
    Scanner in = new Scanner(System.in);
    enum brandAbbrevation {HD, KAW, HON, SUZ, BMW, IND, DUC };

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
        final int minYear = 1990;
        final int maxYear = Year.now().getValue();

        while(year < minYear || year > maxYear)
        {
            System.out.println("Year must be within range from " + minYear + " to " + maxYear + ": ");
            System.out.print("Enter year:");
            year = in.nextInt();
        }
        this.year = year;
    } // end mutator method

    public int getMake()
    {
        return make;
    } // end accessor method

    public void setMake(int make)
    {
        this.make = make;
    } // end mutator method

    public String getModel()
    {
        return model;
    } // end accessor method

    public void setModel(String model)
    {
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

    public void createMotorcycle()
    {
        boolean badInput = true;
        do
        {
            try
            {
                // Input
                System.out.println("Year range acceptance 1990 - current year");
                System.out.print("Enter the year: ");
                year = in.nextInt();
                setYear(year);

                setMake(getMakeOption());


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

    public int getMakeOption()
    {
        System.out.println("List of Brands:");
        String option = "";

        option += "[1] " + brandAbbrevation.HD  + " (Harley-Davidson)\n";
        option += "[2] " + brandAbbrevation.KAW + " (Kawasaki)\n";
        option += "[3] " + brandAbbrevation.HON + " (Honda)\n";
        option += "[4] " + brandAbbrevation.SUZ + " (Suzuki)\n";
        option += "[5] " + brandAbbrevation.BMW + " (Bayerische Motoren Werke)\n";
        option += "[6] " + brandAbbrevation.IND + " (Indian)\n";
        option += "[7] " + brandAbbrevation.DUC + " (Ducati)\n";

        return getUserInput(option);
    }

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
            }
        }while(badInput);
        return make;
    } // end getInput method



}
