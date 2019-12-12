# OOP_Ex1
#### Authors: Meir Nizri and Avihay Bernholtz

This repository represents a mathematical function, which is the main interface, which can be a single monom, polynomial, or complex function. In addition, you can build an object that expresses a collection of functions.

#### Monom
The Monom class represents a simple monom of shape ax^b, where a is a real number and b is an integer (summed a none negative), see: https://en.wikipedia.org/wiki/Monomial. We defined monom so that there can be spaces everywhere (e,g: 2 x ^ 3), as long as the main structure we described is preserved. Writing capital 'X' instead of 'x' will cause an exception.
There are some special monoms:
1. Real number followed by x - the power of the monom is 1.
2. Real number only - the power of the monom is 0.

The class support simple operations as: construction, toString, value at x, derivative, add and multiply.


#### Polynom
This  class represents a general Polynom: f(x) = a_1X^b_1 + a_2*X^b_2 +...+ a_n*Xb_n, where: a_1, a_2 ... a_n are real numbers and b_1, b_2 ... b_n are integer (summed a none negative). all the elements in the polynom are standard monoms as we described in the monom class. We defined Polynom so that there can be spaces everywhere (e,g: 2x^ 3  –  5 x), as long as the main structure we described is preserved. 
This Polynom can get different monoms with equal powers, The constructor will add all monoms of the same power and order all the elements from highest power to the lowest.

Polynom support simple operations as: construction, toString value at x, add, subtract, multiply functionality. it also support the following: 
1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral 
2. Finding a numerical value between two values (currently support root only f(x)=0). 
3. Derivative

#### ComplexFunction
This class represents a general Complex Function: Operation(f1, f2), where f1 and f2 are function, and even Complex Function. Operation can be one of the following: Plus - f1+f2, Times - f1*f2, Divid - f1/f2, Min - min(f1,f2), Max - max(f1,f2), Comp - f1(f2), and also can be None operation, which means its f1 only. other Operation inserted to the constructor will defined as "Error", which mean's unsupported operation. Function f2 can be "null" only when the operation is defined as the None operation, any attempt to insert another operation will throw an error.

ComplexFunction support simple operations as: construction, toString, value at x (only if all the operation diffrent than Error). In all of the following operations: plus, mul, div, min, max, comp,  that can be operated on the function with parameter f, the current ComplexFunction (this) will entered into f1, the function f will  entered into f2, and the primary operation will be the operation that we have operated on the function.

#### Functions_GUI
This class represents a collection of functions, which can be any of the three classes we described above. The operations defined on this class are:
1. Print all functions to a file. Each function will have its own line.
2. Read a file containing functions.
3. Draws all functions in to graph from parameters set by the user. Each function will receive a different random color.
4. Draws all the functions in the graph from parameters obtained from the JSON file. Each function will receive a different random color.
