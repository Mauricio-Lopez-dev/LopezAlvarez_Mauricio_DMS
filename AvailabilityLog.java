/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: June 13, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...Creates the functionality of displaying motorcycles by filter to the user.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class AvailabilityLog extends Motorcycle
{
    // Data field
    private int userInput;
    Scanner in = new Scanner(System.in);

    public AvailabilityLog(Path path)
    {
        displayStatusByFilter(path);
    } // end constructor

    public int getUserInput()
    {
        return userInput;
    } // end accessor method

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

    /*
     * Method name: displayStatusByFilter()
     * Purpose:...Prompts user to select filter option for displaying motorcycles based on status
     * Arguments: Path path
     * Return value: None; no return value for this method
     */
    public void displayStatusByFilter(Path path)
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
                System.out.println("--------------------------------------------------");
                displayStatus(path, getUserInput());
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

    /*
     * Method name: displayStatus()
     * Purpose:...Process filter from user input and displays the list to the user
     * Arguments: Path path, int userInput
     * Return value: None; no return value for this method
     */
   public void displayStatus(Path path, int userInput)
   {
       ArrayList<String> currentList = new ArrayList<>();
       int colsNumOfStatus = 5;
       StringBuilder builder = new StringBuilder();

       try
       {
           BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
           String line = reader.readLine();

           while(line != null)
           {
               currentList.add(line);
               line = reader.readLine();
           }
           reader.close();

       }
       catch(IOException e)
       {
           System.out.println("Error: File cannot be read.");
       }

       String[][] array = buildArray(currentList);

       switch(userInput)
       {
           case 1: // Display In Stock
               for(int i = 0; i < array.length; i++)
               {
                   if(array[i][colsNumOfStatus].equalsIgnoreCase("in stock"))
                   {
                       for(int j = 0; j < array[i].length; j++)
                       {
                           builder.append(array[i][j]).append(",");
                           if(j == 5)
                           {
                               builder.append("\n");
                               break;
                           }
                       }
                   }
               }
               System.out.print(builder);
               System.out.println("--------------------------------------------------");
               break;

           case 2: // Display Out of Stock
               for(int i = 0; i < array.length; i++)
               {
                   if(array[i][colsNumOfStatus].equalsIgnoreCase("out of stock"))
                   {
                       for(int j = 0; j < array[i].length; j++)
                       {
                           builder.append(array[i][j]).append(",");
                           if(j == 5)
                           {
                               builder.append("\n");
                               break;
                           }
                       }
                   }
               }
               System.out.print(builder);
               System.out.println("--------------------------------------------------");
               break;

           case 3: // Display Pending
               for(int i = 0; i < array.length; i++)
               {
                   if(array[i][colsNumOfStatus].equalsIgnoreCase("pending"))
                   {
                       for(int j = 0; j < array[i].length; j++)
                       {
                           builder.append(array[i][j]).append(",");
                           if(j == 5)
                           {
                               builder.append("\n");
                               break;
                           }
                       }
                   }
               }
               System.out.print(builder);
               System.out.println("--------------------------------------------------");
               break;

           case 4: // Display Sold
               for(int i = 0; i < array.length; i++)
               {
                   if(array[i][colsNumOfStatus].equalsIgnoreCase("sold"))
                   {
                       for(int j = 0; j < array[i].length; j++)
                       {
                           builder.append(array[i][j]).append(",");
                           if(j == 5)
                           {
                               builder.append("\n");
                               break;
                           }
                       }
                   }
               }
               System.out.print(builder);
               System.out.println("--------------------------------------------------");
               break;
       } // end switch
   } // end displayStatus method
} // end AvailabilityLog class
