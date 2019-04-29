/**
 * This class defines only the general methods to be inherited by BankAccountStandardUser and
 * BankAccountAdministrator
 */

public abstract class BankAccountUser implements BankAccountUserInterface {
    private String userName;
    private String password;
    private boolean loggedIn;

    /**
     * constructor for online BankAccount object
     * @param userName a unique string signature that IDs a users BankAccount
     * @param password a unique string signature that verifies the identity of a users BankAccount
     */
    public BankAccountUser(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.loggedIn = false;
    }

    /**
     * This method allows a user to log into their online Bank Account
     * @param password The password provided that will be compared to
     *  the password stored on the system, i.e., the value of the
     */
    public void login(String password){
        if (passwordCorrect(password)){
            this.setLoggedIn(true);
        }else{
            throw new IllegalArgumentException();
        }

    }

    /**
     * This method allows a user to log into their online Bank Account
     */
    public void logout(){
        this.setLoggedIn(false);
    }

    /**
     *
     * @param password A password string that is to be compared to the
     * stored password.
     * @return true if the password is correct or false if the inputted password does not match the
     * set user objects password
     */
    public  boolean passwordCorrect(String password) {

        return password.equals(this.getPassword());

    }

    /**
     * setter for a users to reset password
     * @param Password the new password to replace the old.
     */
    public void setPassword(String Password){

        if (this.getLoggedIn()) {
            this.password = Password;
        }
    }

    /**
     * getter for a users objects current password string.
     * this is kept private so only the class can access this method
     * for security reasons
     * @return user objects current password.
     */
    private String getPassword(){
        return this.password;
    }

    /**
     * getter for a user objects current logged in status
     * @return true if user object is logged in and false if logged out.
     */
    public boolean getLoggedIn(){
        return this.loggedIn;
    }

    /**
     * Setter for the current logged in status of a user object
     * @param loggedIn New value for the variable loggedIn
     */
    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    /**
     * getter for the username string of the current user name object in question
     * @return a String corresponding the ID signature of a user object
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * setter username string of the current user name object in question
     * @param userName  a String corresponding the new ID signature of a user object
     */
    public void setUserName(String userName){
        this.userName = userName;
    }








}
