package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex1.Monom;
import Ex1.function;

class MonomTest {

	@Test
	void testMonom() {
		Monom expected = new Monom();
		Monom actual = new Monom(0,0);
		assertEquals(actual, expected);
	}

	@Test
	void testMonomDoubleInt() {
		Monom expected = new Monom((4.0/5), 4);
		Monom actual = new Monom(expected.get_coefficient(),expected.get_power());
		assertEquals(actual, expected);
		
		//when power value are negative
		try {
			@SuppressWarnings("unused")
			Monom toFail = new Monom(1,-2);
		    fail("the constructor didn't throw exception for negative power");
		} 
		catch (RuntimeException e) {}
	}

	@Test
	void testMonomMonom() {
		Monom expected = new Monom((4.0/5), 4);
		Monom actual = new Monom(expected);
		assertEquals(actual, expected);
	}

	@Test
	void testDerivative() {
		Monom m = new Monom((4.0/5), 4);
		Monom actual = m.derivative();
		Monom expected = new Monom((4*4.0/5), 3);
		assertEquals(actual, expected);
	}

	@Test
	void testF() {
		Monom m = new Monom(4.0/5, 4);
		double actual = m.f(11.19);
		double expected = (4.0/5.0) * Math.pow(11.19, 4);
		assertEquals(actual, expected);
	}

	@Test
	void testIsZero() {
		Monom m = new Monom();
		boolean isTrue = m.isZero(); 
		assertTrue(isTrue);
	}

	@Test
	void testMonomString() {
		String [] goodStrings = {"","7 x","-x","4.78","0x^3","3.9x^71"};
		Monom [] expected = {new Monom(), new Monom(7,1), new Monom(-1,1), new Monom(4.78,0), new Monom(), new Monom(3.9,71)};
		for(int i=0; i<expected.length; i++) {
			Monom actual = new Monom(goodStrings[i]);
			assertEquals(actual, expected[i]);
		}
		
		String [] badString = {"%$","2^3", "X","xX","(4","x32","--3X"};
		for(int i=0; i<badString.length; i++) {
			try {
				@SuppressWarnings("unused")
				Monom toFail = new Monom(badString[i]);
			    fail( "the constructor didn't throw exception for bad String" + badString[i]);
			} 
			catch (RuntimeException e) {}
		}	
	}

	@Test
	void testAdd() {
		Monom m = new Monom(1, 3);
		Monom actual = new Monom(2, 3);
		actual.add(m);
		Monom expected = new Monom(3, 3);
		assertEquals(actual, expected);
		
		//when add monoms with different power
		try {
			Monom toFail = new Monom(2, 4);
			toFail.add(m);
		    fail("add didn't throw exception for different power");
		} 
		catch (RuntimeException e) {}
	}

	@Test
	void testMultiply() {
		Monom m = new Monom(1, 3);
		Monom actual = new Monom(2, 3);
		actual.multiply(m);
		Monom expected = new Monom(2, 6);
		assertEquals(actual, expected);
	}

	@Test
	void testToString() {
		Monom [] monoms = {new Monom(), new Monom(7,1), new Monom(-1,2), new Monom(4.78,0), new Monom(3.9,71)};
		String [] expected = {"0","7.0x","-x^2","4.78","3.9x^71"};
		for(int i=0; i<expected.length; i++) {
			String actual = monoms[i].toString();
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testEqualsObject() {
		Monom m1 = new Monom("3x^2");
		Monom m2 = new Monom("3x^2");
		String s = "3x^2";
		boolean isTrue1 = m1.equals(m2);
		boolean isTrue2 = m1.equals(s);
		assertTrue(isTrue1);
		assertTrue(isTrue2);
	}

	@Test
	void testInitFromString() {
		Monom m = new Monom(1,1);
		function actual = m.initFromString("-2.9x^3");
		function expected = new Monom("-2.9x^3");
		assertEquals(actual, expected);
	}

	@Test
	void testCopy() {
		Monom m = new Monom(-2.9,3);
		function actual = m.copy();
		function expected = new Monom("-2.9x^3");
		assertEquals(actual, expected);
	}
}