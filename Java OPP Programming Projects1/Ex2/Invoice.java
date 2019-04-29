/**
 * The class creates an very abstract and basic invoice for a users bank account
 */

public class Invoice implements Measurable {

    private String accountNumber;
    private String sortCode;
    private double amount;

    /**
     *
     * @param accountNumber - The unique ID number for a users accout
     * @param sortCode - Unique number used o used to route money transfers between banks within their
     *                   respective countries via their respective clearance organisations.
     * @param amount - arbitrary amount held by the users invoice object.
     */
    public Invoice(String accountNumber, String sortCode, double amount){

        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.amount = amount;

    }

    /**
     * Overrides the Measurable interface getValue
     * @return the amount of the Invoice object
     */
    @Override
    public double getValue(){
        return this.getAmount();

    }


    /**
     * getter for invoice objects amount number
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter for invoice objects amount number
     * @param accountNumber the Unique ID number of a invoice object
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * getter for invoice objects amount number
     * @return the account number
     */
    public String getSortCode() {
        return sortCode;
    }

    /**
     * setter for invoice objects sort code number
     * @param sortCode :
     */
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    /**
     * getter for invoice objects amount number
     * @return the amount if the invice object
     */
    public double getAmount() {
        return amount;
    }

    /**
     * setter for invoice objects amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
