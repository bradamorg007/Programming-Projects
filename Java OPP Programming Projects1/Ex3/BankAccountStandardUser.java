/**
 * This class creates a standard user bank account for the online banking section of
 * the overall banking app. the Class inherits functionality and fields
 * from the abstract class BankAccount and implements the BankAccountStandardUserInterface
 * interface for standardised method implementations
 */
public class BankAccountStandardUser extends BankAccountUser implements BankAccountStandardUserInterface{
    private BankAccount bankAccount;
    private int loginAttempts;
    private static final int MAXIMAL_LOGIN_ATTEMPTS = 3;


    /**
     * Constructor for the standard User class object
     * @param userName a unique string signature that IDs a users online BankAccount
     * @param password a unique string signature that verifies the identity of a users online BankAccount
     * @param bankAccount an object that contains the actual bank account of user, this object
     *                    allows the user to transfer, withdraw and manage the general details
     *                    of a users fiancees
     */
    public BankAccountStandardUser(String userName, String password, BankAccount bankAccount){

        super(userName, password);
        this.bankAccount = bankAccount;
        this.loginAttempts = 0;


    }

    /**
     * The log in function only allows a password check if the online user has not failed
     * to enter their passwords correctly 3 times. If they meet this criteria then the function will
     * allow a password check if the password check function returns true the user will be allowed to log on
     * with any login attempts lower than 3 attempts being reset to 0.
     * @param password The password provided for the login; this is
     */
    public void login(String password){

        if(this.getLoginAttempts() < MAXIMAL_LOGIN_ATTEMPTS) {

            try{
                super.login(password);
                this.resetLoginAttempts();
                System.out.println("Login Successful");

            }catch (IllegalArgumentException e){
                this.setLoggedIn(false);
                this.setLoginAttempts(this.getLoginAttempts()+1);
                System.out.println("Login Failed: An incorrect password has been entered");
            }

        }else {

            this.setLoggedIn(false);
            System.out.println("The password attempt limit has been reached. For security this account has been locked.\n " +
                    "To unblock your online account Please seek administrative assistance");


        }
    }

    /**
     * This function sets the loginAttempts field to 0 when called.
     */
    public void resetLoginAttempts(){
        this.setLoginAttempts(0);
    }


    /**
     *
     * @return the associated bank account object of an online user bankaccount object
     */
    public BankAccount getBankAccount(){
        return this.bankAccount;
    }

    /**
     *
     * @return the number of failed attempts an online user has made to login
     */
    public int getLoginAttempts(){
        return this.loginAttempts;
    }

    /**
     *
     * @param loginAttempts New value for the variable loginAttempts.
     */
    public void setLoginAttempts(int loginAttempts){


        this.loginAttempts = loginAttempts;
    }

    /**
     * Allows online to user to transfer money from thier associated bank account object to
     * another users bank account object.
     * @param toAccount The account to which the money is to be transferred.
     * @param amount The amount of money to be transferred.
     * @param password The password of the this account.
     */
    public void transferMoney(BankAccount toAccount, long amount, String password){

        if(this.getLoggedIn()){
            this.getBankAccount().transferMoney(toAccount, amount, password);

        }else{

            System.out.println("You must be logged into an account to transfer money");
        }


    }

    /**
     * Function prints the balance of a logged in only online user objects associated bank account
     */
    public void printBalance() {

        if(this.getLoggedIn()) {
            this.getBankAccount().printBalance();
        }
    }

    /**
     *  Function prints a bank statement of a logged in only online user objects associated bank account
     */
    public void printStatement(){

        if(this.getLoggedIn()) {
            this.bankAccount.printStatement();
        }
    }


}
