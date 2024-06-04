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
    enum brandAbbreviation {HD, KAW, HON, SUZ, BMW, IND, DUC }
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
        StringBuilder temp = new StringBuilder();
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

        while (category < 1 || category > 4)
        {
            System.out.println("Error: Not an option. Please try again...");
            System.out.print("Enter category: ");
            category = in.nextInt();
        }
        this.category = category;
    } // end mutator method

    public double getPrice()
    {
        return price;
    } // end accessor method

    public void setPrice(double price)
    {
        final double minPriceOption = 1000.00;
        final double maxPriceOption = 90000.00;

        while(price < minPriceOption || price > maxPriceOption)
        {
            System.out.printf("Error: Invalid price. Price must be within range from $%.2f to $%.2f\n", minPriceOption, maxPriceOption);
            System.out.print("Enter price: ");
            price = in.nextDouble();
        }
        this.price = price;
    } // end mutator method

    public String getModelNumber()
    {
        return modelNumber;
    } // end accessor method

    public void setModelNumber(String modelNumber)
    {
       //int temp2 = Integer.parseInt(modelNumber.replaceAll("[^0-9]", " "));
        int temp = Integer.parseInt(modelNumber);
        int minRange = 1;
        int maxRange = 999;
        String formatterStr;
        String tempStr = "";

        while(temp < minRange || temp > maxRange)
        {
            System.out.println("Error: Invalid model number. Please try again...");
            System.out.print("Enter model number: ");
            modelNumber = in.nextLine();
        }

        if(temp <= 9)
        {
            formatterStr = String.format("%03d", temp);
            tempStr = formatterStr;
        }
        else if(temp > 9 && temp <= 99)
        {
            formatterStr = String.format("%03d", temp);
            tempStr = formatterStr;
        }
        else
        {
            tempStr = modelNumber;
        }

        switch(getMake()) {
            case 1: // HD
                modelNumber = brandAbbreviation.HD.toString() + tempStr;
                break;
            case 2: // KAW
                modelNumber = brandAbbreviation.KAW.toString() + tempStr;
                break;
            case 3: // HON
                modelNumber = brandAbbreviation.HON.toString() + tempStr;
                break;
            case 4: // SUZ
                modelNumber = brandAbbreviation.SUZ.toString() + tempStr;
                break;
            case 5: // BMW
                modelNumber = brandAbbreviation.BMW.toString() + tempStr;
                break;
            case 6: // IND
                modelNumber = brandAbbreviation.IND.toString() + tempStr;
                break;
            case 7: // DUC
                modelNumber = brandAbbreviation.DUC.toString() + tempStr;
                break;
            default:
                break;
        } // end switch

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
        boolean badInput;
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
                System.out.println("List of Brands");
                setMake(getOption());

                // Input - Model
                in.nextLine();
                System.out.print("Enter the model: ");
                model = in.nextLine();
                setModel(model);

                // Input - Category
                System.out.println("List of categories");
                setCategory(getCategoryOption());

                // Input - Price
                System.out.print("Enter the price: ");
                price = in.nextDouble();
                setPrice(price);

                // Input - Model Number
                System.out.println("Model number must be within range from 1 to 999");
                System.out.print("Enter the model number: ");
                int temp = in.nextInt();
                setModelNumber(Integer.toString(temp));

                // Input - Availability status
                System.out.println("Availability status options");
                availabilityStatus();

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

    public int getCategoryOption()
    {
        String option = "------------------------------------------------\n";
        option += "[1]. Sport\n";
        option += "[2]. Cruiser\n";
        option += "[3]. Touring\n";
        option += "[4]. Other\n";

        return getUserInput(option);
    } // end getCategoryOption method

    public int getOption()
    {
        String option = "------------------------------------------------\n";
        option += "[1] " + brandAbbreviation.HD  + " (Harley-Davidson)\n";
        option += "[2] " + brandAbbreviation.KAW + " (Kawasaki)\n";
        option += "[3] " + brandAbbreviation.HON + " (Honda)\n";
        option += "[4] " + brandAbbreviation.SUZ + " (Suzuki)\n";
        option += "[5] " + brandAbbreviation.BMW + " (Bayerische Motoren Werke)\n";
        option += "[6] " + brandAbbreviation.IND + " (Indian)\n";
        option += "[7] " + brandAbbreviation.DUC + " (Ducati)\n";

        return getUserInput(option);
    } // end getMakeOption method

    public int getUserInput(String option)
    {
        boolean badInput;
        int input = 0;

        do
        {
            try
            {
                System.out.print(option + "Enter your option: ");
                input = in.nextInt();
                badInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid Input. Try again...");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
        return input;
    } // end getUserInput method

    public int availabilityStatus() // FINISH HERE
    {
        boolean badInput;
        int input = 0;

         String[] statusOptions = {"Available", "Pending", "Sold"};
        for(int i = 0; i < statusOptions.length; i++)
         {
            System.out.println("[" + (i + 1) + "] " + statusOptions[i]);
         }

        do
        {
            try
            {
                System.out.print("Enter your option: ");
                input = in.nextInt();
                badInput = false;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid Input. Try again...");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
        return input;
    } // end availabilityStatus method

} // end AddMotorcycle class