package Ex1;

import java.util.Comparator;

/**
 * The Monom class represents a simple monom of shape ax^b, where a is a real number
 * and b is an integer (summed a none negative), see: https://en.wikipedia.org/wiki/Monomial.
 * We defined monom so that there can be spaces everywhere (e,g: 2 x ^ 3),
 * as long as the main structure we described is preserved. Writing capital 'X' instead of 'x' will cause an exception.
 * 
 *There are some special monoms:
 *Real number followed by x - the power of the monom is 1.
 *Real number only - the power of the monom is 0.
 *
 *The class implements function and support simple operations as:
 * construction, value at x, derivative, add and multiply.
 * 
 * @author Meir Nizri and Avihay Bernholtz
 */
public class Monom implements function {
	/**   *   */
	private static final long serialVersionUID = 1L;
	private double _coefficient;	
	private int _power;
	
	/** The Constant ZERO. */
	public static final Monom ZERO = new Monom(0, 0);
	
	/** The Constant MINUS1. */
	public static final Monom MINUS1 = new Monom(-1, 0);
	
	/** The Constant EPSILON. */
	public static final double EPSILON = 0.0000001;
	
	/** The Constant _Comp. */
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	/**
	 * Gets the comperator.
	 * @return the comperator
	 */
	public static Comparator<Monom> getComp() {
		return _Comp;
	}
	
	/**
	 * Constructs and initializes the ZERO Monom
	 */
	public Monom() {
		this.set_coefficient(0);
		this.set_power(0);
	}

	/**
	 * Constructs and initializes a monom. a - coefficient, b - power.
	 * @param a the coefficient
	 * @param b the power
	 */
	public Monom(double a, int b) {
		this.set_coefficient(a);
		this.set_power(b);
	}

	/**
	 * Constructs a copy of the monom.
	 * @param ot the monom to copy
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	/**
	 * Gets the coefficient.
	 * @return the coefficient
	 */
	public double get_coefficient() {
		return this._coefficient;
	}
	
	/**
	 * Gets the power.
	 * @return the power
	 */
	public int get_power() {
		return this._power;
	}

	/**
	 * this method returns the derivative of the monom.
	 * @return the derivative of the monom
	 */
	public Monom derivative() {
		if (this.get_power() == 0)
			return getNewZeroMonom();
		return new Monom(this.get_coefficient() * this.get_power(), this.get_power() - 1);
	}

	/**
	 * This method calculate the value of the monom for given x.
	 * @param x double
	 * @return the value of the monom for x
	 */
	public double f(double x) {
		double ans = 0;
		double p = this.get_power();
		ans = this.get_coefficient() * Math.pow(x, p);
		return ans;
	}

	/**
	 * Checks if is zero.
	 * @return true, if is zero
	 */
	public boolean isZero() {
		return this.get_coefficient() == 0;
	}

	// ***************** add your code below **********************
	
	/**
	 * Constructs and initializes a monom from String.
	 * @param s String that represent monom
	 */
	public Monom(String s) {
		//Remove all white-spaces from s
		s = s.replaceAll("\\s", "");
		String coefStr = "", powStr = "";
		
		//if s is empty String - return the zero monom
		if (s.length() == 0) {
			coefStr = "0";
			powStr = "0";
		}
		
		// take only the coefficient part
		else if (s.contains("x")) {
			int indexOfX = s.indexOf('x');
			String sub = s.substring(0, indexOfX);
			if (s.charAt(0) == 'x')
				coefStr = "1";
			else if (sub.equals("-"))
				coefStr = "-1";
			else
				coefStr = sub;
			
			// Takes only the power part
			int indexOfCaret = indexOfX + 1;
			if (s.length()>indexOfCaret && s.charAt(indexOfCaret)=='^')
				powStr = s.substring(indexOfCaret + 1, s.length());
			else if (s.length() == (indexOfX+1))
				powStr = "1";
			else
				powStr = "Error";
		}

		// The String is just number
		else {
			coefStr = s;
			powStr = "0";
		}
		// Turn coefStr to double and powStr to integer;
		try {
			this.set_coefficient(Double.parseDouble(coefStr));
			if(this.get_coefficient()==0)
				this.set_power(0);
			else
				this.set_power(Integer.parseInt(powStr));
		}
		catch (NumberFormatException e){
            throw new NumberFormatException("ERR: got wrong format String for Monom");
		}
	}

	/**
	 * Add monom to this monom.
	 * @param m Monom
	 */
	public void add(Monom m) {
		if (this.get_power() == m.get_power())
			this.set_coefficient(this.get_coefficient() + m.get_coefficient());
		else
			throw new RuntimeException("you cant add two monoms with diffrent power");
	}

	/**
	 * Multiply monom to this monom.
	 * @param d Monom
	 */
	public void multiply(Monom d) {
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
		this.set_power(this.get_power() + d.get_power());
	}

	/**
	 * Print the monom to the screen, in format double_x^_int.
	 * @return string representation of the monom
	 */
	public String toString() {
		String ans = "";
		// if the coefficient is 0 - print 0 no matter what is the power
		if (this.get_coefficient() == 0)
			ans = "0";
		// if the power is zero - print the coefficient only
		else if (this.get_power() == 0)
			ans = this.get_coefficient() + "";
		// if the power is 1 - print x or -x or ax
		else if (this.get_power() == 1) {
			if (this.get_coefficient() == 1)
				ans = "x";
			else if (this.get_coefficient() == -1)
				ans = "-x";
			else
				ans = this.get_coefficient() + "x";
		}

		else { // if the power>1 and print ax^b
			if (this.get_coefficient() == 1)
				ans = "x^" + this.get_power();
			else if (this.get_coefficient() == -1)
				ans = "-x^" + this.get_power();
			else
				ans = this.get_coefficient() + "x^" + this.get_power();
		}
		return ans;
	}

	/**
	 * check if equals.
	 * @param d the d
	 * @return true, if successful
	 */
	public boolean equals(Object obj) {
		try {
			Monom d = new Monom(obj.toString());
			
			//If the coefficients in the range of Epsilon - return true
			boolean eqCoef = ((Math.abs(this.get_coefficient() - d.get_coefficient())) < EPSILON);
			boolean eqPow = (this.get_power() == d.get_power());
			//If one of the coefficients equals 0 and the other in epsilon range from it - return true
			if (this.get_coefficient() == 0 || d.get_coefficient()==0) 
				return (eqCoef);
			else
				return (eqCoef && eqPow);
		}
		catch (NumberFormatException e){
			return false;
		}
	}

	@Override
	public function initFromString(String s) {
		function f = new Monom(s);
		return f;
	}

	@Override
	public function copy() {
		function f = new Monom(this.toString());
		return f;
	}

	// ****************** Private Methods and Data *****************

	/**
	 * Sets the coefficient.
	 * @param a the new coefficient
	 */
	private void set_coefficient(double a) {
		this._coefficient = a;
	}

	/**
	 * Sets the power.
	 * @param p the new power
	 */
	private void set_power(int p) {
		if (p < 0)
			throw new RuntimeException("ERR the power of Monom should not be negative, got: " + p);
		this._power = p;
	}

	/**
	 * Gets the new zero monom.
	 * @return the new zero monom
	 */
	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}
}