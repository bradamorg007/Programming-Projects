import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Ex3MyTests {

    private BankAccountAdministrator admin1, admin2;
    private Customer customerBrad, customerLily, customerMatty, customerPerson1, customerPerson2, customerPerson3;
    private BankAccount bankAccountBrad, bankAccountLily, bankAccountMatty, bankAccountPerson1, bankAccountPerson2, bankAccountPerson3;
    private BankAccountStandardUser brad, lily, matty, person1, person2, person3;

    @Before
    public void setUp() {
        admin1 = new BankAccountAdministrator("Sam", "sesameUser");
        admin2 = new BankAccountAdministrator("Tim", "poppyUser");

        customerBrad = new Customer("brad", "m", "Bham", "0121");
        customerLily = new Customer("lily", "f", "Bham", "0121");
        customerMatty = new Customer("matty", "x", "Bham", "0121");

        customerPerson1 = new Customer("person1", "m", "Bham", "01924");
        customerPerson2 = new Customer("person2", "f", "MIRF", "01876");
        customerPerson3 = new Customer("person3", "m", "LEEDS", "01987");

        bankAccountBrad = new BankAccount(customerBrad, "wheat");
        bankAccountLily = new BankAccount(customerLily, "linseed");
        bankAccountMatty = new BankAccount(customerMatty, "corn");

        bankAccountPerson1 = new BankAccount(customerPerson1, "runner");
        bankAccountPerson2 = new BankAccount(customerPerson2, "sunBeam");
        bankAccountPerson3 = new BankAccount(customerPerson3, "wavesIntheBack");

        brad = new BankAccountStandardUser("brad", "wheatUser", bankAccountBrad);
        lily = new BankAccountStandardUser("lily", "linseedUser", bankAccountLily);
        matty = new BankAccountStandardUser("matty", "cornUser", bankAccountMatty);

        person1 = new BankAccountStandardUser("person1", "runner123", bankAccountPerson1);
        person2 = new BankAccountStandardUser("person2", "sunBeam123", bankAccountPerson2);
        person3 = new BankAccountStandardUser("person3", "wavesIntheback123", bankAccountPerson3);

    }

    // ADMINISTRATOR TESTS

    @Test
    public void TestAdminUserListLoop(){

        admin1.login("sesameUser");
        admin1.addUser(person1);
        admin1.addUser(person2);
        admin1.addUser(person3);
        admin1.addUser(brad);
        admin1.addUser(lily);
        admin1.addUser(matty);

        admin1.resetAccount(lily, "RUBY123!");
        admin1.resetAccount(person1, "moonJump123!");

        int expectedLoginAttempts = 0;
        int expectedLoginAttempts2 = 0;

        int actualLoginAttempts = lily.getLoginAttempts();
        int actualloginAttempts2 = person1.getLoginAttempts();

        assertEquals(expectedLoginAttempts, actualLoginAttempts);
        assertEquals(expectedLoginAttempts2, actualloginAttempts2);

        lily.login("linseedUser");
        assertFalse("assert false old password was true 1", lily.getLoggedIn());

        lily.login("RUBY123!");
        assertTrue("assert true new password was false 1", lily.getLoggedIn());

        person1.login("runner123");
        assertFalse("assert false old password was true 2", person1.getLoggedIn());

        person1.login("moonJump123!");
        assertTrue("assert true new password was false 2", person1.getLoggedIn());

    }


    @Test
    public void TestAdminUserListRemoveAll(){

        admin1.login("sesameUser");
        admin1.removeAllAccounts();

        admin1.addUser(person1);
        admin1.addUser(person2);
        admin1.addUser(person3);
        admin1.addUser(brad);
        admin1.addUser(lily);
        admin1.addUser(matty);

        int expectedListSize = 6;
        int actualListSize = admin1.getAccountUserSize();

        assertEquals(expectedListSize, actualListSize);

        admin1.removeAllAccounts();

        int expectedListSize2 = 0;
        int actualListSize2 = admin1.getAccountUserSize();

        assertEquals(expectedListSize2, actualListSize2);
    }


    @Test
    public void TestAdminUserRemoveAccount(){

        admin2.login("poppyUser");
        admin2.removeAllAccounts();

        admin2.addUser(person1);
        admin2.addUser(person2);
        admin2.addUser(person3);
        admin2.addUser(brad);
        admin2.addUser(lily);
        admin2.addUser(matty);

        int expectedSize1 = 6;
        int actualSize1 = admin2.getAccountUserSize();

        assertEquals(expectedSize1, actualSize1);

        admin2.removeAccount(person3);

        int expectedSize2 = 5;
        int actualSize2 = admin2.getAccountUserSize();
        assertEquals(expectedSize2, actualSize2);

    }


    @Test
    public void adminFindNonExistingAccount() {

        matty.login("jellyCheeks");
        assertFalse("assert false old password was true 1", matty.getLoggedIn());
        matty.login("FIRE_Pistol");
        assertFalse("assert false old password was true 1", matty.getLoggedIn());
        matty.login("DrunkenDrumfishing");
        assertFalse("assert false old password was true 1", matty.getLoggedIn());
        matty.login("ApassWordNoOneWillGuess");
        assertFalse("assert false old password was true 1", matty.getLoggedIn());

        admin2.login("poppyUser");
        admin2.removeAllAccounts();

        admin2.addUser(person1);
        admin2.addUser(person2);
        admin2.addUser(person3);
        admin2.addUser(brad);
        admin2.addUser(lily);
        admin2.addUser(matty);

        int expectedSize1 = 6;
        int actualSize1 = admin2.getAccountUserSize();
        assertEquals(expectedSize1, actualSize1);

        admin2.removeAccount(matty);

        int expectedSize2 = 5;
        int actualSize2 = admin2.getAccountUserSize();
        assertEquals(expectedSize2, actualSize2);

        admin2.resetAccount(matty, "LIONsBane");

        matty.login("LIONsBane");
        assertFalse("assert false old password was true 1", matty.getLoggedIn());

        int expectedAttemptedLogin = 3;
        int actualAttemptedLogin = matty.getLoginAttempts();
        assertEquals(expectedAttemptedLogin, actualAttemptedLogin);

        matty.setLoginAttempts(2);
        matty.login("cornUser");
        assertTrue("assert true new password was false 1", matty.getLoggedIn());

    }

    @Test
    public void adminFindNonStandardUserAccount() {

        admin2.login("poppyUser");
        admin2.removeAllAccounts();

        admin2.addUser(person1);
        admin2.addUser(person2);
        admin2.addUser(person3);
        admin2.addUser(brad);
        admin2.addUser(lily);
        admin2.addUser(matty);
        admin2.addUser(admin1);

        int expectedSize1 = 7;
        int actualSize1 = admin2.getAccountUserSize();

        admin2.resetAccount(admin1, "LEMONFART");

        admin1.login("LEMONFART");
        assertFalse("assert false old password was true 1", admin1.getLoggedIn());

        admin1.login("sesameUser");
        assertTrue("assert true new password was false 1", admin1.getLoggedIn());

    }
    // Check that standard user can not transfer money without being logged in.

    @Test
    public void loginToTransferStandardUser(){

        brad.transferMoney(bankAccountMatty, 20, "wheat");

        long expectedBradsBalance = 100;
        long actualBradsBalance = brad.getBankAccount().getBalance();

        assertEquals(expectedBradsBalance, actualBradsBalance);

    }



}


