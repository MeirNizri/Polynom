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
		function f1 = new Polynom("0");
		function f2 =  new Polynom("x^4");
		ComplexFunction actual = new ComplexFunction(Operation.None, f1, f2);
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionFunction() {
		function f = new ComplexFunction("mul(2x,x^2)");
		ComplexFunction expected = new ComplexFunction(f);
		function f1 = new Polynom("2x");
		function f2 =  new Polynom("x^2");
		ComplexFunction actual = new ComplexFunction(Operation.Times, f1, f2);
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionStringFunctionFunction() {
		function f1 = new ComplexFunction("mul(2x,x^2)");
		function f2 = new ComplexFunction("max(2x^3,4x)");
		ComplexFunction expected = new ComplexFunction("div",f1,f2);
		ComplexFunction actual = new ComplexFunction("div(mul(2x,x^2),max(2x^3,4x))");
		assertEquals(actual, expected);
	}

	@Test
	void testComplexFunctionString() {
		String [] goodStrings = {"(2x,x^2)", "(,)", "mul(0x^2+2x,0)", "mul(div(2x,3),(4x,x^2))", ""};
		ComplexFunction [] expected = {new ComplexFunction("2x"), new ComplexFunction("0"), new ComplexFunction("mul(2x,0)"), 
									new ComplexFunction("mul(div(2x,3),4x)"), new ComplexFunction("(,3x)")};
		for(int i=0; i<expected.length; i++) {
			ComplexFunction actual = new ComplexFunction(goodStrings[i]);
			assertEquals(actual, expected[i]);
		}
		
		String [] badString = {"x^2--3x", "mul(x^2,)", "max(min((2x+1,4x),5x,6x)", "2x,4x", "min((2x+1,4x)5x)"};
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
		function actual = cf.initFromString("max(min((,0),0x^3),div(plus(2x^5+2,0x),25))");
		function expected = new ComplexFunction("max(min((,0),0x^3),div(plus(2x^5+2,0x),25))");
		assertEquals(actual, expected);
	}

	@Test
	void testLeft() {
		ComplexFunction cf = new ComplexFunction("min(mul(2x,x^2),(5x^3-42,x^3))");
		function expected = cf.left();
		function actual = new ComplexFunction("mul(2x,x^2)");
		assertEquals(actual, expected);
	}

	@Test
	void testRight() {
		ComplexFunction cf = new ComplexFunction("min(mul(2x,x^2),None(5x^3-42,x^3))");
		function expected = cf.right();
		function actual = new ComplexFunction("5x^3-42");
		assertEquals(actual, expected);
	}

	@Test
	void testGetOp() {
		ComplexFunction cf = new ComplexFunction("min(mul(2x,x^2),None(5x^3-42,x^3))");
		Operation expected = cf.getOp();
		Operation actual = Operation.Min;
		assertEquals(actual, expected);
	}

	@Test
	void testToString() {
		ComplexFunction [] Cfs = {new ComplexFunction("None(,)"), new ComplexFunction("-x^3+5x^2+2"),
								new ComplexFunction("div(Times(2x,x^2),max(2x^3,4x))"),
								new ComplexFunction("None(div(mul(2x,x^2),3x),2x+5)"),
								new ComplexFunction("max(min(None(,0),0x^3),div(Plus(2x^5+2,0x),25))")};
		String [] expected = {"0", "-x^3+5.0x^2+2.0", "div(mul(2.0x, x^2), max(2.0x^3, 4.0x))",
							"div(mul(2.0x, x^2), 3.0x)", "max(min(0, 0), div(plus(2.0x^5+2.0, 0), 25.0))"};
		for(int i=0; i<expected.length; i++) {
			String actual = Cfs[i].toString();
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testCopy() {
		ComplexFunction cf = new ComplexFunction("max(min((,0),0x^3),div(plus(2x^5+2,0x),25))");
		function actual = cf.copy();
		function expected = new ComplexFunction("max(min(None(,0),0x^3),div(plus(2x^5+2,0x),25))");
		assertEquals(actual, expected);
	}

	@Test
	void testPlus() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.plus(f);
		ComplexFunction actual = new ComplexFunction("plus(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMul() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.mul(f);
		ComplexFunction actual = new ComplexFunction("mul(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testDiv() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.div(f);
		ComplexFunction actual = new ComplexFunction("div(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMax() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.max(f);
		ComplexFunction actual = new ComplexFunction("max(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testMin() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.min(f);
		ComplexFunction actual = new ComplexFunction("min(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testComp() {
		ComplexFunction expected = new ComplexFunction("min(mul(2x,x^2),-4x)");
		function f = new Polynom("2x^3");
		expected.comp(f);
		ComplexFunction actual = new ComplexFunction("comp(min(mul(2x,x^2),-4x),2x^3)");
		assertEquals(actual, expected);
	}

	@Test
	void testF() {
		ComplexFunction [] Cfs = {new ComplexFunction("None(,)"), new ComplexFunction("-x^3+5x^2+2"),
								new ComplexFunction("div(mul(2x,x^2),max(2x^3,4x))"),
								new ComplexFunction("None(div(mul(2x,x^2),4x),2x+5)"),
								new ComplexFunction("max(min(None(,0),0x^3),div(plus(2x^5+2,0x),11))"),
								new ComplexFunction("comp(div(plus(2x^5+2,0x),11),min(plus(3.5,2x),3.5))")};
		double [] expected = {0.0, 14.0, 1.0, 2.0, 6.0, 95.67613636363636};
		for(int i=0; i<expected.length; i++) {
			double actual = Cfs[i].f(2);
			assertEquals(actual, expected[i]);
		}
	}

	@Test
	void testEquals() {
		ComplexFunction cf = new ComplexFunction("plus(div(mul(2x,x^2),-4x),2x^3)");
		Polynom p = new Polynom("-0.5x^2+2x^3");
		String s = "-0.5x^2+2x^3";
		boolean isTrue1 = cf.equals(p);
		boolean isTrue2 = cf.equals(s);
		assertTrue(isTrue1);
		assertTrue(isTrue2);
	}
}