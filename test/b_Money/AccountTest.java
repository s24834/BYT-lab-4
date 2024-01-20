package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

    @Before
    public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

    @Test
    public void testAddRemoveTimedPayment() {
        // Test adding and removing timed payments
    	assertFalse(testAccount.timedPaymentExists("payment1"));
        testAccount.addTimedPayment("payment1", 2, 1, new Money(5000, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("payment1"));

        testAccount.removeTimedPayment("payment1");
        assertFalse(testAccount.timedPaymentExists("payment1"));
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        // Test timed payment execution
    	SweBank.deposit("Alice", new Money(1000, SEK));
        testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");

        testAccount.tick(); // za pierwszym razem płatność nie powinna zostać jeszcze zainicjalizowana
        assertEquals(Integer.valueOf(9999500), testAccount.getBalance().getAmount());
        assertEquals(Integer.valueOf(1001500), SweBank.getBalance("Alice"));

    }

    @Test
    public void testAddWithdraw() {
        // Test deposit and withdrawal
    	testAccount.deposit(new Money(5000, SEK));
        assertEquals(Integer.valueOf(10005000), testAccount.getBalance().getAmount());

        testAccount.withdraw(new Money(2000, SEK));
        assertEquals(Integer.valueOf(10003000), testAccount.getBalance().getAmount());
    }

    @Test
    public void testGetBalance() {
        // Test getBalance
    	assertEquals(Integer.valueOf(10000000), testAccount.getBalance().getAmount());

        testAccount.deposit(new Money(5000, SEK));
        assertEquals(Integer.valueOf(10005000), testAccount.getBalance().getAmount());

        testAccount.withdraw(new Money(2000, SEK));
        assertEquals(Integer.valueOf(10003000), testAccount.getBalance().getAmount());
    }
}
