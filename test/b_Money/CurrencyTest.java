package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
    public void testGetName() {
		assertEquals("SEK", SEK.getName());
    }

    @Test
    public void testGetRate() {
    	assertEquals(Double.valueOf(0.15), SEK.getRate());
    }

    @Test
    public void testSetRate() {
    	 SEK.setRate(0.18);
	     assertEquals(Double.valueOf(0.18), SEK.getRate());
    }

    @Test
    public void testGlobalValue() {
    	assertEquals(Integer.valueOf(1500), SEK.universalValue(10000));
    }

    @Test
    public void testValueInThisCurrency() {
    	assertEquals(Integer.valueOf(15000), SEK.valueInThisCurrency(1500, EUR));
    }

}
