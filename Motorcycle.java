/*
 * Author: Mauricio Lopez Alvarez
 * Course: CEN 3024C
 * Date Written: June, 10, 2024
 * Class: Software Development I
 * Purpose:...Motorcycle class will store all objects loaded from the file supplied by the user. In addition,
 *            the class contains all functionality methods to execute based on the user's choice when prompted
 *            to choose a feature in the software.
 */
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Motorcycle
{
    // Data Fields
    private ArrayList<String> data;
    private Path path;
    Scanner in = new Scanner(System.in);

    public Path getPath()
    {
        return path;
    } // end accessor method

    public void setPath(Path path)
    {
        while(!path.toFile().exists())
        {
            System.out.println("Error: File not found. Try again...");
            System.out.print("Enter file path: ");
            path = Paths.get(in.nextLine());
        }
        this.path = path;
    } // end mutator method

    public void setPathWithGUI(Path path)
    {
        while(!path.toFile().exists())
        {
            JOptionPane.showMessageDialog(null, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            String temp = JOptionPane.showInputDialog(null,"Enter file path", "Locate File", JOptionPane.PLAIN_MESSAGE);
            path = Paths.get(temp);
        }
        this.path = path;
    } // end setPathWithGUI method

    public ArrayList<String> getData()
    {
        return data;
    } // end getData method

    public void setData(ArrayList<String> data)
    {
        this.data = data;
    } // end mutator method

    /*
     * Method name: locatePath()
     * Purpose:...Prompts the user to input absolute path to locate the file in their operating system.
     * Arguments: Zero arguments
     * Return value: Path; returns the path to the file
     */
    public Path locateFile()
    {
        try
        {
            System.out.println("\t\tWelcome to Orlando Harley-Davidson DMS");
            System.out.println("File must be uploaded.");
            System.out.print("Enter file path: ");
            path = Paths.get(in.nextLine());
            setPath(path);
            System.out.println("File successfully located.");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return getPath();
    } // end locatePath method

    /*
     * Method name: locatePathWithGUI()
     * Purpose:...Prompts the user to input absolute path to locate the file in their operating system.
     * Arguments: Zero arguments
     * Return value: No return value
     */
    public void locateFileWithGUI(String filePath)
    {
        try
        {
            path = Paths.get(filePath);
            setPathWithGUI(path);
            JOptionPane.showMessageDialog(null, "File successfully located", "Location Received", JOptionPane.INFORMATION_MESSAGE);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    } // end locatePath method

    /*
     * Method name: writeToFile()
     * Purpose:...File will be truncated to 0 and write new data to the file to reflect any changes that occurred
     * Arguments: Path path, ArrayList<String> list
     * Return value: None; no return value for this method
     */
    public void writeToFile(Path path, ArrayList<String> list)
    {
        try(BufferedWriter bw = Files.newBufferedWriter(path,StandardOpenOption.WRITE,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                PrintWriter pw = new PrintWriter(new BufferedWriter(bw)))
        {
            for(int i = 0; i < list.size(); i++)
            {
                pw.write(list.get(i));
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    } // end writeToFle method

    /*
     * Method name: toString()
     * Purpose:...This method will store all the current information from the variable data. Also, will
     *            display all current content from the file.
     * Arguments: Zero arguments
     * Return value: Returns value of type String
     */
    public String toString()
    {
        String result = "";

       for(int i = 0; i < data.size(); i++)
        {
            result += data.get(i) + "\n";
        }
        return result;
    } // end toString method

    /*
     * Method name: addObjects()
     * Purpose:...This method will add all data from file and store it to an ArrayList<String>
     * Arguments: ArrayList<String> list
     * Return value: None
     */
    public void addObjects(ArrayList<String> list)
    {
        data = new ArrayList<>();

        for(String s : list)
        {
            data.add(s);
        }
        setData(data);
        System.out.println("***Data Stored***");
    } // end addObject method


    /*
     * Method name: removeObject()
     * Purpose:...File will be read and locate the motorcycle by its ID. Once found, it is then removed from
     *            the system.
     * Arguments: Path path, int id
     * Return value: None; no return value for this method
     */
    public void removeObject(Path path, int id)
    {
        // Variables
        int line = 0;
        String currentLine;
        String tempFile = "text.txt";
        File oldFile = new File(path.toFile().getAbsolutePath());
        File newFile = new File(tempFile);
        data = new ArrayList<>();

        try
        {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(oldFile);
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine()) != null)
            {
                line++;
                if(id != line)
                {
                    pw.println(currentLine);
                    data.add(currentLine);
                }
            }
            fr.close();
            bw.close();
            fw.close();
            br.close();

            oldFile.delete();
            File dump = new File(path.toFile().getName());
            newFile.renameTo(dump);
            System.out.println("***Motorcycle removed***");
            addObjects(data);
        }
        catch(IOException e)
        {
           System.out.println("Error: " + e.getMessage());
        }
    } // end removeObjects method

    /*
     * Method name: removeObject() - Overloaded method
     * Purpose:...File will be read and locate the motorcycle by its ID and year. Once found, it is then removed from
     *            the system.
     * Arguments: Path path, int id, int year
     * Return value: None
     */
    public void removeObject(Path path, int id, int year)
    {
        // Variables
        int line = 0;
        String currentLine;
        String tempFile = "text.txt";
        File oldFile = new File(path.toFile().getAbsolutePath());
        File newFile = new File(tempFile);
        data = new ArrayList<>();

        try
        {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(oldFile);
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine()) != null)
            {
                line++;
                if(id != line && (!currentLine.contains(Integer.toString(year))))
                {
                    pw.println(currentLine);
                    data.add(currentLine);
                }
            }
            pw.close();
            fr.close();
            bw.close();
            fw.close();
            br.close();

            oldFile.delete();
            File dump = new File(path.toFile().getName());
            newFile.renameTo(dump);
            System.out.println("***Motorcycle removed***");
            addObjects(data);
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    } // end removeObject method

    /*
     * Method name: updateObj()
     * Purpose:...Updates values to aa motorcycle. Motorcycle's price and status are updated if the customer
     *            can purchase the motorcycle.
     * Arguments: Path path, String userInput, int id, double salesTax
     * Return value: None; no return value for this method
     */
    public void updateObj(Path path, int id, double salesTax)
    {
        ArrayList<String> currentList = new ArrayList<>();
        double currentPrice;
        double totalPrice;
        String status;
        String[] statusOptions = {"Pending", "Sold"};
        StringBuilder builder = new StringBuilder();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            String line = br.readLine();

            while (line != null)
            {
                currentList.add(line);
                line = br.readLine();
            }
            br.close();

            String[][] array = buildArray(currentList);
            int colNumForPrice = 4;
            int colNumForStatus = 5;

            if (array[id - 1][colNumForStatus].equalsIgnoreCase("in stock"))
            {
                currentPrice = Integer.parseInt(array[id - 1][colNumForPrice]);
                System.out.println("MSRP: \t\t\t$" + currentPrice);
                totalPrice = calculateTotalPrice(salesTax, currentPrice);
                status = statusOptions[1];

                // Output
                System.out.println("FL Sales Tax: \t" + salesTax);
                System.out.println("Total price: \t$" + totalPrice);
                System.out.println("STATUS: \t\t"     + status);

                // Change values
                array[id - 1][colNumForPrice] = String.valueOf(totalPrice);
                array[id - 1][colNumForStatus] = status;

                currentList.clear();
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < 6; j++) {
                        builder.append(array[i][j]).append(",");
                        if (j == 5) {
                            builder.append("\n");
                            break;
                        }
                    }
                }

                currentList.add(builder.toString());
                addObjects(currentList);
                writeToFile(path, currentList);
                System.out.println("***Motorcycle values updated***");
            }
            else if (array[id - 1][colNumForStatus].equalsIgnoreCase("sold"))
            {
                System.out.println("This motorcycle is sold");
            }
            else if (array[id - 1][colNumForStatus].equalsIgnoreCase("pending"))
            {
                System.out.println("Motorcycle is in pending status. Please contact buyer for an update.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    } // end updateObj method

    /*
     * Method name: updateObjWithGUI()
     * Purpose:...Updates values to a motorcycle. Motorcycle's price and status are updated if the customer
     *            can purchase the motorcycle. This method is utilized in the MainFrame to display GUI
     * Arguments: Path path, String userInput, int id, double salesTax
     * Return value: None; no return value for this method
     */
    public void updateObjWithGUI(Path path, int id, double salesTax)
    {
        ArrayList<String> currentList = new ArrayList<>();
        double currentPrice;
        double totalPrice;
        String status;
        String[] statusOptions = {"Pending", "Sold"};
        StringBuilder builder = new StringBuilder();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            String line = br.readLine();

            while (line != null)
            {
                currentList.add(line);
                line = br.readLine();
            }
            br.close();

            String[][] array = buildArray(currentList);
            int colNumForPrice = 4;
            int colNumForStatus = 5;

            if (array[id - 1][colNumForStatus].equalsIgnoreCase("in stock"))
            {
                currentPrice = Integer.parseInt(array[id - 1][colNumForPrice]);

                totalPrice = calculateTotalPrice(salesTax, currentPrice);
                status = statusOptions[1];

                JOptionPane.showMessageDialog(null,
                        "<html>Motorcycle is in stock" +
                                "<br><B>Current Price: </B></br>$" + currentPrice +
                                "<br><B>Sales tax: </B></br>" + salesTax +
                                "<br><B>Total Price: </B></br>$" + totalPrice +
                                "<br><B>Status: </B></br>" + status + "</html>",
                            "Current Sale Amount", JOptionPane.INFORMATION_MESSAGE);

                // Change values
                array[id - 1][colNumForPrice] = String.valueOf(totalPrice);
                array[id - 1][colNumForStatus] = status;

                currentList.clear();
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < 6; j++) {
                        builder.append(array[i][j]).append(",");
                        if (j == 5) {
                            builder.append("\n");
                            break;
                        }
                    }
                }
                currentList.add(builder.toString());
                addObjects(currentList);
                writeToFile(path, currentList);
                JOptionPane.showMessageDialog(null, "Motorcycle updated",
                        "Updated Values", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (array[id - 1][colNumForStatus].equalsIgnoreCase("sold"))
            {
                JOptionPane.showMessageDialog(null, "Motorcycle is not longer available",
                                        "Sold Status", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (array[id - 1][colNumForStatus].equalsIgnoreCase("pending"))
            {
                JOptionPane.showMessageDialog(null,"Motorcycle is in pending status. Please contact buyer for an update.",
                        "Pending Status", JOptionPane.INFORMATION_MESSAGE );
            }
            else if(array[id - 1][colNumForStatus].equalsIgnoreCase("out of stock"))
            {
                JOptionPane.showMessageDialog(null,"Motorcycle is currently out of stock",
                        "Out of Stock Status", JOptionPane.INFORMATION_MESSAGE );
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    } // end updateObjWithGUI Method

    /*
     * Method name: buildArray()
     * Purpose:...Takes in an ArrayList and converts to a 2D-array to access attributes of a motorcycle.
     * Arguments: ArrayList<String> list
     * Return value: Returns of type String[][]
     */
    public String[][] buildArray(ArrayList<String> list)
    {
        int rows = list.size();
        int cols =  list.get(0).split(",").length;
        String[][] array = new String[rows][cols];

        // Value is added when encountering ','
        for(int i = 0; i < rows; i++)
        {
            String[] values = list.get(i).split(",");
            for(int j = 0; j < cols; j++)
            {
                array[i][j] = values[j];
            }
        }
        return array;
    } // end buildArray method

    /*
     * Method name: calculateTotalPrice()
     * Purpose:...Calculates sales tax with the motorcycle's price. The sum of the price and sales tax is stored
     *            in a local variable
     * Arguments: double salesTax, double price
     * Return value: Returns the value of type double result
     */
    public double calculateTotalPrice(double salesTax, double price)
    {
        double result;

        //Calculation
        result = salesTax * price;
        result = result + price;

        return result;
    } // end calculateTotalPrice method

 } // end Motorcycle class