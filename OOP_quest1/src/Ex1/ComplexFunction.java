package Ex1;

/** This interface represents a complex function of type y=g(f1(x), f2(x)), where both f1, f2 are functions (or complex functions), 
 * y and x are real numbers and g is an operation: plus, mul, div, max, min, comp (f1(f2(x))).
 **/

public class ComplexFunction implements complex_function {
	function leftFunc;
	function rightFunc;
	Operation op;

	// Default constructor
	public ComplexFunction() {
		leftFunc = new Polynom();
		rightFunc = new Polynom();
		op = Operation.None;
	}

	// Initial a ComplexFunction
	public ComplexFunction(Operation op, function leftFunc, function rightFunc) {
		this.leftFunc = new Polynom();
		this.rightFunc = new Polynom();
		this.op = Operation.None;
	}

	// Initial a CopmplexFunction from String
	public ComplexFunction(String s) {
		if(s.contains("(")) {
			String rightfunc=s.substring(GetComma(s),s.length()-1);
			String operation=s.substring(0,s.indexOf('('));
			String leftfunc=s.substring(s.indexOf('(')+1,GetComma(s));
			this.rightFunc=new Polynom(rightfunc);
			this.leftFunc=new ComplexFunction(leftfunc);
			this.op=OpfromString(operation);
		}
		else {
			this.leftFunc=new Polynom(s);
			this.rightFunc = new Polynom();
			this.op = Operation.None;
		}




	}


	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	/** returns the left side of the complex function - this side should always exists (should NOT be null).
	 * @return a function representing the left side of this complex funcation
	 */
	@Override
	public function left() {
		return this.leftFunc;
	}

	/** returns the right side of the complex function - this side might not exists (aka equals null).
	 * @return a function representing the left side of this complex funcation
	 */
	@Override
	public function right() {
		return this.rightFunc;
	}

	/**
	 * The complex_function oparation: plus, mul, div, max, min, comp
	 * @return
	 */
	@Override
	public Operation getOp() {
		return this.op;
	}

	@Override
	public function copy() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Add to this complex_function the f1 complex_function
	 * @param f1 the complex_function which will be added to this complex_function.
	 */
	@Override
	public void plus(function f1) {
		// TODO Auto-generated method stub

	}
	/** Multiply this complex_function with the f1 complex_function
	 * @param f1 the complex_function which will be multiply be this complex_function.
	 */
	@Override
	public void mul(function f1) {
		// TODO Auto-generated method stub

	}

	/** Divides this complex_function with the f1 complex_function
	 * @param f1 the complex_function which will be divid this complex_function.
	 */
	@Override
	public void div(function f1) {
		// TODO Auto-generated method stub

	}

	/** Computes the maximum over this complex_function and the f1 complex_function 
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the maximum.
	 */
	@Override
	public void max(function f1) {
		// TODO Auto-generated method stub

	}

	/** Computes the minimum over this complex_function and the f1 complex_function 
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the minimum.
	 */
	@Override
	public void min(function f1) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method wrap the f1 complex_function with this function: this.f(f1(x))
	 * @param f1 complex function
	 */
	@Override
	public void comp(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		return 0;
	}
	private int GetComma(String s) {
		char c=' ';
		int comma=s.length();
		do {
			comma=comma-1;
			if(s.charAt(comma)==',') {
				c=',';
			}
		}while(c!=',');
		return comma+1;
	}
	private Operation OpfromString(String s) {
		switch(s) {
		case "plus":
			return Operation.Plus;
		case "mul":
			return Operation.Times;
		case "div":
			return Operation.Divid;
		case "min":
			return Operation.Min;
		case "max":
			return Operation.Max;
		case "comp":
			return Operation.Comp;
		case "":
			return Operation.None;
		default:
			return Operation.Error;
		}
	}
}