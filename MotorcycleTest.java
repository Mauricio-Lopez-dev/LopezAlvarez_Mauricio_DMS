import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MotorcycleTest
{
    // Object to be tested
    AddMotorcycle addMotorcycleObject;
    AvailabilityLog objectStatus;
    Motorcycle motorcycleObject;
    ArrayList<String> testData;
    Path path = Paths.get(new File("MotorcycleList.txt").getAbsolutePath());


    @BeforeEach
    void setUp()
    {
        testData = new ArrayList<>();
        motorcycleObject = new Motorcycle();
        addMotorcycleObject = new AddMotorcycle(path);

        for(int i = 0; i < addMotorcycleObject.getData().size(); i++)
        {
            testData.add(addMotorcycleObject.getData().get(i));
        }
    } // end setUp test method

    @Test
    @DisplayName("Add object to ArrayList test")
    void addObjects()
    {
        testData = new ArrayList<>();

        testData.add("Hello");
        testData.add("World");
        motorcycleObject.addObjects(testData);

        assertEquals(testData, motorcycleObject.getData(), "Error: ArrayList data does not match");
    } // end addObject test method

    @Test
    @DisplayName("Remove object option one test")
    void removeObject()
    {
        int testID = 4;
        motorcycleObject.removeObject(path, testID);

        assertNotEquals(testData, motorcycleObject.getData(), "Error: Object was not removed");
    } // end removeObject test method

    @Test
    @DisplayName("Remove object option two test")
    void testRemoveObject()
    {
        int testID = 7;
        int testYear = 2019;
        motorcycleObject.removeObject(path, testID, testYear);

        // Object is removed and compared with previous ArrayList stored
        assertNotEquals(testData, motorcycleObject.getData(), "Error: Object was not removed");

    } // end testRemoveObject method

    @Test
    @DisplayName("Update object test")
    void updateObjTest()
    {
        int testID = 5;
        double testSalesTax = 0.09;

        motorcycleObject.updateObj(path, testID, testSalesTax);

        assertNotEquals(testData, motorcycleObject.getData(), "Error: Object was not updated");

    } // end updateObj test method

    @Test
    @DisplayName("Compute total price test")
    void calculateTotalPrice()
    {
        double salesTaxTest = 0.09;
        double currentPriceTest = 19985.23;

        // Test Calculation
        double testTotalPrice = (salesTaxTest * currentPriceTest) + currentPriceTest;

        assertEquals(testTotalPrice, motorcycleObject.calculateTotalPrice(salesTaxTest, currentPriceTest), "Error: Calculation results do not match");
    } // end calculateTotalPrice test method

    @Test
    @DisplayName("Display motorcycle by filter test")
    void displayLog()
    {
        // Options are between 1 - 4
        int testUserInput = 3;

        objectStatus = new AvailabilityLog();

        objectStatus.displayStatus(path, testUserInput);

        // Test to determine whether the object returns null
        assertNotNull(objectStatus);
    } // end displayLog test method
} // end MotorcycleTest class