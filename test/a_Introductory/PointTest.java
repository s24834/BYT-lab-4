package a_Introductory;


import org.junit.Before;
import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}
	
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
		assertTrue(new Integer(4).equals(res1.x));
		assertTrue(new Integer(-21).equals(res1.y));
		assertTrue(new Integer(-3).equals(res2.x));
		assertTrue(new Integer(12).equals(res2.y));
	}
	
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
		assertTrue(new Integer(10).equals(res1.x));
		assertTrue(new Integer(39).equals(res1.y));
		assertTrue(new Integer(17).equals(res2.x));
		assertTrue(new Integer(6).equals(res2.y));
	}
}
