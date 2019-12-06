package Ex1;

/** This interface represents a complex function of type y=g(f1(x), f2(x)), where both f1, f2 are functions (or complex functions), 
 * y and x are real numbers and g is an operation: plus, mul, div, max, min, comp (f1(f2(x))).
**/

@SuppressWarnings("serial")
public class ComplexFunction implements complex_function {
	function leftFunc;
	function rightFunc;
	Operation op;
	
	// Default constructor
	public ComplexFunction() {
		this.leftFunc = new Polynom();
		this.rightFunc = null;
		this.op = Operation.None;
	}
	
	// Initial a ComplexFunction
	public ComplexFunction(Operation op, function leftFunc, function rightFunc) {
		this.leftFunc = new Polynom();
		this.rightFunc = new Polynom();
		this.op = op;
	}
	
	// Initial a ComplexFunction from String
	public ComplexFunction(String s) {
		if(s.contains("(")) {
			String opString = s.substring(0, s.indexOf('('));
			String rightFuncString = s.substring(s.lastIndexOf(',')+1, s.length()-1);;
			String leftFuncString = s.substring(s.indexOf('(')+1, s.lastIndexOf(','));;
			//
			this.op = operationFromString(opString);
			this.rightFunc = new Polynom(rightFuncString);
			this.leftFunc = new ComplexFunction(leftFuncString);
		}
		else {
			this.leftFunc = new Polynom(s);
			this.rightFunc = null;
			this.op = Operation.None;
		}
	}
	
	public Operation operationFromString(String s) {
		switch(s) {
			case "Plus":
			    return Operation.Plus;
			case "Times":
			    return Operation.Times;
			case "Divid":
			    return Operation.Divid;
			case "Max":
			    return Operation.Max;
			case "Min":
			    return Operation.Min;
			case "Comp":
			    return Operation.Comp;
			case "None":
			    return Operation.None;
			default:
				return Operation.Error;
		}
	}

	@Override
	public function initFromString(String s) {
		function f = new ComplexFunction(s);
		return f;
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
		function f = new ComplexFunction(this.getOp(), this.left(), this.right());
		return f;
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
}