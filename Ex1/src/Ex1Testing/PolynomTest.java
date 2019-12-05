package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;
import Ex1.function;

class PolynomTest {

	@Test
	void testPolynom() {
		Polynom expected = new Polynom();
		Polynom actual = new Polynom("0");
		assertEquals(actual, expected);
	}

	@Test
	void testPolynomString() {
		String [] goodStrings = {"3-7+5-2", "-x^3 + 5x^2 +2", "-x^2-5-x^2-x^3-7", "0x^5+3","3x^0-5x", ""};
		Polynom [] expected = {new Polynom("-1.0"), new Polynom("-x^3+5x^2+2"), new Polynom("-x^3-2x^2-12"), 
								new Polynom("3.0"), new Polynom("-5x+3"), new Polynom("0")};
		for(int i=0; i<expected.length; i++) {
			Polynom actual = new Polynom(goodStrings[i]);
			assertEquals(actual, expected[i]);
		}
		
		String [] badString = {"(-x^2+(3x^5))", "x^2--3x", "%$ - X", "xX + (4 - x32"};
		for(int i=0; i<badString.length; i++) {
			try {
				@SuppressWarnings("unused")
				Polynom toFail = new Polynom(badString[i]);
			    fail( "the constructor didn't throw exception for bad String" + badString[i]);
			} 
			catch (RuntimeException e) {}
		}	
	}

	@Test
	void testInitFromString() {
		Polynom p = new Polynom("x");
		function actual = p.initFromString("-2.9x^3+5x^2-x+0");
		function expected = new Polynom("-2.9x^3+5x^2-x+0");
		assertEquals(actual, expected);
	}

	@Test
	void testToString() {
		Polynom [] polynoms = {new Polynom("-1"), new Polynom("-x^3+5x^2+2"), new Polynom("-x^3-2x^2-12"), 
							  new Polynom("-5x+3"), new Polynom("0")};
		String [] expected = {"-1.0","-x^3+5.0x^2+2.0","-x^3-2.0x^2-12.0","-5.0x+3.0","0"};
		for(int i=0; i<expected.length; i++) {
			String actual = polynoms[i].toString();
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testIsZero() {
		Polynom p = new Polynom();
		boolean isTrue = p.isZero(); 
		assertTrue(isTrue);
	}

	@Test
	void testCopy() {
		Polynom p = new Polynom("-x^3+5x^2+2");
		function actual = p.copy();
		function expected = new Polynom("-x^3+5x^2+2");
		assertEquals(actual, expected);
	}

	@Test
	void testEqualsObject() {
		Polynom p1 = new Polynom("-x^3+5x^2+2");
		Polynom p2 = new Polynom("-x^3+5x^2+2");
		String s = "-x^3+5x^2+2";
		boolean isTrue1 = p1.equals(p2);
		boolean isTrue2 = p1.equals(s);
		assertTrue(isTrue1);
		assertTrue(isTrue2);
	}

	@Test
	void testAddMonom() {
		Monom m = new Monom(1, 3);
		Polynom actual = new Polynom("-x^3+5x^2+2");
		actual.add(m);
		Polynom expected = new Polynom("5x^2+2");
		assertEquals(actual, expected);
	}

	@Test
	void testMultiplyMonom() {
		Monom m = new Monom(-1, 1);
		Polynom actual = new Polynom("-x^3+5x^2+2");
		actual.multiply(m);
		Polynom expected = new Polynom("x^4-5x^3-2x");
		assertEquals(actual, expected);
	}

	@Test
	void testAddPolynom_able() {
		Polynom p = new Polynom("5x^3+4x^2-3x+2");
		Polynom actual = new Polynom("6x^4+2x-2");
		actual.add(p);
		Polynom expected = new Polynom("6x^4+5x^3+4x^2-x");
		assertEquals(actual, expected);
	}

	@Test
	void testSubstract() {
		Polynom p = new Polynom("5x^3+4x^2-3x+2");
		Polynom actual = new Polynom("6x^4+2x-2");
		actual.substract(p);
		Polynom expected = new Polynom("6x^4-5x^3-4x^2+5x-4");
		assertEquals(actual, expected);
	}

	@Test
	void testMultiplyPolynom_able() {
		Polynom p = new Polynom("3x^2+x");
		Polynom actual = new Polynom("4x+2");
		actual.multiply(p);
		Polynom expected = new Polynom("12x^3+10x^2+2x");
		assertEquals(actual, expected);
	}

	@Test
	void testDerivative() {
		Polynom p = new Polynom("3.5x^3-2.3x^2+15x-32");
		Polynom_able actual = p.derivative();
		Polynom_able expected = new Polynom("10.5x^2-4.6x+15");
		assertEquals(actual, expected);
	}

	@Test
	void testF() {
		Polynom p = new Polynom("3.5x^3-2.3x^2+15x-32");
		double actual = p.f(11.19);
		double expected = (3.5*Math.pow(11.19, 3) - 2.3*Math.pow(11.19, 2) + 15*11.19 - 32);
		assertEquals(actual, expected);
	}

	@Test
	void testRoot() {
		Polynom p = new Polynom("x^3+3x+4.0");
		double actual = p.root(-2, 2, 0.001);
		double expected = (-1.0);
		boolean isTrue = Math.abs(actual-expected) < 0.01;
		assertTrue(isTrue, "expect to get -1, but get " + actual);
		
		try {
			@SuppressWarnings("unused")
			double toFail = p.root(2, -2, 0.001);
		    fail("you entered x0>x1, should be x0<x1");
		} 
		catch (IllegalArgumentException e) {}
	}

	@Test
	void testArea() {
		Polynom p = new Polynom("x^3+3x+4.0");
		double actual = p.area(0, 5, 0.001);
		double expected = (213.75);
		boolean isTrue = Math.abs(actual-expected) < 0.1;
		assertTrue(isTrue, "expect to get 213.75, but get " + actual);
		
		try {
			@SuppressWarnings("unused")
			double toFail = p.root(2, -2, 0.001);
		    fail("you entered x0>x1, should be x0<x1");
		} 
		catch (IllegalArgumentException e) {}
	}
}