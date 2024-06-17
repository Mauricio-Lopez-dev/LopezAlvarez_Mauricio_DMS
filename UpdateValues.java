/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...Creates the functionality of updating values to a motorcycle. To locate the motorcycle it will
 *            prompt the user to input the motorcycle id. It will convert the ArrayList to a 2D array to access
 *            the attributes to update its values.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class UpdateValues extends Motorcycle
{
    // Data Fields
    private double salesTax;
    private int id;
    private String userInput;
    Scanner in = new Scanner(System.in);

    public UpdateValues(Path path)
    {
        updateValuesToObject(path);
    } // end constructor

    public double getSalesTax()
    {
        return salesTax;
    } // end accessor method

    public void setSalesTax(double salesTax)
    {
        this.salesTax = salesTax;
    } // end mutator method

    public int getId()
    {
        return id;
    } // end accessor method

    public void setId(Path path, int id)
    {
        int line = 0;
        int min = 1;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile().getName()));
            while (br.readLine() != null)
            {
                line++;
            }
            br.close();

            while (id < min || id > line)
            {
                System.out.println("Error: Motorcycle ID does not exist");
                System.out.println("Enter ID: ");
                id = in.nextInt();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.id = id;
    } // end mutator method

    public String getUserInput()
    {
        return userInput;
    } // end accessor method

    public void setUserInput(String userInput)
    {
        boolean badInput;

        while(userInput.length() == 0)
        {
            System.out.println("Error: Cannot be null");
            System.out.print("Enter true of false: ");
            userInput = userInput.toLowerCase();
        }

        do
        {
            userInput = userInput.toLowerCase();

            if((userInput.equals("true") || userInput.equals("false")))
            {
                badInput = false;
            }
            else
            {
                System.out.println("Error: Invalid option.");
                System.out.print("Enter true or false: ");
                userInput = in.nextLine();
                badInput = true;
            }

        }while(badInput);
        this.userInput = userInput;
    } // end mutator method

    /*
     * Method name: updateValuesToObject()
     * Purpose:...Prompts the user if the customer can purchase the motorcycle. Based on the option it will either
     *            update values or display a message to the user
     * Arguments: Path path
     * Return value: None; no return value for this method
     */
    public void updateValuesToObject(Path path) // FIX ME - If false, display message to user; do not let it go to updateObj method
    {
        boolean badInput;
        double currentSalesTax = 0.06;

        setSalesTax(currentSalesTax);

        do
        {
            try
            {
                System.out.println("Can the customer purchase the motorcycle today?");
                System.out.print("Enter true or false: ");
                userInput = in.nextLine();
                setUserInput(userInput);

                System.out.print("Enter motorcycle ID: ");
                id = in.nextInt();
                setId(path, id);

                if(getUserInput().equalsIgnoreCase("true")) {
                    updateObj(path, getUserInput(), getId(), getSalesTax());
                }
                else
                {
                    System.out.println("Buyer must contact financial department for further assistance.");
                }
                badInput = false;
            }
            catch(Exception e)
            {
                System.out.println("Error: Invalid input.");
                badInput = true;
                in.nextLine();
            }
        }while(badInput);
    } // end updateValuesToObject method
} // end UpdateValues class