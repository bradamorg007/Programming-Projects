import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
// ADD MORE TESTS MAYBE

public class Ex1MyTests {
    public static final double TOLERANCE = 0.00001;
    Vehicle vehicle;
    Bus bus;
    FamilyCar familyCar;
    Bicycle bike;

    @Before
    public void setUp() {

        vehicle = new Vehicle(15,87);
        bus = new Bus(100,200,50.3);
        familyCar = new FamilyCar(210,150,33);
        bike = new Bicycle(1, 20);
    }


    // Test1 = public transport payment functions
    @Test
    public void publicTransportTest1(){

        bus.setDriverBalance(50);
        int customerAmount = 8;
        int busFare = 5;

        int expectedCustomerChange = 3;
        int expectedDriverBalance = 55;

        int actualCustomerChange = bus.chargeCustomer(busFare, customerAmount);
        int actualDriverBalance = bus.getDriverBalance();

        assertEquals(expectedCustomerChange, actualCustomerChange);
        assertEquals(expectedDriverBalance, actualDriverBalance);

        bus.setDriverBalance(50);
        int customerAmount2 = 9;
        int busFare2 = 9;

        int expectedCustomerChange2 = 0;
        int expectedDriverBalance2 = 59;

        int actualCustomerChange2 = bus.chargeCustomer(busFare2, customerAmount2);
        int actualDriverBalance2 = bus.getDriverBalance();

        assertEquals(expectedCustomerChange2, actualCustomerChange2);
        assertEquals(expectedDriverBalance2, actualDriverBalance2);

    }

    // Test that customers payment with 0 and negative customer payments and fare amounts
    @Test(expected = IllegalArgumentException.class)
    public void publicTransportTest2(){

        bus.setDriverBalance(543);
        int expectedDriverBalance = 543;
        int customerAmount = 0;
        int busFare = 5;

        bus.chargeCustomer(busFare, customerAmount);
        int actualDriverBalance = bus.getDriverBalance();
        assertEquals(expectedDriverBalance, actualDriverBalance);

        bus.setDriverBalance(0);
        int expectedDriverBalance2 = 0;
        int customerAmount2 = 3;
        int busFare2 = -1;

        bus.chargeCustomer(busFare2, customerAmount2);
        int actualDriverBalance2 = bus.getDriverBalance();
        assertEquals(expectedDriverBalance2, actualDriverBalance2);

        bus.setDriverBalance(1234);
        int expectedDriverBalance3 = 1234;
        int customerAmount3 = -2;
        int busFare3 = -1;

        bus.chargeCustomer(busFare3, customerAmount3);
        int actualDriverBalance3 = bus.getDriverBalance();
        assertEquals(expectedDriverBalance3, actualDriverBalance3);

        bus.setDriverBalance(8);
        int expectedDriverBalance4 = 8;
        int customerAmount4 = -1;
        int busFare4 = -2;

        bus.chargeCustomer(busFare4, customerAmount4);
        int actualDriverBalance4 = bus.getDriverBalance();
        assertEquals(expectedDriverBalance4, actualDriverBalance4);


    }

    // Test getters and setters for fuel tanksize
    @Test
    public void tankSizeTest1(){

        double expectedBusTanksize = 500;
        double actualBusTankSize = bus.getTankSize();

        double expectedFamCarTankSize = 200;
        double actualFamCarTanksize = familyCar.getTankSize();


        assertEquals(expectedBusTanksize, actualBusTankSize, TOLERANCE);
        assertEquals(expectedFamCarTankSize, actualFamCarTanksize, TOLERANCE);

        bus.setTankSize(450);

        double expectedBusTanksize2 = 450;
        double actualBusTankSize2 = bus.getTankSize();

        familyCar.setTankSize(450);

        double expectedFamCarTanksize2 = 450;
        double actualFamCarTankSize2 = familyCar.getTankSize();

        assertEquals(expectedBusTanksize2, actualBusTankSize2, TOLERANCE);
        assertEquals(expectedFamCarTanksize2, actualFamCarTankSize2, TOLERANCE);
    }

