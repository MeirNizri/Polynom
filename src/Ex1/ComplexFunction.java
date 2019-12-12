package Ex1;

/** This interface represents a complex function of type y=g(f1(x), f2(x)), where both f1, f2 are functions (or complex functions), 
 * y and x are real numbers and g is an operation: plus, mul, div, max, min, comp (f1(f2(x))).
**/
@SuppressWarnings("serial")
public class ComplexFunction implements complex_function {
	
	/** The left function */
	private function leftFunc;
	/** The right function */
	private function rightFunc;
	/** The operation */
	private Operation op;
	
	/**
	 * Default constructor
	 */
	public ComplexFunction() {
		this.leftFunc = new Polynom();
		this.rightFunc = null;
		this.op = Operation.None;
	}
	
	/**
	 * Initial a complexFunction similiar to Polynom
	 * @param f the left function
	 */
	public ComplexFunction(function f) {
		this.leftFunc = f;
		this.rightFunc = null;
		this.op = Operation.None;
	}
	
	/**
	 * Initialize a complexFunction
	 * @param f1 the left function
	 * @param f2 the right function
	 * @param operation
	 */
	public ComplexFunction(Operation operation, function f1, function f2) {
		this.leftFunc = f1;
		this.rightFunc = f2;
		this.op = operation;
		// Ensures that if the right function is null then the operation must be 'None'
		if(rightFunc==null && op!=Operation.None)
			throw new IllegalArgumentException("The operation can't be different than 'None' when the right function is null");
	}
	
	/**
	 * Initialize a complexFunction
	 * @param f1 the left function
	 * @param f2 the right function
	 * @param operation
	 */
	public ComplexFunction(String operation, function f1, function f2) {
		this.leftFunc = f1;
		this.rightFunc = f2;
		operation.toLowerCase();
		this.op = operationFromString(operation);
		// Ensures that if the right function is null then the operation must be 'None'
		if(rightFunc==null && op!=Operation.None)
			throw new IllegalArgumentException("The operation can't be different than 'None' when the right function is null");
	}
	
	/**
	 * Initialize a complexFunction from a String such as: {"Plus(x,x+1)", "Plus(Divid(x,x+1), Times(1,null))", "(3,2x)"}.
	 * @param s String that represent complexFunction.
	 */
	public ComplexFunction(String s) {
		// Remove spaces and check if the parenthesis are balanced
		s = s.replaceAll("\\s", "");
		s = s.toLowerCase();
		if (!isValidString(s))
			throw new IllegalArgumentException("The parenthesis in the String you entered is not valid");
		// Find the appropriate String for every class variable
		int firstOpenBracket = s.indexOf('(');
		if(firstOpenBracket != -1) {
			int indexOfComa = getComaIndex(s);
			String opString = s.substring(0, firstOpenBracket);
			String leftFuncString = s.substring(firstOpenBracket+1, indexOfComa);
			String rightFuncString = s.substring(indexOfComa+1, s.length()-1);
			
			// Converts the string to class variables
			this.op = operationFromString(opString);        //operation
			if(leftFuncString.contains("("))                //left function
				this.leftFunc = new ComplexFunction(leftFuncString);
			else
				this.leftFunc = new Polynom(leftFuncString);
			if(rightFuncString.contains("("))               //right function
				this.rightFunc = new ComplexFunction(rightFuncString);
			else if(rightFuncString.length() == 0 || rightFuncString.equals("null"))
				this.rightFunc = null;
			else 
				this.rightFunc = new Polynom(rightFuncString);
			// Ensures that if the right function is null then the operation must be 'None'
			if(rightFunc==null && op!=Operation.None)
				throw new IllegalArgumentException("The operation can't be different than 'None' when the right function is null");
		}
		// If the string is not a complexFunction, enter it to the left function as Polynom
		else {
			this.leftFunc = new Polynom(s);
			this.rightFunc = null;
			this.op = Operation.None;
		}
	}
	
    /**
     * Checks if the parenthesis in the String are balanced
     * @param s String that represent complexFunction.
     * @return true, if valid string
     */
    public static boolean isValidString(String s) {
        if(s.contains("(") || s.contains(")")) {
        	// If the parenthesis are not balanced return false
        	if(s.indexOf(')')<s.indexOf('(')  || s.lastIndexOf('(')>s.lastIndexOf(')'))
        		return false;
        	// split the string into left and right functions and check both of them
        	else {
        		int indexOfComa = getComaIndex(s);
        		String s1 = s.substring(s.indexOf('(')+1, indexOfComa);
        		String s2 = s.substring(indexOfComa, s.lastIndexOf(')'));
        		return (isValidString(s1) && isValidString(s2));
        	}
        }
        else return true;
    }
    
    /**
     * Gets the main coma index.
     * @param s String that represent complexFunction.
     * @return the coma index
     */
    public static int getComaIndex(String s) {
		int numOpenBracket = 0, numClosingBracket = 0;
		// Traverse all the string and count opening and closing brackets
		for(int i=s.indexOf('(')+1; i<s.length(); i++) {
			if(s.charAt(i) == '(') numOpenBracket++;
			if(s.charAt(i) == ')') numClosingBracket++;
			if(s.charAt(i) == ',' && numOpenBracket==numClosingBracket)
				return i;		
		}
		throw new IllegalArgumentException("The String you entered should contain coma");
    }
	
