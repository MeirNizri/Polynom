package Ex1Testing;

import java.util.Iterator;
import Ex1.*;


public class MyPolynomTest {

	public static void main (String[]args){
		polyConstructorTest();
		polyStringConstructorTest();
		polyAddMonomTest();
		polyAddPolyTest();
		polySubstractTest();
		polyMultiplyTest();
		polyEqualsTest();
		polyIsZeroTest();
		polyRootTest();
		polyCopyTest();
		polyDerivativeTest();
		polyFuncTest();
		polyToStringTest();
		polyAreaTest();
	}
	public static void polyConstructorTest(){
		Polynom p1 = new Polynom();
		Iterator<Monom> itr = p1.iteretor();
		itr.next();
		if (itr.hasNext())
			System.out.println("error: default constructor");

		Polynom p2 = new Polynom();
		Monom m1 = new Monom(3,5);
		Monom m2 = new Monom(2,4);
		p2.add(m1);
		p2.add(m2);

		Polynom p2Copy = new Polynom(p2.toString());
		if(!p2.equals(p2Copy))
			System.out.println("error: copy constructor");
	}

	@SuppressWarnings("unused")
	private static void polyStringConstructorTest () {
		String [] good = {"3-7+5-2",
				"x^3 + 5x^2 + x + 4",
				"-x^3 + 5x^2 +2",
				"-x^2-5-x^2-x^3-7" ,
				"0x^5+3",
				"3x^0-5x", "" };
		String [] bad = {"(-x^2+(3x^5))","x^2--3x"};
		for(int i=0; i<good.length; i++) {
			String c = good[i];
			Polynom m1 = new Polynom (c);
		}
		for(int i=0; i<bad.length; i++) {
			String c = bad[i];
			boolean ok = true;
			try {
				Polynom m2 = new Polynom (c);
			}
			catch (Exception e) {
				ok = false;
			}
			if (ok) {
				System.out.println("error: bad String " + c + " was excepted");
			}
		}
	}

	private static void polyAddMonomTest(){
		Polynom p = new Polynom("5x^3 + 4x^2 - 3x + 2");
		Monom m0 = new Monom ();
		Monom m1 = new Monom (6,4);
		Monom m2 = new Monom (1,2);
		Monom m3 = new Monom (3,1);
		Monom m4 = new Monom (-2,0);

		p.add(m0);
		p.add(m1);
		p.add(m2);
		p.add(m3);
		p.add(m4);

		Polynom target = new Polynom("6x^4 + 5x^3 + 5x^2");
		if(!p.equals(target))
			System.out.println("error: polynom Add Monom func ");
	}

	private static void polyAddPolyTest() {
		Polynom p0 = new Polynom("5x^3 + 4x^2 - 3x + 2");
		Polynom p1 = new Polynom("6x^4 + 2x - 2");
		Polynom target = new Polynom("6x^4 + 5x^3 + 4x^2 - x");

		p0.add(p1);

		if(!p0.equals(target))
			System.out.println("error: polynom Add Polynom func ");
	}

	private static void polySubstractTest() {
		Polynom p0 = new Polynom("5x^3 + 4x^2 - 3x + 2");
		Polynom p1 = new Polynom("-6x^4 - 2x + 2");
		Polynom target = new Polynom("6x^4 + 5x^3 + 4x^2 - x");

		p0.substract(p1);

		if(!p0.equals(target))
			System.out.println("error: polynom substract func ");
	}

	private static void polyMultiplyTest() {
		Polynom p0 = new Polynom("3x^2 + x");
		Polynom p1 = new Polynom("4x + 2");
		Polynom target = new Polynom("12x^3 + 10x^2 + 2x");

		p0.multiply(p1);

		if(!p0.equals(target))
			System.out.println("error: polynom multiply func ");
	}

	private static void polyEqualsTest() {
		Polynom p0 = new Polynom("3x^2 + x");
		Polynom p1 = new Polynom("3x^2 + x");
		Polynom p2 = new Polynom("3x^3 + 1");

		if(!p0.equals(p1) || p0.equals(p2))
			System.out.println("error: polynom equals func ");
	}

	private static void polyIsZeroTest() {
		Polynom p0 = new Polynom();
		Polynom p1 = new Polynom("3x^2 + x");

		if (!p0.isZero() || p1.isZero())
			System.out.println("error: polynom isZero func ");
	}

