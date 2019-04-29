/**
 *
 *Astract Class that describes the generic instructions for calculating  public transport service charges.
 * This class is general enough to could be used by further subdivisions of public transport vehicles such as
 * a bus and taxi. they are very different vehicles overall, but they both share the same sunctionility of charging
 * a customer for a fare. Thus the code for this function only needs to be written once and can be inherited from the subclass vehicles.
 */
public abstract class PublicTransport extends FuelManagement {

    private int driverBalance;

    /**
     * constructor for a public transport vehicle.
     * @param passengerNumber maximum number of pasengers the vehicle can hold.
     * @param maxSpeed the maximum speed the vehicle can travel at.
     * @param tankSize the amount of fuel a vehicle can carry
     * @param fuelConsumption the rate at which the vehicle uses fuel to function
     */
    public PublicTransport(int passengerNumber, int maxSpeed, double tankSize, double fuelConsumption){

        super(passengerNumber, maxSpeed,tankSize, fuelConsumption );
        this.driverBalance = 0 ;
    }

    /**
     * Function charges onboarding customer a public transport fee
     * @param fare customer fee
     * @param payment the amount given by the customer to pay for the fee
     * @return the change if any for the customer
     */
    public int chargeCustomer(int fare, int payment){

            if (payment < fare || fare < 0) {

                throw new IllegalArgumentException("The Passenger has insufficient funds for this fare or the Fare has been set to a negative value");
            }

            int change = payment % fare;

            System.out.println("============================================================");
            System.out.println("Customers Change = £" + change);
            System.out.println("============================================================");

            this.setDriverBalance(this.getDriverBalance() + (payment - change));


        return change;

    }

    /**
     * getter for driver balance
     * @return the current on board amount of cash the driver
     */
    public int getDriverBalance() {
        return driverBalance;
    }

    /**
     * setter driver balance.
     * @param onBoardChange the new balance for the on board transportation driver
     */
    public void setDriverBalance(int onBoardChange) {
        this.driverBalance = onBoardChange;
    }

    public String toString(){
        return super.toString() + "  DRIVER BALANCE: £"+ this.getDriverBalance();
    }
}
