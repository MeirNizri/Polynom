package myMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This  class represents a general Polynom: f(x) = a_1X^b_1 + a_2*X^b_2 +...+ a_n*Xb_n,
 * where: a_1, a_2 ... a_n are real numbers and b_1, b_2 ... b_n are integer (summed a none negative).
 * all the elements in the polynom are standard monoms as we described in the monom class.
 * We defined Polynom so that there can be spaces everywhere (e,g: 2x^ 3  –  5 x), 
 * as long as the main structure we described is preserved. 
 * This Polynom can get different monoms with equal powers, The constructor will add all monoms
 *  of the same power and order all the elements from highest power to the lowest.

 * Polynom implements function and support simple operations as: construction, value at x,
 * add, subtract, multiply functionality, it also should support the following: 
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral 
 * 2. Finding a numerical value between two values (currently support root only f(x)=0). 
 * 3. Derivative
 * 
 * @author Meir Nizri and Avihay Bernholtz
 */
public class Polynom implements Polynom_able {

	/** 
	 * The monoms in the Polynom
	 */
	private ArrayList<Monom> monoms = new ArrayList<Monom>();

	/**
	 * Constructs the zero polynom.
	 */
	public Polynom() {
		monoms.add(new Monom(0,0));
	}

	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s String that represent Polynom.
	 */
	public Polynom(String s) {
		//the String is empty
		if (s.length() == 0)
			monoms.add(Monom.ZERO);
		//traverse all the string until he sees '+' or '-', and than make monom
		int start = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '+' || (s.charAt(i) == '-' && i > 0)) {
				monoms.add(new Monom(s.substring(start, i)));
				// check how to promote start
				if (s.charAt(i) == '+')
					start = i + 1;
				else if (s.charAt(i) == '-')
					start = i;
			}
			// the last monom
			else if (i == s.length() - 1)
				monoms.add(new Monom(s.substring(start, i + 1)));
		}
		this.sortAndSumEqualDegree();
	}

	/**
	 * Sort the polynom and sum monoms of equal degree.
	 */
	public void sortAndSumEqualDegree() {
		Collections.sort(this.monoms, new Monom_Comperator());
		for (int i = 1; i < monoms.size(); i++) {
			// check every two neighboring monom if they have same power
			Monom elem = monoms.get(i - 1);
			Monom nextElem = monoms.get(i);
			if (elem.get_power() == nextElem.get_power()) {
				elem.add(nextElem);
				monoms.remove(i);
				i--;
				// if after we sum the two monoms we get the zero monom - remove it
				if (elem.isZero() && monoms.size() > 1)
					monoms.remove(i);
			}
		}
		// check if the last monom is zero, if so - remove it
		Monom lastElem = new Monom(monoms.get(monoms.size() - 1));
		if (lastElem.isZero() && monoms.size() > 1)
			monoms.remove(monoms.size() - 1);
	}

	/**
	 * find the iterator of the polynom, which is a pointer to the start of the polynom
	 * @return an iterator of this Polynom
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return monoms.iterator();
	}

	/**
	 * print the polynom 
	 * @return string representation of the polynom
	 */
	@Override
	public String toString() {
		String ans = "";
		ans += monoms.get(0).toString();
		for (int i = 1; i < monoms.size(); i++) {
			Monom m = new Monom(monoms.get(i));
			if (m.get_coefficient() >= 0)
				ans += "+" + m.toString();
			else if (m.get_coefficient() < 0)
				ans += m.toString();
		}
		return ans;
	}

	/**
	 * Test if this is the Zero Polynom.
	 * @return true iff this polynom is the zero polynom, false otherwise
	 */
	@Override
	public boolean isZero() {
		return this.monoms.get(0).isZero();
	}

	/**
	 * create a deep copy of this Polynom.
	 * @return a copy of the polynom and not pointer to it
	 */
	@Override
	public Polynom_able copy() {
		Polynom_able p = new Polynom(this.toString());
		return p;
	}

	/**
	 * Test if this Polynom is logically equals to p1.
	 * @param p1 Polynom
	 * @return true iff this polynom represents the same function as p1
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		// traverse p1 and this Polynom with iterator equally
		Iterator<Monom> itr1 = p1.iteretor();
		Iterator<Monom> itr2 = this.iteretor();
		while (itr1.hasNext() && itr2.hasNext()) {
			Monom elem1 = itr1.next();
			Monom elem2 = itr2.next();
			if (!elem1.equals(elem2))
				return false;
		}
		//one Polynom is longer from the other
		if(itr1.hasNext() || itr2.hasNext())
				return false;
		return true;
	}

	/**
	 * Add m1 to this Polynom.
	 * @param m1 Monom
	 */
	@Override
	public void add(Monom m1) {
		boolean found = false;
		Iterator<Monom> itr = this.iteretor();
		while (itr.hasNext()) {
			Monom elem = itr.next();
			// if found monom with the same power of m1 than add them
			if (elem.get_power() == m1.get_power()) {
				found = true;
				elem.add(m1);
				if (elem.isZero() && monoms.size() > 1)
					itr.remove();
				break;
			}
		}
		if (!found) {
			monoms.add(m1);
			this.sortAndSumEqualDegree();
		}
	}

	/**
	 * Multiply this Polynom by Monom m1.
	 * @param m1 Monom
	 */
	@Override
	public void multiply(Monom m1) {
		if (m1.isZero()) {
			Polynom p = new Polynom();
			this.monoms = p.monoms;
		}
		Iterator<Monom> itr = monoms.iterator();
		while (itr.hasNext()) {
			Monom elem = itr.next();
			elem.multiply(m1);
		}
	}

	/**
	 * Add p1 to this Polynom.
	 * @param p1 Polynom
	 */
	@Override
	public void add(Polynom_able p1) {
		if (!(p1 instanceof Polynom))
			throw new IllegalArgumentException("The argument you entered is not instance of Polynom");
		//take every monom in p1 and add him to this polynom
		Iterator<Monom> itr = p1.iteretor();
		while (itr.hasNext()) {
			Monom elem = itr.next();
			this.add(elem);
		}
	}

	/**
	 * Subtract p1 from this Polynom.
	 * @param p1 Polynom
	 */
	@Override
	public void substract(Polynom_able p1) {
		if (!(p1 instanceof Polynom))
			throw new IllegalArgumentException("The argument you entered is not instance of Polynom");
		if(this.equals(p1)) 
			this.multiply(Monom.ZERO);
		else {
			p1.multiply(Monom.MINUS1);
			this.add(p1);
		}
	}

	/**
	 * Multiply this Polynom by p1.
	 * @param p1 Polynom
	 */
	@Override
	public void multiply(Polynom_able p1) {
		if (!(p1 instanceof Polynom))
			throw new IllegalArgumentException("The argument you entered is not instance of Polynom");
		Polynom p2 = new Polynom();
		Iterator<Monom> itr = p1.iteretor();
		while (itr.hasNext()) {
			Monom elem = itr.next();
			//in every iteration p3 equal to this.
			//multiply p3 in every monom in p1, and sum all the results in p2
			Polynom_able p3 = this.copy();
			p3.multiply(elem);
			p2.add(p3);
		}
		//this = p2
		this.multiply(Monom.ZERO);
		this.add(p2);
	}

	/**
	 * Compute a new Polynom which is the derivative of this Polynom.
	 * @return derivative of the polynom
	 */
	@Override
	public Polynom_able derivative() {
		Polynom_able p1 = new Polynom();
		Iterator<Monom> itr = this.iteretor();
		while (itr.hasNext()) {
			Monom elem = itr.next();
			p1.add(elem.derivative());
		}
		return p1;
	}

	/**
	 * calculate the value of the polynom when inserted value of x
	 * @param x double
	 * @return the value of the polynom
	 */
	@Override
	public double f(double x) {
		double ans = 0;
		Iterator<Monom> itr = this.iteretor();
		while (itr.hasNext()) {
			Monom element = itr.next();
			ans += element.f(x);
		}
		return ans;
	}

	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, else should throws runtimeException 
	 * computes f(x') such that:
	 * 	(i) x0<=x'<=x1 && 
	 * 	(ii) |f(x')|<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps>0 (positive) representing the epsilon range the solution should be within.
	 * @return an approximated value (root) for this (cont.) function 
	 */
	@Override 
	public double root(double x0, double x1, double eps) {
		if(x0 > x1)
			throw new IllegalArgumentException("you entered x0>x1, should be x0<x1");
		if (this.f(x0)*this.f(x1) > 0)
			throw new RuntimeException("The range (x0,x1) you entered doesn't surely contain root of the polynom");
		// binary search for f(mid) <= eps
		double mid = x0;
		while((x1-x0) >= eps) {
			mid = (x1+x0)/2;
			if (Math.abs(this.f(mid)) < eps)
				break;
			else if(this.f(mid)*this.f(x0) < 0)
				x1 = mid; 
			else
				x0 = mid;
		}
		return mid;
	}

	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps. 
	 * @param x0 starting pooint
	 * @param x1 end point
	 * @param eps positive step value
	 * @return the approximated area above X-axis below this function bounded in the range of [x0,x1]
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double ans = 0.0, value = 0.0;
		for (double i = x0; i < x1; i += eps) {
			//sum all the rectangles from the x axis to f(i) when f(i)>0
			value = this.f(i);
			if (value > 0)
				ans += value*eps;
		}
		return ans;
	}
}