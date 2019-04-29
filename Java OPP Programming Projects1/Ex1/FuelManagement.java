/**
 * Astract Class that describes the generic instructions for managing the fuel and engine usage of vehicle regardless
 * of further vehicle sub-divisions such as public transport vs non public transport vehicle subclasses.
 */
public abstract class FuelManagement extends Vehicle{
    private double tankSize;
    private double fuelInTheTank;
    private boolean engineOn;
    private double fuelConsumption;
    private static final double REFUEL_SAFETY_LIMIT = 0.9;

    /**
     * Constructor for a fuel managed vehicle
     * @param passengerNumber maximum number of pasengers the vehicle can hold.
     * @param maxSpeed the maximum speed the vehicle can travel at.
     * @param tankSize the amount of fuel a vehicle can carry
     * @param fuelConsumption the rate at which the vehicle uses fuel to function
     */
    public FuelManagement(int passengerNumber, int maxSpeed, double tankSize, double fuelConsumption){

        super(passengerNumber, maxSpeed);
        this.fuelInTheTank = 0;
        this.tankSize = tankSize;
        this.engineOn = false;
        this.fuelConsumption = fuelConsumption;

    }

    /**
     * Removes fuel from vehicle fuel tank
     * @param amountLitres amount of litres of fuel to remove
     */
    public void depleteFuel(double amountLitres){

            if (amountLitres > this.getFuelInTheTank()) {
                throw new IllegalArgumentException();
            }
            this.setFuelInTheTank(this.getFuelInTheTank() - amountLitres);
    }

    /**
     * allows a vehicles fuel tank to be refilled
     * @param amountLitres amount to add to the fuel tank
     */
    public void refuel(double amountLitres){

        try{

            if((amountLitres*REFUEL_SAFETY_LIMIT) > this.getTankSize()){
                this.setFuelInTheTank(this.getFuelInTheTank() + (this.getTankSize() - this.getFuelInTheTank()));
                throw new IllegalArgumentException();
            }

            this.setFuelInTheTank(getFuelInTheTank() + amountLitres);

        }catch (IllegalArgumentException e){

            System.out.println("The fuel tank is full. The refuel amount exceeds saftey limits. The fuel tank will now automatically close to prevent damage");
        }

    }

    // calculated in litres and Km

    /**
     * very simplistic method for calculating fuel depletion based on distance and fuel consumption.
     * The method is used to just simulate car movement
     * @param distanceTravelled distance the driver wish to move.
     */
    public void moveVehicle(double distanceTravelled){


        try {
            if (!engineOn) {
                throw  new IllegalArgumentException();
            }

            double litreCost = (distanceTravelled / 100) * this.getFuelConsumption();
            depleteFuel(litreCost);
            this.setMileage(distanceTravelled);
            this.setEngineOn(true);
            System.out.println("============================================================");
            System.out.println("The vehicle moves " + distanceTravelled+"Km");
            System.out.println("============================================================");

        }catch (IllegalArgumentException e){
            System.out.println("============================================================");
            System.out.println(" The Car can not move due to the engine being offline or there being not enough fuel to complete a specified journey distance."+ "\n"+
                    " Please Either switch the engine on or refuel.");
            System.out.println("============================================================");
        }

    }

    /**
     * function checks if the fuel tank is empty.
     * @return true if fuel tank is empty and false otherwise
     */
    public boolean emergencyStop() {
        if(this.getFuelInTheTank() <= 0){
            return true;
        }

        return false;
    }

    /**
     * getter for fuel tank size.
     * @return the size of a vehicle objects fuel tank
     */
    public double getTankSize() {
        return tankSize;
    }

    /**
     * setter for fuel tank size.
     * @param tankSize the new size of the vehicles fuel tank
     */
    public void setTankSize(double tankSize) {
        this.tankSize = tankSize;
    }

    /**
     * getter for fuel in the tank
     * @return how much fuel there is left in the fuel tank of  vehicle
     */
    public double getFuelInTheTank() {
        return fuelInTheTank;
    }

    /**
     * setter for the amount of fuel in the tank
     * @param amountLitres the new amount of fuel to add to the fuel tank
     */
    private void setFuelInTheTank(double amountLitres){
        this.fuelInTheTank = amountLitres;
    }

    /**
     *getter engine active check
     * @return true if the engine is on, false if the engine is off
     */
    public boolean isEngineOn() {
        return engineOn;
    }

    /**
     * getter for fuel consumption
     * @return the fuel consumption of a vehile object
     */
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * setter for fuel consumption
     * @param fuelConsumption new value for few consumption variable.
     */

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    /**
     * Vehicle ignition
     * @param engineOn car is on only if there is enough fuel in the tank
     */
    public void setEngineOn(boolean engineOn) {

            if(emergencyStop()){
                this.engineOn = false;
                throw new IllegalArgumentException("There is not enough Fuel in the tank");

            }
            this.engineOn = engineOn;


    }

    public String toString(){

       return  super.toString() + "  Fuel Tank Size: " + this.getTankSize()+"L" + "  Fuel: " +
                this.getFuelInTheTank()+"L" +
               "  Fuel Consumption: " + this.getFuelConsumption()+ "\n" +"  Engine On: " + this.isEngineOn() + "\n";
    }

}
