

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Functions_GUI implements functions {
	private ArrayList<function> Functions;
	public Functions_GUI() {
		Functions=new ArrayList<>();
	}
	@Override
	public int size() {
		return Functions.size();
	}

	@Override
	public boolean isEmpty() {
		if(Functions.isEmpty()) return true;

		return false;
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof function) {
			if(Functions.contains(o)) return true;
			else return false;
		}

		else throw new IllegalArgumentException("The argument you entered is not instance of Function");	
	}

	@Override
	public Iterator<function> iterator() {
		return Functions.iterator();
	}

	@Override
	public Object[] toArray() {
		Object[] obj=new Object[Functions.size()];
		obj=Functions.toArray();
		return obj;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(function e) {
		if(!Functions.contains(e)) {
			Functions.add(e);
			return true;
		}
		else {
			System.out.println("This Function is already in the List");
			return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		if(o instanceof function) {
			if(Functions.contains(o)) {
				Functions.remove(o);
				return true;
			}
			else {
				System.out.println("The List of Functions in not contains this function");
				return false;
			}
		}
		else throw new IllegalArgumentException("The argument you entered is not instance of Function");	

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		if(!Functions.containsAll(c)) {
			Functions.addAll(c);
			return true;
		}
		else {
			System.out.println("This Function's Collection is already in the List");
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if(c instanceof function) {
			if(Functions.containsAll(c)) {
				Functions.removeAll(c);
				return true;
			}
			else {
				System.out.println("This Coleection of Functions in not contains this function");
				return false;
			}
		}
		else throw new IllegalArgumentException("This Collection is not of Functions");

	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if(c instanceof function) {
			if(Functions.containsAll(c)) {
				Functions.retainAll(c);
				return true;
			}
			else {
				System.out.println("This Coleection of Functions in not contains this function");
				return false;
			}
		}
		else throw new IllegalArgumentException("This Collection is not of Functions");
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initFromFile(String file) throws IOException {
		 BufferedReader Buff = new BufferedReader(new FileReader(file));
		 StringBuffer NewFunc=new StringBuffer("");
		 String buff;
		 while((buff=Buff.readLine())!=null) {
			NewFunc.append(buff);
		 }
		 buff=NewFunc.toString();
		 Buff.close();
		 ComplexFunction Func=new ComplexFunction(buff);
		 Functions.add(Func);
	}

	@Override
	public void saveToFile(String file) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawFunctions(String json_file) {
		// TODO Auto-generated method stub

	}

}
