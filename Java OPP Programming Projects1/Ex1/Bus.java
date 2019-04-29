/**
 * This class defines the bus object which is derived from the general parameters and functions of vehicles
 * but more specifically the bus also is derived from the class of fuel consuming/managed vehicles which in turn
 * is more specifically defined as public transportation vehicle that requires customer payment functionality.
 */
public class Bus extends PublicTransport {
    private static final String VEHICLETYPE = "Bus";


    public Bus(int passengerNumber, int maxSpeed, double fuelConsumption){

        super(passengerNumber, maxSpeed, 500, fuelConsumption);

    }


    /*
     * Here one could add as many methods that are spesfic to the bus object that make it distinct from another form
     * of public transport like a taxi. Wheel number and size maybe a distinguishing factor between the taxi and the bus
      * classes
     */


    /**
     *
     * @return
     */

    public String toString() {

        return "   Vehicle Type: " + VEHICLETYPE + " " + super.toString();

    }
}
