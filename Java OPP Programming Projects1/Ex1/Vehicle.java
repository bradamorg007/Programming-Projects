/**
 * Abstract method defines the generic parameters and functions that defines the general parameters
 *  and behaviours of a vehicle.
 */
public class Vehicle {

    private int passengerNumber;
    private int maxSpeed;
    private double mileage;

    /**
     * Constructor method for Vehicle
     * @param passengerNumber = maximum number of people a vehicle can hold.
     * @param maxSpeed = maximum speed that the vehicle can travel at.
     */
    public Vehicle( int passengerNumber, int maxSpeed){

        this.passengerNumber = passengerNumber;
        this.maxSpeed = maxSpeed;
        this.mileage = 0;

    }

    /**
     * Getter for maximum number of passengers for a specific vehicle object.
     * @return The maximum number of passengers
     */
    public int getPassengerNumber(){
        return this.passengerNumber;
    }

    /**
     * Getter for the maximum speed of a vehicle object.
     * @return the maximum speed of the vehicle object.
     */
    public int getMaxSpeed(){
        return this.maxSpeed;
    }

    /**
     * Setter for the maximum number of passengers for a vehicle object
     * @param passengerNumber the new number of maximum passengers
     */

    public void setPassengerNumber(int passengerNumber){
        this.passengerNumber = passengerNumber;
    }

    /**
     * Getter for the total distance travelled by a vehicle object.
     * @return the total distance travelled of vehicle since object creation.
     */
    public double getMileage() {
        return mileage;
    }

    /**
     * Setter for the total distance travelled by a vehicle object.
     * @param mileage the total distance travelled of vehicle since object creation
     */
    public void setMileage(double mileage) {
        this.mileage = this.getMileage() + mileage;
    }

    /**
     * Setter for the maximum speed of a vehicle object.
     * @param maxSpeed
     */
    public void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
    }

   public  String toString(){

      return  "Max Passengers: " + this.getPassengerNumber() + "  Max Speed: " +
              this.getMaxSpeed()+"Km/h" + "\n" + "  Vehicle Milage: " + this.getMileage();
   }


}
