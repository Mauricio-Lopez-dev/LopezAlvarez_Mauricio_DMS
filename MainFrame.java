/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: July 1, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...The software and its features are implemented in a GUI frame. This class handles all the functionalities
 *             of the software to include exceptional handling.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame
{
    // Data Fields
    private JPanel MainPanel;
    private JButton addBtn;
    private JPanel FunctionalityPanel;
    private JButton removeBtn;
    private JButton updateBtn;
    private JButton statusBtn;
    private JButton exitBtn;
    private JButton uploadBtn;
    private JTextPane descTextPane;
    private JPanel DisplayInfoPanel;
    private JTextPane outputTextPane;
    private JScrollPane displayScrollPane;
    private JPanel OutputPane;
    Motorcycle motorObj;
    RemoveMotorcycle removeObj;
    UpdateValues updateObj;
    AvailabilityLog statusObj;

    /*
     * Method name: MainFrame()
     * Purpose:...Constructor builds the GUI for the DMS software.
     * Arguments: Zero arguments for this method.
     * Return value: None
     */
    public MainFrame()
    {
        // Setting frame
        setContentPane(MainPanel);
        setTitle("Orlando Harley-Davidson DMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 435);
        setLocationRelativeTo(getParent());
        setVisible(true);

        // Variables and Objects
        ArrayList<Motorcycle> motorcycleList = new ArrayList<>();
        double currentSalesTax = 0.06;
        motorObj = new Motorcycle();
        removeObj = new RemoveMotorcycle();
        updateObj = new UpdateValues();
        statusObj = new AvailabilityLog();

        // Welcoming message
        descTextPane.setText(
                        "Welcome to Orlando Harley-Davidson DMS." +
                        " This software provides many functionalities to assist " +
                        " the user with day-to-day tasks. To get started, upload " +
                        " the data file to the system by selecting the upload function.");

        // Upload Button
        uploadBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

                descTextPane.setText(
                                    "Upload File function will prompt the user to input the absolute path. " +
                                    "If the file exist the path will be saved in the system to access the file. " +
                                    "Otherwise, it will display a message to the user that file cannot be found. " +
                                    "This occurs if the user supplied an incorrect path or the file does not exist.");

                String file = JOptionPane.showInputDialog(MainFrame.this, "Enter file path",
                                                    "Locate File", JOptionPane.PLAIN_MESSAGE);
                motorObj.locateFileWithGUI(file);
                JOptionPane.showMessageDialog(MainFrame.this, "File Location Recorded: " +
                                            motorObj.getPath(), "Location of File",
                                            JOptionPane.INFORMATION_MESSAGE);
            }
        }); // end uploadBtn action listener

        // Add Button
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(motorObj.getPath() != null) {
                    descTextPane.setText(
                            "Add Motorcycle function will save all the data from the uploaded file " +
                                    "If successful it will display a message to the user indicating that " +
                                    "all motorcycles have been saved in the system.");

                    JOptionPane.showMessageDialog(MainFrame.this, "All motorcycles have been uploaded and saved to the system.");
                    motorcycleList.add(new AddMotorcycle(motorObj.getPath()));
                    outputTextPane.setText(motorcycleList.toString());
                    displayScrollPane.setViewportView(outputTextPane);
                }
                else{
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "<html><i>Error: File is not uploaded to the system. " +
                                    "Please upload a file by utilizing the <B><span bgcolor=\"yellow\">Upload File option</span></B>.</i></html>",
                            "File Not Uploaded", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); // end addBtn action listener

        // Remove Button
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!motorcycleList.isEmpty()) {
                    descTextPane.setText("Remove Motorcycle function will prompt the user to supply at least one attribute" +
                            " that is associated to the motorcycle that will be removed. The motorcycle can" +
                            " be searched by its ID or ID and Year. Once the motorcycle is found it will be" +
                            " removed from the system.");
                    try {
                        removeObj.setChoiceWithGUI(Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this,
                                "<html>To search a motorcycle to remove choose one of the following: " +
                                        "<br><B>1. Search by ID</B></br>" +
                                        "<br><B>2. Search by ID and Year</B></br></html>",
                                "Remove motorcycle", JOptionPane.PLAIN_MESSAGE)));

                        removeObj.setMotorcycleIDWithID(Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this,
                                "Enter the ID",
                                "Motorcycle ID", JOptionPane.PLAIN_MESSAGE)));

                        if (removeObj.getChoice() == 1) {
                            // call method to removeObj
                            motorObj.removeObject(motorObj.getPath(), removeObj.getMotorcycleID());
                        } else if (removeObj.getChoice() == 2) {
                            removeObj.setYearWithGUI(Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this,
                                    "Enter the year",
                                    "Year of Motorcycle", JOptionPane.PLAIN_MESSAGE)));

                            motorObj.removeObject(motorObj.getPath(), removeObj.getMotorcycleID(), removeObj.getYear());
                        }

                        JOptionPane.showMessageDialog(MainFrame.this, "Motorcycle removed from the system",
                                "Processed", JOptionPane.INFORMATION_MESSAGE);

                        motorcycleList.clear();
                        motorcycleList.add(new AddMotorcycle(motorObj.getPath()));
                        outputTextPane.setText(motorcycleList.toString());
                        displayScrollPane.setViewportView(outputTextPane);
                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Invalid input. Must enter a number", "Error!", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(MainFrame.this, "Returning to main menu",
                                "Caution", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "<html><i>Error: There is no data in the system. " +
                                    "Please utilize the <B><span bgcolor=\"yellow\">Add Motorcycle option</span></B> " +
                                    "to store data from the file into the system.</i></html>",
                            "No Data Found", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); // end removeBtn action listener

        // Update Button
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descTextPane.setText(
                        "Update Values function will ask the user if the customer can purchase the " +
                        "motorcycle today? If yes is selected it will prompt the user to input the motorcycle ID to " +
                        "verify it's status. Otherwise, it will prompt the user to direct the customer to financial " +
                        "department for further assistance.");

                if(!motorcycleList.isEmpty()) {
                    String option = "true";
                    int result = JOptionPane.showConfirmDialog(MainFrame.this,
                            "Before updating any values, can the customer purchase the motorcycle today?",
                            "Message", JOptionPane.YES_NO_OPTION);

                    try {
                        if (result == JOptionPane.YES_OPTION) {
                            updateObj.setUserInputWithGUI(option);
                            updateObj.setIdWithGUI(motorObj.getPath(),
                                    Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this,
                                            "Enter the motorcycle ID")));

                            updateObj.setSalesTax(currentSalesTax);
                            motorObj.updateObjWithGUI(motorObj.getPath(), updateObj.getId(), updateObj.getSalesTax());
                        } else if (result == JOptionPane.NO_OPTION) {
                            JOptionPane.showMessageDialog(MainFrame.this,
                                    "Buyer must contact financial department for further assistance.",
                                    "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Invalid input. Must enter a number",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(MainFrame.this, "Returning to main menu",
                                "Caution", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "<html><i>Error: There is no data in the system. " +
                                    "Please utilize the <B><span bgcolor=\"yellow\">Add Motorcycle option</span></B> " +
                                    "to store data from the file into the system.</i></html>",
                            "No Data Found", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); // end updateBtn action listener

        // Status Button
        statusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                descTextPane.setText(
                                    "Display Status Log function will prompt the user to select an option " +
                                    "of motorcycles to display based on their status.");

                if(!motorcycleList.isEmpty()) {
                    try {
                        statusObj.setUserInputWithGUI(Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this,
                                "<html>Display availability status log by filter.<br>Choose one of the following:</br>" +
                                        " <br><B>1. In Stock</B></br>" +
                                        " <br><B>2. Out of Stock</B></br>" +
                                        " <br><B>3. Pending</B></br>" +
                                        " <br><B>4. Sold</B></br></html>", "Display Status", JOptionPane.PLAIN_MESSAGE)));
                        outputTextPane.setText(statusObj.displayStatusWithGUI(motorObj.getPath(), statusObj.getUserInput()).toString());
                        displayScrollPane.setViewportView(outputTextPane);

                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Invalid input. Must enter a number",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(MainFrame.this, "Returning to main menu",
                                "Caution", JOptionPane.WARNING_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "<html><i>Error: There is no data in the system. " +
                                    "Please utilize the <B><span bgcolor=\"yellow\">Add Motorcycle option</span></B> " +
                                    "to store data from the file into the system.</i></html>",
                            "No Data Found", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); // end statusBtn action listener

        // Exit Button
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Thank you for using Orlando Harley DMS",
                        "Exiting Application", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }); // end exitBtn action listener
    } // end MainFrame constructor

    /*
     * Method name: main()
     * Purpose:...Main method's purpose is to call the MainFrame constructor that allows the user to interact
     *            with a GUI frame.
     * Arguments: Zero arguments for this method.
     * Return value: None
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainFrame::new);
    } // end main
} // end MainFrame class
