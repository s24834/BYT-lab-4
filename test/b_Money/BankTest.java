package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

    @Test
    public void testGetName() {
        // Test getName
    	assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
    }

    @Test
    public void testGetCurrency() {
        // Test getCurrency
    	assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        // Test openAccount
    	try {
            SweBank.openAccount("John");
            assertTrue(true);
        } catch (AccountExistsException e) {
            fail("Account should not exist");
        }

        try {
            SweBank.openAccount("Bob");
            fail("AccountExistsException should be thrown");
        } catch (AccountExistsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        // Test deposit
    	SweBank.deposit("Ulrika", new Money(5000, SEK));
        assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
    	// Test withdraw
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.withdraw("Ulrika", new Money(3000, SEK));
        assertEquals(Integer.valueOf(7000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        // Test getBalance
    	SweBank.deposit("Bob", new Money(2000, SEK));
        assertEquals(Integer.valueOf(2000), SweBank.getBalance("Bob"));
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        // Test transfer
    	SweBank.deposit("Ulrika", new Money(5000, SEK));
        Nordea.deposit("Bob", new Money(2000, SEK));
        SweBank.transfer("Ulrika", Nordea, "Bob", new Money(3000, SEK));

        assertEquals(Integer.valueOf(2000), SweBank.getBalance("Ulrika"));
        assertEquals(Integer.valueOf(5000), Nordea.getBalance("Bob"));
    }
    
    @Test
    public void testTransferSameBank() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(5000, SEK));
        SweBank.deposit("Bob", new Money(2000, SEK));
        SweBank.transfer("Ulrika", "Bob", new Money(3000, SEK));

        assertEquals(Integer.valueOf(2000), SweBank.getBalance("Ulrika"));
        assertEquals(Integer.valueOf(5000), SweBank.getBalance("Bob"));
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException, AccountExistsException {
        // Test timed payment
    	SweBank.deposit("Ulrika", new Money(5000, SEK));
        SweBank.addTimedPayment("Ulrika", "payment1", 2, 1, new Money(1000, SEK), SweBank, "Bob");

        SweBank.tick();
        assertEquals(Integer.valueOf(4000), SweBank.getBalance("Ulrika"));
        assertEquals(Integer.valueOf(1000), SweBank.getBalance("Bob"));

        SweBank.tick();
        assertEquals(Integer.valueOf(4000), SweBank.getBalance("Ulrika"));
        assertEquals(Integer.valueOf(1000), SweBank.getBalance("Bob"));
    }
}
