package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {

    Currency SEK, DKK, NOK, EUR;
    Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

    @Before
    public void setUp() throws Exception {
        // Inicjalizacja instancji walut i kwot
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);

        // Utworzenie instancji Money dla testów
        SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
    }

    @Test
    public void testGetAmount() {
        // Sprawdzenie, czy metoda getAmount zwraca poprawną kwotę
    	assertEquals(Integer.valueOf(10000), SEK100.getAmount());
    }

    @Test
    public void testGetCurrency() {
        // Sprawdzenie, czy metoda getCurrency zwraca poprawną walutę
    	assertEquals(SEK, SEK100.getCurrency());
    }

    @Test
    public void testToString() {
        // Sprawdzenie, czy metoda toString generuje oczekiwany format napisu
    	assertTrue("100,00 SEK".equals( SEK100.toString()));
    }

    @Test
    public void testGlobalValue() {
        // Sprawdzenie, czy metoda globalValue poprawnie oblicza wartość w globalnej walucie
    	assertEquals(Integer.valueOf(1500), SEK100.universalValue());
    }

    @Test
    public void testEqualsMoney() {
        // Sprawdzenie, czy metoda equals porównuje Money z innym Money
    	assertFalse(EUR0.equals(SEK100));
    	assertTrue(SEK100.equals(EUR10));
    }

    @Test
    public void testAdd() {
        // Sprawdzenie, czy metoda add poprawnie dodaje dwie kwoty Money
    	 Money result = SEK100.add(EUR10);
	     Money expected = new Money(20000, SEK);
	     assertTrue(expected.equals(result));
    }

    @Test
    public void testSub() {
        // Sprawdzenie, czy metoda sub poprawnie odejmuje dwie kwoty Money
    	   Money result = SEK200.sub(EUR20);
	       Money expected = new Money(0, SEK);
	       assertTrue(expected.equals(result));
    }

    @Test
    public void testIsZero() {
        // Sprawdzenie, czy metoda isZero identyfikuje poprawnie kwoty zerowe
    	assertFalse(SEK100.isZero());
        assertTrue(SEK0.isZero());
    }

    @Test
    public void testNegate() {
        // Sprawdzenie, czy metoda negate poprawnie zmienia znak kwoty Money
    	Money result = SEKn100.negate();
        Money expected = new Money(10000, SEK);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testCompareTo() {
        // Sprawdzenie, czy metoda compareTo porównuje Money ze sobą
    	System.out.println(SEK100.compareTo(EUR10));
        assertTrue(SEK100.compareTo(EUR10) == 0);
        assertTrue(EUR10.compareTo(SEK0) > 0);
        assertEquals(0, SEK100.compareTo(SEK100));
    }
}
