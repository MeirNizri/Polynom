package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest {

	@Test
	void testComplexFunction() {
		ComplexFunction expected = new ComplexFunction();
		ComplexFunction actual = new ComplexFunction("None(0,)");
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionFunction() {
		function f = new ComplexFunction("Times(2x,x^2)");
		ComplexFunction expected = new ComplexFunction(f);
		ComplexFunction actual = new ComplexFunction("None(Times(2x,x^2),)");
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionStringFunctionFunction() {
		function f1 = new ComplexFunction("Times(2x,x^2)");
		function f2 = new ComplexFunction("Max(2x^3,4x)");
		ComplexFunction expected = new ComplexFunction("Divid",f1,f2);
		ComplexFunction actual = new ComplexFunction("Divid(Times(2x,x^2),Max(2x^3,4x))");
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionString() {
		String [] goodStrings = {"None(2x,x^2)", "None(,)", "Times(0x^2+2x,0)", "Times(Divid(2x,3),None(4x,x^2))", ""};
		ComplexFunction [] expected = {new ComplexFunction("2x"), new ComplexFunction("0"), new ComplexFunction("Times(2x,0)"), 
									new ComplexFunction("Times(Divid(2x,3),4x)"), new ComplexFunction("None(,3x)")};
		for(int i=0; i<expected.length; i++) {
			ComplexFunction actual = new ComplexFunction(goodStrings[i]);
			assertEquals(actual, expected[i]);
		}
		
		String [] badString = {"x^2--3x", "Times(x^2,)", "Max(Min(None(2x+1,4x),5x,6x)", "2x,4x", "Min(None(2x+1,4x)5x)"};
		for(int i=0; i<badString.length; i++) {
			try {
				@SuppressWarnings("unused")
				ComplexFunction toFail = new ComplexFunction(badString[i]);
			    fail( "the constructor didn't throw exception for bad String" + badString[i]);
			} 
			catch (RuntimeException e) {}
		}	
	}

	@Test
	void testInitFromString() {
		ComplexFunction cf = new ComplexFunction("");
		function actual = cf.initFromString("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),25))");
		function expected = new ComplexFunction("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),25))");
		assertEquals(actual, expected);
	}

	@Test
	void testLeft() {
		ComplexFunction cf = new ComplexFunction("Min(Times(2x,x^2),None(5x^3-42,x^3))");
		function expected = cf.left();
		function actual = new ComplexFunction("Times(2x,x^2)");
		assertEquals(actual, expected);
	}

	@Test
	void testRight() {
		ComplexFunction cf = new ComplexFunction("Min(Times(2x,x^2),None(5x^3-42,x^3))");
		function expected = cf.right();
		function actual = new ComplexFunction("5x^3-42");
		assertEquals(actual, expected);
	}

	@Test
	void testGetOp() {
		ComplexFunction cf = new ComplexFunction("Min(Times(2x,x^2),None(5x^3-42,x^3))");
		Operation expected = cf.getOp();
		Operation actual = Operation.Min;
		assertEquals(actual, expected);
	}

	@Test
	void testToString() {
		ComplexFunction [] Cfs = {new ComplexFunction("None(,)"), new ComplexFunction("-x^3+5x^2+2"),
								new ComplexFunction("Divid(Times(2x,x^2),Max(2x^3,4x))"),
								new ComplexFunction("None(Divid(Times(2x,x^2),3x),2x+5)"),
								new ComplexFunction("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),25))")};
		String [] expected = {"0", "-x^3+5.0x^2+2.0", "Divid(Times(2.0x, x^2), Max(2.0x^3, 4.0x))",
							"Divid(Times(2.0x, x^2), 3.0x)", "Max(Min(0, 0), Divid(Plus(2.0x^5+2.0, 0), 25.0))"};
		for(int i=0; i<expected.length; i++) {
			String actual = Cfs[i].toString();
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testCopy() {
		ComplexFunction cf = new ComplexFunction("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),25))");
		function actual = cf.copy();
		function expected = new ComplexFunction("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),25))");
		assertEquals(actual, expected);
	}

	@Test
	void testPlus() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.plus(f);
		ComplexFunction actual = new ComplexFunction("Plus(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMul() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.mul(f);
		ComplexFunction actual = new ComplexFunction("Times(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testDiv() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.div(f);
		ComplexFunction actual = new ComplexFunction("Divid(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMax() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.max(f);
		ComplexFunction actual = new ComplexFunction("Max(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMin() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.min(f);
		ComplexFunction actual = new ComplexFunction("Min(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testComp() {
		ComplexFunction expected = new ComplexFunction("Min(Times(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.comp(f);
		ComplexFunction actual = new ComplexFunction("Comp(Min(Times(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testF() {
		ComplexFunction [] Cfs = {new ComplexFunction("None(,)"), new ComplexFunction("-x^3+5x^2+2"),
								new ComplexFunction("Divid(Times(2x,x^2),Max(2x^3,4x))"),
								new ComplexFunction("None(Divid(Times(2x,x^2),4x),2x+5)"),
								new ComplexFunction("Max(Min(None(,0),0x^3),Divid(Plus(2x^5+2,0x),11))"),
								new ComplexFunction("Comp(Divid(Plus(2x^5+2,0x),11),Min(Plus(3.5,2x),3.5))")};
		double [] expected = {0.0, 14.0, 1.0, 2.0, 6.0, 95.67613636363636};
		for(int i=0; i<expected.length; i++) {
			double actual = Cfs[i].f(2);
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testEquals() {
		ComplexFunction cf = new ComplexFunction("Plus(Divid(Times(2x,x^2),-4x),2x^3)");
		Polynom p = new Polynom("-0.5x^2+2x^3");
		String s = "-0.5x^2+2x^3";
		boolean isTrue1 = cf.equals(p);
		boolean isTrue2 = cf.equals(s);
		assertTrue(isTrue1);
		assertTrue(isTrue2);
	}
}