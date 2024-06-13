/*
 * Author: Mauricio Lopez Alvarez
 * Course: CEN 3024C
 * Date Written: June, 10, 2024
 * Class: Software Development I
 * Purpose:...Creates the functionality of adding all motorcycles from a file supplied by the user.
 *            The file is read and adds each line to an ArrayList<String>. Once it reaches the end of the file
 *            method addObjects from Motorcycle class is called to store all data.
 */
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class AddMotorcycle extends Motorcycle
{
    public AddMotorcycle(Path path)
    {
        addMotorcycles(path);
    } // end constructor

    /*
     * Method name: addMotorcycle()
     * Purpose:...Once path is supplied by the user it will read each line and store into a variable. Once completed
     *            the method called addObjects() to store all lines to an ArrayList.
     * Arguments: Path path
     * Return value: None; no return value for this method
     */
    public void addMotorcycles(Path path)
    {
        ArrayList<String> addMotorcycleList= new ArrayList<>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            String line = br.readLine();

            while(line != null)
            {
                addMotorcycleList.add(line);
                line = br.readLine();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        addObjects(addMotorcycleList);
    } // end addMotorcycle method
} // end AddMotorcycle class