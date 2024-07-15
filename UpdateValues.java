/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 3, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...Creates the functionality of updating values to a motorcycle. To locate the motorcycle it will
 *            prompt the user to input the motorcycle id. It will convert the ArrayList to a 2D array to access
 *            the attributes to update its values.
 */
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UpdateValues extends Motorcycle
{
    // Data Fields
    private double salesTax;
    private int id;
    private String userInput;
    Scanner in = new Scanner(System.in);

    public UpdateValues() {} // end default constructor

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

    public void setIdWithGUI(int id)
    {
        int min = 1;
        int max = 30;

        while (id < min || id > max)
        {
            JOptionPane.showMessageDialog(null, "Invalid ID", "ID Not Found", JOptionPane.ERROR_MESSAGE);
            id = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter an existing motorcycle ID", "Locating ID", JOptionPane.PLAIN_MESSAGE));
        }

        this.id = id;
    } // end mutator method

    public void setIdWithGUI(Path path, int id)
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
                JOptionPane.showMessageDialog(null, "Invalid ID", "ID Not Found", JOptionPane.ERROR_MESSAGE);
                id = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Enter an existing motorcycle ID", "Locating ID", JOptionPane.PLAIN_MESSAGE));
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

    public void setUserInputWithGUI(String input)
    {
        this.userInput = input;
    } // end setUserInputWithGUI method

    /*
     * Method name: updateValuesToObject()
     * Purpose:...Prompts the user if the customer can purchase the motorcycle. Based on the option it will either
     *            update values or display a message to the user
     * Arguments: Path path
     * Return value: None; no return value for this method
     */
    public void updateValuesToObject(Path path)
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
                    updateObj(path, getId(), getSalesTax());
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

    public double getPriceQuery(Connection con, int id)
    {
        double price = 0.0;
        try{
            Statement stmt = con.createStatement();
            String query = "select Price from motorcycle_data where ID = " + id;
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                price += rs.getDouble("Price");
            }
            JOptionPane.showMessageDialog(null, "Price captured: " + price,
                    "Price Query", JOptionPane.PLAIN_MESSAGE);
        }
        catch(SQLException error)
        {
           error.printStackTrace();
        }
        return price;
    }

    /*
     * Method name: getQuery()
     * Purpose:...Returns a query to execute the functionality
     * Arguments: int id, double total, String status
     * Return value: Stirng query
     */
    public String getQuery(int id, double total, String status)
    {
        String query = "UPDATE motorcycle_data ";
                query += "SET Price = " + total + ", Status = '" + status + "'";
                query += "where ID = " + id + ";";

        return query;
    } // end getQuery method

    /*
     * Method name: updateValues()
     * Purpose:...Updates values of the motorcycle in the database
     * Arguments: int id, double price, Connection con
     * Return value: None
     */
    public void updateValues(int id, double price, Connection con)
    {
        String status = "";
        String priceQuery = "select Status from motorcycle_data where ID = " + id;

        try{
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt1.executeQuery(priceQuery);

            while(rs.next())
            {
                status = rs.getString("Status");
            }

            if (status.equalsIgnoreCase("in stock"))
            {
                status = "Sold";

                String query = getQuery(id, price, status);
                stmt2.executeUpdate(query);
            }
            else if(status.equalsIgnoreCase("out of stock"))
            {
                JOptionPane.showMessageDialog(null, "Motorcycle is currently out of stock",
                                    "Not Available", JOptionPane.WARNING_MESSAGE);
            }
            else if(status.equalsIgnoreCase("sold"))
            {
                JOptionPane.showMessageDialog(null, "Motorcycle is sold",
                        "Not Available", JOptionPane.WARNING_MESSAGE);
            }
        }catch(SQLException e)
        {
           e.printStackTrace();
        }
    } // end updateValues method
} // end UpdateValues class