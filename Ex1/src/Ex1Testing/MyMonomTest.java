package Ex1Testing;
import java.util.ArrayList;
import Ex1.*;
/**
 * This class represents a simple (naive) tester for the Monom class, 
 * Note: <br>
 * (i) The class is NOT a JUNIT - (i.e., educational reasons) - should be changed to a proper JUnit in Ex1. <br>
 * (ii) This tester should be extend in order to test ALL the methods and functionality of the Monom class.  <br>
 * (iii) Expected output:  <br>
 * *****  Test1:  *****  <br>
0) 2.0    	isZero: false	 f(0) = 2.0  <br>
1) -1.0x    	isZero: false	 f(1) = -1.0  <br>
2) -3.2x^2    	isZero: false	 f(2) = -12.8  <br>
3) 0    	isZero: true	 f(3) = 0.0  <br>
 *****  Test2:  *****  <br>
0) 0    	isZero: true  	eq: true  <br>
1) -1.0    	isZero: false  	eq: true  <br>
2) -1.3x    	isZero: false  	eq: true  <br>
3) -2.2x^2    	isZero: false  	eq: true  <br>
 */
public class MyMonomTest {
	
	public static void main(String[] args) {
		monomContructorTest();
		monomStringConstructorTest();
		monomFuncTest();
		monomAddTest();
		monomMultiTest();
		monomDerivativeTest();
		monomToStringTest();
		monomEqualsTest();
		test1();
		test2();
	}
	private static void monomContructorTest() {

		//default constructor
		Monom example1 = new Monom();
		if (example1.get_coefficient() != 0 || example1.get_power() != 0)
			System.out.println("error: default constructor");

		//constructor
		Monom example2 = new Monom(2, 5);
		Monom example3 = new Monom((1.0 / 4), 2);

		if (example2.get_coefficient() != 2 || example2.get_power() != 5 ||
				example3.get_coefficient() != 0.25 || example3.get_power() != 2)
			System.out.println("error: constructor");

		boolean flag = false;
		try {                                         //test wither the constractor throwes exception
			@SuppressWarnings("unused")
			Monom example4 = new Monom(1, -2);    //when power values are negative.
		} catch (RuntimeException e) {
			flag = true;
		}

		if (!flag)
			System.out.println("error: constructor (exception)");

		//copy constructor
		Monom example7 = new Monom(example1);
		Monom example8 = new Monom(example2);
		Monom example9 = new Monom(example3);

		if (example7.get_coefficient() != 0 || example7.get_power() != 0 ||
				example8.get_coefficient() != 2 || example8.get_power() != 5 ||
				example9.get_coefficient() != 0.25 || example9.get_power() != 2)
			System.out.println("error: copy constructor");
	}

	private static void monomFuncTest() {
		//test if the calculation is right for random power and coefficient.
		Monom example1 = new Monom(4.0 / 5, 4);
		//test if the calculation for the power of 0 is right.
		Monom example2 = new Monom(4.0 / 5, 0);
		if (example1.f(2) != 12.8 || example2.f(2) != 0.8)
			System.out.println("error: function f calculation ");
	}

	private static void monomAddTest () {
		Monom example1 = new Monom(1, 3);
		Monom example2 = new Monom(2, 3);
		Monom example3 = new Monom(3, 0);
		Monom example4 = new Monom(0.25, 7);

		boolean flag = false;
		try {
			example1.add(example4);
		} catch (RuntimeException e) {
			flag = true;
		}

		example1.add(example2);
		example3.add(example3);

		if (!flag
				|| example1.get_power() != 3
				|| example1.get_coefficient() != 3
				|| example3.get_coefficient() != 6
				|| example3.get_power() != 0)
			System.out.println("error: Monom func add");
	}

	@SuppressWarnings("unused")
	private static void monomStringConstructorTest () {
		String [] good = {"","7 x","-x","4.78","3.9x^71"};
		String [] bad = {"%$","2^3", "X","xX","(4","x32","--3X"};

		for(int i=0; i<good.length; i++) {
			String c = good[i];
			Monom m = new Monom (c);
		}
		for(int i=0; i<bad.length; i++) {
			String c = bad[i];
			boolean ok = true;
			try {
				Monom m = new Monom (c);
			}
			catch (Exception e) {
				ok = false;
			}
			if (ok) {
				System.out.println("error: bad String " + c + " was excepted");
			}
		}
	}

	public static void monomMultiTest() {
		Monom m0 = new Monom ();
		Monom m1 = new Monom (2,2);
		Monom m2 = new Monom (3,2);
		Monom m3 = new Monom (5,3);

		m0.multiply(m1);
		m1.multiply(m2);
		m2.multiply(m3);

		if (m0.get_coefficient() != 0 ||
				m1.get_coefficient() != 6 || m1.get_power() != 4 ||
				m2.get_coefficient() != 15 || m2.get_power() != 5)
			System.out.println("error: monom func multiply");

	}

	public static void monomDerivativeTest() {
		Monom m0 = new Monom ();
		Monom m1 = new Monom (2,1);
		Monom m2 = new Monom (3,2);
		Monom m3 = new Monom (5,3);

		Monom d0 = m0.derivative();
		Monom d1 = m1.derivative();
		Monom d2 = m2.derivative();
		Monom d3 = m3.derivative();

		if(d0.get_coefficient() != 0 || d0.get_power() != 0
				|| d1.get_coefficient() != 2 || d1.get_power() != 0
				|| d2.get_coefficient() != 6 || d2.get_power() != 1
				|| d3.get_coefficient() != 15 || d3.get_power() != 2) {
			System.out.println("error: monom func derivative");
		}

	}

	public static void monomToStringTest() {
		Monom m0 = new Monom ();
		Monom m1 = new Monom (0,1);
		Monom m2 = new Monom (5,0);
		Monom m3 = new Monom (1,2);
		Monom m4 = new Monom (5,1);
		Monom m5 = new Monom (5,3);

		String a = m0.toString();
		String b = m1.toString();
		String c = m2.toString();
		String d = m3.toString();
		String e = m4.toString();
		String f = m5.toString();

		if(!a.equals("0")
				|| !b.equals("0")
				|| !c.equals("5.0")
				|| !d.equals("x^2")
				|| !e.equals("5.0x")
				|| !f.equals("5.0x^3")) {
			System.out.println("error: monom func toString");
		}
	}

	private static void monomEqualsTest() {
		Monom m0 = new Monom("3x^2");
		Monom m1 = new Monom("3x^2");
		Monom m2 = new Monom("5");

		if(!(m0.equals(m1) || m0.equals(m2)))
			System.out.println("error: Monom equals func ");
	}

	private static void test1() {
		System.out.println("*****  Test1:  *****");
		String[] monoms = {"2", "-x","-3.2x^2","0"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			String s = m.toString();
			m = new Monom(s);
			double fx = m.f(i);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"\t f("+i+") = "+fx);
		}
	}

	private static void test2() {
		System.out.println("*****  Test2:  *****");
		ArrayList<Monom> monoms = new ArrayList<Monom>();
		monoms.add(new Monom(0,5));
		monoms.add(new Monom(-1,0));
		monoms.add(new Monom(-1.3,1));
		monoms.add(new Monom(-2.2,2));

		for(int i=0;i<monoms.size();i++) {
			Monom m = new Monom(monoms.get(i));
			String s = m.toString();
			Monom m1 = new Monom(s);
			boolean e = m.equals(m1);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"  \teq: "+e);
		}
	}
}
