import java.util.ArrayList;

/**
 * This class creates a administrative account for the online banking section of
 * the overall banking app. admin accounts will be utilised by company staff to
 * enable standard users accounts to be reset if they forget their password or lock themselves out
 * their account. the Class inherits functionality and fields
 * from the abstract class BankAccount and implements the BankAccountAdministratorUserInterface
 * interface for standardised method implementations.
 */
public class BankAccountAdministrator extends BankAccountUser implements BankAccountAdministratorInterface {
    private ArrayList<BankAccountUser> bankAccountUsers;

    /**
     * Constructor for an admin online user account
     * @param userName a unique string signature that IDs a admin users online account
     * @param password a unique string signature that verifies the identity of a admine users online account
     */
    public BankAccountAdministrator(String userName, String password){

        super(userName, password);
        this.bankAccountUsers = new ArrayList<>();

    }

    /**
     * getter for get account user list
     * @return  the list of standard online user accounts thta have been added to an admins
     * list of responsibility
     */
    private ArrayList<BankAccountUser> getAccountUsers(){
        return this.bankAccountUsers;
    }

    /**
     * getter for account user list
     * @return the size of the list that contains a series of standard user online accounts that the admin
     * account is responsible for.
     */
    public int getAccountUserSize(){
        return this.getAccountUsers().size();
    }

    /**
     * Allows admin to log into their online account.
     * @param password The password provided for the login; this is
     */
    public void login(String password){

        try{
            super.login(password);
            System.out.println(" Admin Login Successful");

        }catch (IllegalArgumentException e){
            this.setLoggedIn(false);
            System.out.println("Admin Login Failed: An incorrect password has been entered");
        }
    }

    /**
     * add a standard online users account to the admins lis of responsibility
     * @param user The user to be added to the responsibility of the
     */
    public void addUser(BankAccountUser user){

        if(this.getLoggedIn()) {
            this.bankAccountUsers.add(user);
        }

    }

    /**
     * This function searches the admins responsibility list for a specific standard user
     * The function will only allow standard user accounts to be reset and only standard user accounts
     * that are found within the list of the admins responsibility
     * @param accountStandardUser the standard user account the admin wishes to reset
     * @param password The new password for the account that is to be
     */
    public void resetAccount(BankAccountUser accountStandardUser, String password){

        boolean match = false;

        if(this.getLoggedIn()) {
            for (BankAccountUser user : this.getAccountUsers()) {

                // check to see if the objects point to the same class
                // check that the username variables are the same

                if ((user == accountStandardUser) && (user.getUserName().equals(accountStandardUser.getUserName()))) {
                    try {

                        BankAccountStandardUser user1 = (BankAccountStandardUser) user;
                        user1.setLoggedIn(true);
                        user1.setLoginAttempts(0);
                        user1.setPassword(password);
                        match = true;
                        user1.logout();
                        break;

                    } catch (ClassCastException e) {

                        System.out.println("Admin function failure: The specified bank account is invalid. Only standard user bank accounts can be reset via administrative functions");
                        break;
                    }
                }

            }

        }

        if(!match){
            System.out.println("The administrator is not responsible for this account: The bank account can not be found within the administrator bank account users list. \n" +
                    "Please add the account to the administrators bank account users list");

        }

    }

    /**
     * revemoes a standard user account from the admins list of responsibility.
     * @param accountStandardUser the standard user account the admin wishes to remove
     */
    public void removeAccount(BankAccountUser accountStandardUser){

        boolean match = false;

        if(this.getLoggedIn()) {
                for (int i = 0; i < this.getAccountUsers().size(); i++) {

                    // check to see if the objects point to the same class
                    // check that the username variables are the same

                    if ((this.getAccountUsers().get(i) == accountStandardUser) && (this.getAccountUsers().get(i).getUserName().equals(accountStandardUser.getUserName()))) {

                        this.getAccountUsers().remove(i);
                        match = true;
                        break;
                    }

                }


        }

        if(!match){
            System.out.println("The administrator is not responsible for this account: The bank account can not be found within the administrator bank account users list. \n" +
                    "Please add the account to the administrators bank account users list");

        }

    }

    /**
     * function clears all the accounts from the admins list of responsibility. This resets the list back to an
     * empty list.
     */
    public void removeAllAccounts(){
        this.getAccountUsers().clear();
    }

}
