package ca.cmpt213.asn4.bank;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    SavingsAccount temp;

    @BeforeEach
    void setUp() {
        try{
            temp = new SavingsAccount(1000,-0.1);  // when any arguments are negative
            fail();
        }
        catch (IllegalArgumentException ex){
            assertTrue(true);
        }

        try{
            temp = new SavingsAccount(-1000,0.1);  // when any arguments are negative
            fail();
        }
        catch (IllegalArgumentException ex){
            assertTrue(true);
        }

        temp = new SavingsAccount(1000,0.1);
    }
    @AfterEach
    void tearDown() {
        temp = null;
    }
    @Test
    void check_state() {                 // if it is inactive
        temp.balance  = 5;
        temp.check_state(temp.balance);
        if (temp.account_state)
            fail();

        temp.balance  = 26;             // if it is active
        temp.check_state(temp.balance);
        if (!temp.account_state)
            fail();
    }

    @Test
    void withdraw() {
        try{
           temp.withdraw(-1);
           fail();
        }
        catch (IllegalArgumentException ex){
            assertTrue(true);
        }

        try{
            temp.withdraw(temp.balance+1000);
            fail();
        }
        catch (IllegalArgumentException ex){
            assertTrue(true);
        }

        temp.withdraw(10);
    }

    @Test
    void deposit() {
        try{
            temp.deposit(-1);        // if it is negative
            fail();
        }
        catch (IllegalArgumentException ex){
            assertTrue(true);
        }

        temp.deposit(1000);
        assertEquals(temp.balance,2000);
    }

    @Test
    void monthlyProcess() {
        for (int i = 0; i < 10; i++)                // if withdraw time more than 4
            temp.withdraw(10);
        assertEquals(temp.withdraw_money,10);

        temp.monthlyProcess();                     // check the calculate is correct
        assertEquals(temp.balance, 901.45);
        assertEquals(temp.withdraw_money,0); // check if reset
        assertEquals(temp.deposit_money,0);  // check if reset
        assertEquals(temp.service_charge,0); // check if reset
    }
}