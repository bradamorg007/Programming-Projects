/**
 * This class defines the family Vehicle object which is derived from the general parameters and functions of vehicles
 * but more specifically the bus also is derived from the class of fuel consuming/managed vehicles which in turn
 * is not  defined as public transportation vehicle and thus does not require the inheritance of
 * customer payment functionality.
 */
public class FamilyCar extends FuelManagement {

    private static final String VEHICLETYPE = "Family Estate Car";

    public FamilyCar(int passengerNumber, int maxSpeed, double fuelConsumption){

        super(passengerNumber, maxSpeed, 200, fuelConsumption);

    }


    /*
     * Here one could add as many methods that are specific to the famil car object that make it distinct from another form
     * of private transport like a sports car. Wheel size, engine type and engine tuning functionality maybe a distinguishing
     * factor between the sports car and the family classes that can be detailed in the above class.
     */


    public String toString() {

        return "   Vehicle Type: " + VEHICLETYPE + " " + super.toString();

    }
}