    // Fuel management and movement tests for bus, fam car and bike.
    @Test
    public void fuelManagementAndMovementTest1(){

        bus.setTankSize(500);
        familyCar.setTankSize(200);

        bus.refuel(500);
        familyCar.refuel(200);
        bus.setEngineOn(true);
        familyCar.setEngineOn(true);

        bus.moveVehicle(300);
        familyCar.moveVehicle(199);

        //150.9
        //65.67

        double expectedBusFuelInTheTank = 349.1;
        double expectedFamCarFuelInTheTank = 134.33;

        double expectedBusMilage = 300;
        double expectedFamcarMilage = 199;

        double actualBusFuelInTheTank = bus.getFuelInTheTank();
        double actualFamCarFuelInTheTank = familyCar.getFuelInTheTank();

        double actualBusMilage = bus.getMileage();
        double actualFamCarMilage = familyCar.getMileage();

        assertEquals(expectedBusFuelInTheTank, actualBusFuelInTheTank, TOLERANCE);
        assertEquals(expectedFamCarFuelInTheTank, actualFamCarFuelInTheTank, TOLERANCE);

        assertEquals(expectedBusMilage, actualBusMilage, TOLERANCE);
        assertEquals(expectedFamcarMilage, actualFamCarMilage, TOLERANCE);

    }

    // Fuel management and movement tests for bus, fam car. car should not move if not enough fuel in the tank for the
    //movement
    @Test
    public void fuelManagementAndMovementTest2(){

        bus.setTankSize(100);
        familyCar.setTankSize(30);

        bus.refuel(100);
        familyCar.refuel(30);
        bus.setEngineOn(true);
        familyCar.setEngineOn(true);

        bus.moveVehicle(300);
        familyCar.moveVehicle(199);

        //150.9
        //65.67

        double expectedBusFuelInTheTank = 100;
        double expectedFamCarFuelInTheTank = 30;

        double expectedBusMilage = 0;
        double expectedFamcarMilage = 0;

        double actualBusFuelInTheTank = bus.getFuelInTheTank();
        double actualFamCarFuelInTheTank = familyCar.getFuelInTheTank();

        double actualBusMilage = bus.getMileage();
        double actualFamCarMilage = familyCar.getMileage();

        assertEquals(expectedBusFuelInTheTank, actualBusFuelInTheTank, TOLERANCE);
        assertEquals(expectedFamCarFuelInTheTank, actualFamCarFuelInTheTank, TOLERANCE);

        assertEquals(expectedBusMilage, actualBusMilage, TOLERANCE);
        assertEquals(expectedFamcarMilage, actualFamCarMilage, TOLERANCE);

    }

    // Fuel management and movement tests for bus, fam car. So if the bus or car move uses
    // all the fuel then the system should switch the car engine off and it should
    // not be able to switch back on until refueled.
    @Test
    public void fuelManagementAndMovementTest3(){

        bus.setMileage(0);
        familyCar.setMileage(0);
        bus.setFuelConsumption(50);
        familyCar.setFuelConsumption(20);
        bus.setTankSize(500);
        familyCar.setTankSize(100);

        bus.refuel(500);
        familyCar.refuel(100);
        bus.setEngineOn(true);
        familyCar.setEngineOn(true);

        bus.moveVehicle(1000);
        familyCar.moveVehicle(500);

        assertFalse(bus.isEngineOn());
        assertFalse(familyCar.isEngineOn());

        //150.9
        //65.67

        double expectedBusFuelInTheTank = 0;
        double expectedFamCarFuelInTheTank = 0;

        double expectedBusMilage = 1000;
        double expectedFamcarMilage = 500;

        double actualBusFuelInTheTank = bus.getFuelInTheTank();
        double actualFamCarFuelInTheTank = familyCar.getFuelInTheTank();

        double actualBusMilage = bus.getMileage();
        double actualFamCarMilage = familyCar.getMileage();

        assertEquals(expectedBusFuelInTheTank, actualBusFuelInTheTank, TOLERANCE);
        assertEquals(expectedFamCarFuelInTheTank, actualFamCarFuelInTheTank, TOLERANCE);

        assertEquals(expectedBusMilage, actualBusMilage, TOLERANCE);
        assertEquals(expectedFamcarMilage, actualFamCarMilage, TOLERANCE);

        bus.moveVehicle(1000);
        familyCar.moveVehicle(500);

        assertFalse(bus.isEngineOn());
        assertFalse(familyCar.isEngineOn());

    }

    @Test
    public void bikeTest(){

        bike.moveVehicle(30.762);

        double expectedMilage = 30.762;
        double actualMilage = bike.getMileage();

        assertEquals(expectedMilage, actualMilage, TOLERANCE);
    }




}