package Ex1;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Functions_GUI implements functions {
	
	private ArrayList<function> Functions;
	
	public Functions_GUI() {
		Functions = new ArrayList<function>();
	}
	
	@Override
	public int size() {
		return Functions.size();
	}

	@Override
	public boolean isEmpty() {
		return Functions.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return Functions.contains(o);	
	}

	@Override
	public Iterator<function> iterator() {
		return Functions.iterator();
	}

	@Override
	public Object[] toArray() {
		return Functions.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return Functions.toArray(a);
	}

	@Override
	public boolean add(function e) {
		return Functions.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return Functions.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return Functions.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		return Functions.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return Functions.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return Functions.retainAll(c);
	}

	@Override
	public void clear() {
		Functions.clear();
	}

	@Override
	public void initFromFile(String file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = bufferedReader.readLine()) != null){
			if(line.contains("f(x)"))
				line = line.substring(line.lastIndexOf('=')+1);
			function Func = new ComplexFunction(line);
			Functions.add(Func);
		}
		bufferedReader.close();
	}

	@Override
	public void saveToFile(String file) throws IOException {
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
		for(int i=0; i<Functions.size(); i++) {
			bufferedwriter.write(Functions.get(i).toString());
			bufferedwriter.newLine();
		}
		bufferedwriter.close();
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width,height);
		
		// The range for x
		double xrange = rx.get_max()-rx.get_min();
		double yrange = ry.get_max()-ry.get_min();	
		
		// Set scales
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		
		// Vertical and Horizontal line
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (double i=rx.get_min(); i<rx.get_max(); i+=(xrange/20)) 
			StdDraw.line(i, ry.get_min(), i, ry.get_max());
		for (double i=ry.get_min(); i<=ry.get_max(); i+=(yrange/20)) 
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
		
		// Preperation to draw axis
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		
		// x axis and numbers
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		for (double i=rx.get_min(); i<rx.get_max(); i+=(xrange/20))
			StdDraw.text(i, -0.35, Integer.toString((int)i));
		
		// y axis ans numbers
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());
		for (double i=ry.get_min(); i<=ry.get_max(); i+=(yrange/20))
			StdDraw.text(-0.35, i, Integer.toString((int)i));

		// Draw the functions
		for (int i = 0; i<Functions.size(); i++) {
			Color RandomColor = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			StdDraw.setPenColor(RandomColor);
			for(int j=0; j<resolution-1; j++) {
				double x1 = rx.get_min() + (xrange/resolution*j);
				double x2 = rx.get_min() + (xrange/resolution*(j+1));
				double value1 = Functions.get(i).f(x1);
				double value2 = Functions.get(i).f(x2);
				StdDraw.line(x1, value1, x2, value2);
			}
		}
	}

	@Override
	public void drawFunctions(String json_file)  {
		int Width=0,Height=0,Resolution=0;//our paramateres
		Range Range_x,Range_y;
		double[][] Ranges = new double[2][2];
		try {
			FileReader jsonreader = new FileReader(json_file);
			JSONParser parser = new JSONParser();              //Those are the objects that help to convert the jsonfile into parameters
			JSONObject Jobj = (JSONObject)parser.parse(jsonreader);

			/*Initilaizing The parameters from the json file
			 first we must check if the value is not empty and has null value
			 and after this we convert those values into our parameters*/
			
			if(Jobj.get("Width")!=null&&Jobj.get("Height")!=null&&Jobj.get("Resolution")!=null) {
				Width=Math.toIntExact((long)Jobj.get("Width"));
				Height=Math.toIntExact((long)Jobj.get("Height"));
				Resolution=Math.toIntExact((long)Jobj.get("Resolution"));
			}
			
			
			/*Doing the same thing to the Ranges. 
			 because range object has tw values,we treat him like an array
			 and after this we pass those array's values to the ranges*/
			
			JSONArray xValues,yValues;
			if(Jobj.get("Range_X")!=null&&Jobj.get("Range_Y")!=null) {
				xValues=(JSONArray)Jobj.get("Range_X");
				yValues=(JSONArray)Jobj.get("Range_Y");
				int count=0;
				Iterator<?> Xitr=xValues.iterator(),Yitr=yValues.iterator();
				while(Xitr.hasNext()&&Yitr.hasNext()&&count<=1) {
					Ranges[0][count]=(long)Xitr.next();
					Ranges[1][count]=(long)Yitr.next();
					count++;
				}

			}
			Range_x=new Range(Ranges[0][0],Ranges[0][1]);
			Range_y=new Range(Ranges[1][0],Ranges[1][1]);
			drawFunctions(Width, Height,Range_x,Range_y,Resolution);
		}
		catch(IOException e){
			System.out.println("Error!,check if there is such file with the path "+json_file);
		}
		catch(ParseException e) {
			System.out.println("Sothing got wrong with the convertion of the Jsonfle into parameters.");
			System.out.println("Please check your file's syntax");
		}
		catch(Exception e) {
			throw e;
		}
	}
}