	/**
	 * convert String to Operation
	 * @param s String that represent Operation
	 * @return the operation
	 */
	public Operation operationFromString(String s) {
		switch(s) {
			case "plus":  return Operation.Plus;
			case "mul":   return Operation.Times;
			case "times": return Operation.Times;
			case "div":   return Operation.Divid;
			case "divid": return Operation.Divid;
			case "max":   return Operation.Max;
			case "min":   return Operation.Min;
			case "comp":  return Operation.Comp;
			case "none":  return Operation.None;
			case "":      return Operation.None;
			default:      return Operation.Error;
		}
	}

	/**
	 * Initialize function from string.
	 * @param s String that represent function
	 * @return the function
	 */
	@Override
	public function initFromString(String s) {
		function f = new ComplexFunction(s);
		return f;
	}

	/** 
	 * returns the left side of the complex function - this side should always exists (should NOT be null).
	 * @return a function representing the left side of this complex funcation
	 */
	@Override
	public function left() {
		return this.leftFunc;
	}

	/** 
	 * returns the right side of the complex function - this side might not exists (aka equals null).
	 * @return a function representing the right side of this complex funcation
	 */
	@Override
	public function right() {	
		return this.rightFunc;
	}

	/**
	 * return The complex_function oparation: plus, mul, div, max, min, comp.
	 * @return the op
	 */
	@Override
	public Operation getOp() {
		return this.op;
	}
	
	/**
	 * return a String representing this complex function
	 * @return string
	 */
	public String toString() {
		String ans = "";
		// If the Operation is "None" than no operation will performed on the right function
		if(this.getOp() == Operation.None)
			ans = this.leftFunc.toString();
		else {
			ans += this.operationToString(this.getOp()) + "(";
			ans += this.leftFunc.toString() + ", ";
			ans += this.rightFunc.toString() + ")";
		}
		return ans;
	}
	
	/**
	 * Convert Operation to String.
	 * @param op the Operatiom
	 * @return the string
	 */
	public String operationToString(Operation op) {
		switch(op) {
			case Plus:  return "plus";
			case Times: return "mul";
			case Divid: return "div";
			case Max:   return "max";
			case Min:   return "min";
			case Comp:  return "comp";
			case None:  return "";
			default:    return "Error";
		}
	}
	
	/**
	 * Test if this ComplexFunction is logically equals to obj.
	 * @param obj the object
	 * @return true iff this ComplexFunction represents the same function as obj
	 */
	@Override
	public boolean equals (Object obj) {
		ComplexFunction cf = new ComplexFunction(obj.toString());
		double[] values = {1500, 54.6789, 0, Math.PI, -0.2323, -1500};
		for(int i=0; i<values.length; i++) {
			if(Math.abs(this.f(values[i])-cf.f(values[i])) > 0.001)
					return false;
		}
		return true;
	}

	/**
	 * create a deep copy of this ComplexFunction.
	 * @return a copy of the ComplexFunction, and not pointer to it
	 */
	@Override
	public function copy() {
		function f = new ComplexFunction(this.toString());
		return f;
	}
	
	/**
	 *  Add to this complex_function the f1 complex_function.
	 * @param f1 the complex_function which will be added to this complex_function.
	 */
	@Override
	public void plus(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Plus;
		this.rightFunc = f1;	
	}
	
	/**
	 *  Multiply this complex_function with the f1 complex_function.
	 * @param f1 the complex_function which will be multiply be this complex_function.
	 */
	@Override
	public void mul(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Times;
		this.rightFunc = f1;	
	}
	
	/**
	 *  Divides this complex_function with the f1 complex_function.
	 * @param f1 the complex_function which will be divid this complex_function.
	 */
	@Override
	public void div(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Divid;
		this.rightFunc = f1;	
	}
	
	/**
	 *  Computes the maximum over this complex_function and the f1 complex_function .
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the maximum.
	 */
	@Override
	public void max(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Max;
		this.rightFunc = f1;	
	}
	
	/**
	 *  Computes the minimum over this complex_function and the f1 complex_function.
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the minimum.
	 */
	@Override
	public void min(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Min;
		this.rightFunc = f1;	
	}

	/**
	 * This method wrap the f1 complex_function with this function: this.f(f1(x))
	 * @param f1 complex function
	 */
	@Override
	public void comp(function f1) {
		this.leftFunc = this.copy();
		this.op = Operation.Comp;
		this.rightFunc = f1;	
	}
	
	/**
	 * calculate the value of the complexFunction when inserted value of x.
	 * @param x double
	 * @return the value of the complexFunction
	 */
	@Override
	public double f(double x) {
		switch(this.getOp()) {
			case Plus:  return leftFunc.f(x) + rightFunc.f(x);
			case Times: return leftFunc.f(x) * rightFunc.f(x);
			case Divid: return leftFunc.f(x) / rightFunc.f(x);			
			case Max: 	return Math.max(leftFunc.f(x), rightFunc.f(x));
			case Min: 	return Math.min(leftFunc.f(x), rightFunc.f(x));
			case Comp:	return leftFunc.f(rightFunc.f(x));
			case None:	return leftFunc.f(x);
			default:	throw new ArithmeticException("The operation you entered is unsupported");
		}
	}
}