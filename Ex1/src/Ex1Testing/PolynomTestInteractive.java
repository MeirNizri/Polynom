package Ex1Testing;

import java.util.Scanner;
import Ex1.*;

public class PolynomTestInteractive {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.print("choose Polynom:  ");
		Polynom p = new Polynom(in.nextLine());	
		System.out.println("the polynom: " + p.toString());
		System.out.print("enter Polynom to add: ");
		Polynom a = new Polynom(in.nextLine());
		p.add(a);
		System.out.println("result adding: " + p.toString());
		System.out.println();
		
		System.out.print("enter Polynom to substract: ");
		Polynom b = new Polynom(in.nextLine());
		p.substract(b);
		System.out.println("result substracting: " + p.toString());
		System.out.println();
		
		System.out.print("enter Polynom to multiply: ");
		Polynom c = new Polynom(in.nextLine());
		p.multiply(c);
		System.out.println("result multyplying: " + p.toString());
		System.out.println();
		
		System.out.print("enter Polynom to check if equal: ");
		Polynom d = new Polynom(in.nextLine());
		System.out.println("result check: " + p.equals(d));
		System.out.println();
		
		System.out.print("enter value of x: ");
		double e = in.nextDouble();
		System.out.println("value of f(x): " + p.f(e));
		System.out.println();
		
		System.out.println("polynom: " + p.toString());
		System.out.println("result derivative: " + p.derivative());
		System.out.println();
		
		System.out.println("area calculetor of: "+ p.toString());
		System.out.print("enter value of x0: ");
		double f = in.nextDouble();
		System.out.print("enter value of x1: ");
		double g = in.nextDouble();
		System.out.println("area in (x0,x1): " + p.area(f, g, 0.0001));	
		System.out.println();
		
		System.out.println("root calculetorof: " + p.toString());
		System.out.print("enter value of x0: ");
		double h = in.nextDouble();
		System.out.print("enter value of x1: ");
		double i = in.nextDouble();
		System.out.println("root in (x0,x1): " + p.root(h, i, 0.001));	
	}
}