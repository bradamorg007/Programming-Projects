/**
 * This class defines the bicycle object which is derived from the general parameters and functions of vehicles
 * but is not derived from the class of fuel consuming/managed vehicles which in turn
 * is not defined as a public transportation vehicle that requires customer payment functionality.
 */

public class Bicycle extends  Vehicle {

    private static final String VEHICLETYPE = "Bicycle";
    public Bicycle(int passengerNumber, int maxSpeed){
        super(passengerNumber, maxSpeed);
    }



    public String toString() {

        return "   Vehicle Type: " + VEHICLETYPE + " " + super.toString();

    }

    public void moveVehicle(double distanceTravelled){

        this.setMileage(this.getMileage()+distanceTravelled);
    }
}