	@SuppressWarnings("unused")
	private static void polyRootTest() {
		boolean exceptionThrown = false;

		//check if smaller epsilon gives more precise answer
		Polynom p0 = new Polynom("x"); // cuts x axis at: 0
		double result1 = p0.root(-0.5 ,2 , 0.5);
		double result2 = p0.root(-0.5 ,2 , 0.25);
		double result3 = p0.root(-0.5 ,2 , 0.01);
		if (Math.abs(result1)<Math.abs(result2) || Math.abs(result2)<Math.abs(result3))
			System.out.println("error: polynom root func ");

		//check how root handles different cases
		Polynom p1 = new Polynom("x^3 - x"); // cuts x axis at: -1,0,1. see: https://www.desmos.com/calculator/9fwg0quutq
		final double eps = 0.01;

		//one cut
		double cut1 = p1.root(-0.5 , 0.75 , eps);

		//3 cuts
		double cut2 = p1.root(-1.5 , 2 , eps);

		//x0 || x1 are cuts
		double cut3 = p1.root(0 , 1.5 , eps);
		double cut4 = p1.root(-0.5 , 1 , eps);


		try {
			// both x0 && x1 are above x axis or below it
			double cut5 = p1.root(-1.5, 0.5, eps);
			double cut6 = p1.root(-0.75, -0.25, eps);

			//no cut
			double cut7 = p1.root(1.5 , 2 , eps);
			double cut8 = p1.root(-2, -1.5 , eps);

			//x1<x0
			double cut9 = p1.root(1.5, 0.5 , eps);
		}
		catch (RuntimeException e){
			exceptionThrown = true;
		}

		if(!exceptionThrown)
			System.out.println("error: polynom root func ");
	}

	private static void polyCopyTest() {
		Polynom p0 = new Polynom("3x^2 + x");
		Polynom_able p1 =p0.copy();

		if (!p1.equals(p0))
			System.out.println("error: polynom copy func ");
	}

	private static void polyDerivativeTest() {
		Polynom p = new Polynom("3.5x^3 - 2.3x^2 + 15x - 32");
		Polynom_able target = new Polynom("10.5x^2 - 4.6x + 15");
		Polynom_able der = p.derivative();

		if (!der.equals(target))
			System.out.println("error: polynom derivative func ");
	}

	private static void polyFuncTest() {
		Polynom p = new Polynom("3.5x^3 - 2.3x^2 + 15x - 32");
		double result1 = p.f(-2);
		double result2 = p.f(0);
		double result3 = p.f(1);
		double result4 = p.f(2.5);

		double target1 = -99.2;
		double target2 = -32;
		double target3 = -15.8;
		double target4 = 45.8125;

		if (result1 != target1
				||result2 != target2
				||result3 != target3
				||result4 != target4)
			System.out.println("error: polynom f func ");
	}

	private static void polyToStringTest() {
		Polynom p = new Polynom();
		Polynom p1 = new Polynom("3.5x^3 - 2.3x^2 + 15x - 32");

		if (!p.toString().equals("0")
				|| !p1.toString().equals("3.5x^3-2.3x^2+15.0x-32.0"))
			System.out.println("error: polynom toString func ");
	}

	private static void polyAreaTest() {
		//check if smaller epsilon gives more precise answer
		Polynom p0 = new Polynom("-x^2 + 9");
		double result1 = p0.area(-3 ,3 , 0.5);
		double result2 = p0.area(-3 ,3 , 0.25);
		double result3 = p0.area(-3 ,3 , 0.01);
		final int target1 = 36;

		if (Math.abs(target1-result1)<Math.abs(target1-result2)
				|| Math.abs(target1-result2)<Math.abs(target1-result3))
			System.out.println("error: polynom area func ");

		//check if area calculates only areas above x axis
		Polynom p1 = new Polynom("x^3 - x");	//positive at: -1<x<0 && x>1. negative at: 0<x<1 && x<-1
		final double eps = 0.01;
		double result4 = p1.area(-1 ,1 , eps);
		double result5 = p1.area(-1 ,0 , eps);

		if (result4 != result5)
			System.out.println("error: polynom area func ");
	}
}