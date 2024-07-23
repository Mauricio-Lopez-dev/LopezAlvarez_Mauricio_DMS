/*
 * Author: Mauricio Lopez Alvarez
 * Date Written: July 1, 2024
 * Course: CEN 3024C
 * Class: Software Development I
 * Purpose:...The software and its features are implemented in a GUI frame. This class handles all the functionalities
 *             of the software to include exceptional handling.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseFrame extends JFrame
{
    // Data Fields
    private JPanel MainPanel;
    private JButton printDataBtn;
    private JPanel FunctionalityPanel;
    private JButton removeBtn;
    private JButton updateBtn;
    private JButton statusBtn;
    private JButton exitBtn;
    private JButton connectDbBtn;
    private JTextPane descTextPane;
    private JPanel DisplayInfoPanel;
    private JTextPane outputTextPane;
    private JScrollPane displayScrollPane;
    private JPanel OutputPane;
    private JTable table1;
    private JButton resetBtn;
    Motorcycle motorObj;
    RemoveMotorcycle removeObj;
    UpdateValues updateObj;
    AvailabilityLog statusObj;

    /*
     * Method name: DatabaseFrame()
     * Purpose:...Constructor builds the GUI for the DMS software.
     * Arguments: None
     * Return value: None
     */
    public DatabaseFrame()
    {
        // Setting frame
        setContentPane(MainPanel);
        setTitle("Orlando Harley-Davidson DMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 435);
        setLocationRelativeTo(getParent());
        setVisible(true);

        // Variables and Objects
        double currentSalesTax = 0.06;
        motorObj = new Motorcycle();
        removeObj = new RemoveMotorcycle();
        updateObj = new UpdateValues();
        statusObj = new AvailabilityLog();
        String url;
        String username;
        String password;
        boolean badInput = false;

        // Welcoming message
        descTextPane.setText(
                "Welcome to Orlando Harley-Davidson DMS." +
                        " This software provides many functionalities to assist " +
                        " the user with day-to-day tasks. To get started, upload " +
                        " the data file to the system by selecting the upload function." +
                        "Connect to database function will prompt the user to input the absolute path of the database file. " +
                        "Along with inputting the username and password to gain access to the database. " +
                        "If any information provided is incorrect. A message will display to the user.");

        do {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                do {
                    url = JOptionPane.showInputDialog(DatabaseFrame.this, "Enter url",
                            "Database URL", JOptionPane.PLAIN_MESSAGE);
                    username = JOptionPane.showInputDialog(DatabaseFrame.this, "Enter username",
                            "Database", JOptionPane.PLAIN_MESSAGE);
                    password = JOptionPane.showInputDialog(DatabaseFrame.this, "Enter password",
                            "Database", JOptionPane.PLAIN_MESSAGE);

                    // Validation
                    if (!username.isEmpty() && !password.isEmpty()) {
                        badInput = false;
                    } else {
                        badInput = true;
                        JOptionPane.showMessageDialog(DatabaseFrame.this, "Error: Username and password fields cannot be empty.",
                                "Error Found", JOptionPane.ERROR_MESSAGE);
                    }
                } while (badInput);

                Connection con = DriverManager.getConnection(url, username, password);
                JOptionPane.showMessageDialog(DatabaseFrame.this, "Established connection",
                        "Successful", JOptionPane.INFORMATION_MESSAGE);

                // Print data button
                printDataBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement st = con.createStatement();
                            String query = "select * from motorcycle_data";
                            ResultSet rs = st.executeQuery(query);
                            ResultSetMetaData rsmd = rs.getMetaData();
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int cols = rsmd.getColumnCount();
                            String[] colName = new String[cols];

                            for (int i = 0; i < cols; i++) {
                                colName[i] = rsmd.getColumnName(i + 1);
                            }
                            model.setColumnIdentifiers(colName);

                            String id, year, make, modelDB, price, status;
                            while (rs.next()) {
                                id = rs.getString(1);
                                year = rs.getString(2);
                                make = rs.getString(3);
                                modelDB = rs.getString(4);
                                price = rs.getString(5);
                                status = rs.getString(6);
                                String[] row = {id, year, make, modelDB, price, status};
                                model.addRow(row);
                            }
                            st.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }); // end printDataBtn action listener

                // Remove Button
                removeBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        descTextPane.setText("Remove Motorcycle function will prompt the user to supply at least one attribute" +
                                " that is associated to the motorcycle that will be removed. The motorcycle can" +
                                " be searched by its ID or ID and Year. Once the motorcycle is found it will be" +
                                " removed from the system.");
                        try {
                            removeObj.setChoiceWithGUI(Integer.parseInt(JOptionPane.showInputDialog(DatabaseFrame.this,
                                    "<html>To search a motorcycle to remove choose one of the following: " +
                                            "<br><B>1. Search by ID</B></br>" +
                                            "<br><B>2. Search by ID and Year</B></br></html>",
                                    "Remove motorcycle", JOptionPane.PLAIN_MESSAGE)));

                            removeObj.setMotorcycleIDWithID(Integer.parseInt(JOptionPane.showInputDialog(DatabaseFrame.this,
                                    "Enter the ID",
                                    "Motorcycle ID", JOptionPane.PLAIN_MESSAGE)));

                            if (removeObj.getChoice() == 1) {
                                String query = removeObj.getQueryOption(removeObj.getMotorcycleID());
                                removeObj.removeRow(query, con);

                            } else if (removeObj.getChoice() == 2) {
                                removeObj.setYearWithGUI(Integer.parseInt(JOptionPane.showInputDialog(DatabaseFrame.this,
                                        "Enter the year",
                                        "Year of Motorcycle", JOptionPane.PLAIN_MESSAGE)));

                                String query = removeObj.getQueryOption(removeObj.getMotorcycleID(), removeObj.getYear());
                                removeObj.removeRow(query, con);
                            }

                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Motorcycle removed from the system",
                                    "Processed", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception error) {
                            JOptionPane.showMessageDialog(DatabaseFrame.this,
                                    "Invalid input. Must enter a number", "Error!", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Returning to main menu",
                                    "Caution", JOptionPane.WARNING_MESSAGE);
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

                        int result = JOptionPane.showConfirmDialog(DatabaseFrame.this,
                                "Before updating any values, can the customer purchase the motorcycle today?",
                                "Message", JOptionPane.YES_NO_OPTION);
                        try {
                            if (result == JOptionPane.YES_OPTION) {
                                updateObj.setIdWithGUI(Integer.parseInt(JOptionPane.showInputDialog(DatabaseFrame.this,
                                        "Enter the motorcycle ID")));

                                updateObj.setSalesTax(currentSalesTax);
                                double total = motorObj.calculateTotalPrice(updateObj.getSalesTax(), updateObj.getPriceQuery(con, updateObj.getId()));
                                updateObj.updateValues(updateObj.getId(), total, con);

                            } else if (result == JOptionPane.NO_OPTION) {
                                JOptionPane.showMessageDialog(DatabaseFrame.this,
                                        "Buyer must contact financial department for further assistance.",
                                        "Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception error) {
                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Invalid input. Must enter a number",
                                    "Error!", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Returning to main menu",
                                    "Caution", JOptionPane.WARNING_MESSAGE);
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
                        try {
                            statusObj.setUserInputWithGUI(Integer.parseInt(JOptionPane.showInputDialog(DatabaseFrame.this,
                                    "<html>Display availability status log by filter.<br>Choose one of the following:</br>" +
                                            " <br><B>1. In Stock</B></br>" +
                                            " <br><B>2. Out of Stock</B></br>" +
                                            " <br><B>3. Pending</B></br>" +
                                            " <br><B>4. Sold</B></br></html>", "Display Status", JOptionPane.PLAIN_MESSAGE)));

                            Statement st = con.createStatement();
                            String query = statusObj.getQuery(statusObj.getUserInput());
                            ResultSet rs = st.executeQuery(query);
                            ResultSetMetaData rsmd = rs.getMetaData();
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int cols = rsmd.getColumnCount();
                            String[] colName = new String[cols];

                            for (int i = 0; i < cols; i++) {
                                colName[i] = rsmd.getColumnName(i + 1);
                            }
                            model.setColumnIdentifiers(colName);

                            String id, year, make, modelDB, price, status;
                            while (rs.next()) {
                                id = rs.getString(1);
                                year = rs.getString(2);
                                make = rs.getString(3);
                                modelDB = rs.getString(4);
                                price = rs.getString(5);
                                status = rs.getString(6);
                                String[] row = {id, year, make, modelDB, price, status};
                                model.addRow(row);
                            }
                            st.close();
                        } catch (Exception error) {
                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Invalid input. Must enter a number",
                                    "Error!", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(DatabaseFrame.this, "Returning to main menu",
                                    "Caution", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }); // end statusBtn action listener

                // Reset Panel Button
                resetBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        table1.setModel(new DefaultTableModel());
                    }
                }); // end resetBtn action listener

                // Exit Button
                exitBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(DatabaseFrame.this, "Thank you for using Orlando Harley DMS",
                                "Exiting Application", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                }); // end exitBtn action listener

                badInput = false;
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(DatabaseFrame.this, "Error: Class Not Found",
                        "Driver", JOptionPane.ERROR_MESSAGE);
                badInput = true;
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(DatabaseFrame.this, "Error: Database not Found",
                        "Database Does not exist", JOptionPane.ERROR_MESSAGE);
                badInput = true;
            }
        }while(badInput);
    } // end DatabaseFrame constructor

    /*
     * Method name: main()
     * Purpose:...Main method's purpose is to call the DatabaseFrame constructor that allows the user to interact
     *            with a GUI frame.
     * Arguments: Zero arguments for this method.
     * Return value: None
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(DatabaseFrame::new);
    } // end main
} // end DatabaseFrame class