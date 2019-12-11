

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javax.security.auth.x500.X500Principal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Functions_GUI implements functions {
	private ArrayList<function> Functions;
	public Functions_GUI() {
		Functions=new ArrayList<>();
		Functions.add(new ComplexFunction());
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
		String line=null;
		while((line=bufferedReader.readLine())!=null){
			function Func=new ComplexFunction(line);
			Functions.add(Func);
		}

		bufferedReader.close();
	}

	@Override
	public void saveToFile(String file) throws IOException {
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<Functions.size();i++) {
			bufferedwriter.write(Functions.get(i).toString());
			bufferedwriter.newLine();
		}
		bufferedwriter.close();

	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width,height);
		double[] x=new double[resolution+1];
		double[][] y=new double[Functions.size()][resolution+1];
		double ranger=(rx.get_max()-rx.get_min())/resolution;
		for(int i=1;i<resolution+1;i++) {
			x[i]=rx.get_min()+(ranger*i);
		}
		for(int i=0;i<Functions.size();i++) {
			for(int j=1;j<resolution+1;j++) {		
				y[i][j]=Functions.get(i).f(x[j]);
			}
		}
		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());

		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (int i = 1; i <resolution+1; i++) {
			StdDraw.line(x[i],ry.get_min(),x[i],ry.get_max());
		}
		//////// horizontal lines
		for (double i =ry.get_min(); i <=ry.get_max(); i=i+1) {
			StdDraw.line(rx.get_min(),i,rx.get_max(), i);
		}
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(),x[resolution/2],rx.get_max(),x[resolution/2]);
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		//x initialize
		for (int i = 1; i < resolution+1; i=i+1) {
			StdDraw.text(x[i]-0.07, -0.30, Integer.toString(((int)x[i])));
		}
		StdDraw.line(x[resolution/2],ry.get_min(),x[resolution/2],ry.get_max());

		for (int i =((int)ry.get_min()); i <= ry.get_max(); i=i+1) {
			StdDraw.text(x[resolution/2]-0.07, i-0.30,Integer.toString(i));
		}

		for (int i = 0;i<Functions.size(); i++) {
			Color RandomColor= new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			StdDraw.setPenColor(RandomColor);
			for(int j=1;j<resolution;j++) {
				StdDraw.line(x[j], y[i][j], x[j+1], y[i][j+1]);
			}

		}
		StdDraw.setPenColor(Color.RED);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(x[resolution/2], 0);




	}

	@Override
	public void drawFunctions(String json_file) throws IOException,ParseException  {
		int Width=0,Height=0,Resolution=0;//our paramateres
		Range Range_x,Range_y;
		double[][] Ranges=new double[2][2];
		try {
			FileReader jsonreader=new FileReader(json_file);
			JSONParser parser=new JSONParser();              //Those are the objects that help to convert the jsonfile into parameters
			JSONObject Jobj=(JSONObject)parser.parse(jsonreader);

			/*Initilaizing The parameters from the json file
			 first we must check if the value is not empty and has null value
			 and after this we convert those values into our parameters*/
			
			if(Jobj.get("Width")!=null&&Jobj.get("Height")!=null&&Jobj.get("Resolution")!=null) {
				Width=Math.toIntExact((long)Jobj.get("Width"));
				Height=Math.toIntExact((long)Jobj.get("Height"));
				Resolution=Math.toIntExact((long)Jobj.get("Resolution"));
				System.out.println(Width+" "+Height+" "+Resolution);
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
			System.out.println(Range_y.toString()+" "+Range_x.toString());

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




