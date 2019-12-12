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



// TODO: Auto-generated Javadoc
/**
 * The Class Functions_GUI.
 * The class represents a collection of Functions.
 * it can draw or to save in file those functions.
 * also,it can get sizes from JsonFile and by them to draw the functions
 * Functions_GUI implements the interface "functions" so it Override all of it's methods
 * and the methods of Collection<function> taht functions implements
 */
public class Functions_GUI implements functions {

	/** Init a new collection of functions that called Functions. */
	private ArrayList<function> Functions;

	/**
	 * Instantiates a new Functions_GUI object.
	 */
	public Functions_GUI() {
		Functions=new ArrayList<>();

	}

	/* 
	 * Returns the size of Functions
	 */
	@Override
	public int size() {
		return Functions.size();
	}

	/* 
	 * Returns "true" if Functions is empty
	 */
	@Override
	public boolean isEmpty() {
		return Functions.isEmpty();
	}

	/* 
	 * Returns true if Functions contains o
	 * @param o-Object 
	 */
	@Override
	public boolean contains(Object o) {
		return Functions.contains(o);	
	}

	/* 
	 * Returns Iterator that pass over Functions
	 */
	@Override
	public Iterator<function> iterator() {
		return Functions.iterator();
	}

	/* 
	 * Converts Function into array of Objects
	 */
	@Override
	public Object[] toArray() {

		return Functions.toArray();
	}

	/* 
	 *Converts Functions into array of T
	 * @param a-an Array from the Object <T> 
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return Functions.toArray(a);
	}

	/* 
	 * Adds function to Functions 
	 *  Returns true if succeed
	 * @param e-function that we want to add to Functions
	 */
	@Override
	public boolean add(function e) {
		return Functions.add(e);
	}

	/* (non-Javadoc)
	* Removes Object from Functions 
	 *  Returns true if succeed
	 * @param o-Object that we want to add to Functions
	 */
	@Override
	public boolean remove(Object o) {
		return Functions.remove(o);
	}

	/* 
	 *  Removes all the function's that in the collection it gets
	 *  Returns true if succeed
	 * @param c - a collection that might contain functions
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return Functions.containsAll(c);

	}

	/* 
	 * Removes all the function's that in the collection it gets
	 * Returns true if succeed
	 * @param c - a collection that contain functions (extends the interface "function")
	 */
	@Override
	public boolean addAll(Collection<? extends function> c) {
		return Functions.addAll(c);
	}

	/* 
	 * Removes all the function's that in the collection it gets
	 * Returns true if succeed
	 * @param c - a collection that might contain functions
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return Functions.removeAll(c);

	}

	/* 
	 * Retain in the Function's list only the funtions that in the collection it gets
	 * Returns true if succeed
	 * @param c - a collection that might contain functions
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return Functions.retainAll(c);
	}

	/* 
	 * Clears the list from all its functions 
	 */
	@Override
	public void clear() {
		Functions.clear();

	}

	/* 
	 * Init a new collection of functions from a file
	 * @param file - the file name
	 * @throws IOException if the file does not exists of unreadable (wrong format)
	 */
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

	/* 
	 *  Draws all the functions in the collection in a GUI window using the
	 * given parameters for the GUI windo and the range & resolution
	 * @param width - the width of the window - in pixels
	 * @param height - the height of the window - in pixels
	 * @param rx - the range of the horizontal axis
	 * @param ry - the range of the vertical axis
	 * @param resolution - the number of samples with in rx: the X_step = rx/resulution
	 */
	@Override
	public void saveToFile(String file) throws IOException {
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
		Iterator<function> itr=this.iterator();
		while(itr.hasNext()) {
			bufferedwriter.write(itr.next().toString());
			bufferedwriter.newLine();
		}
		bufferedwriter.close();

	}

	/* 
	 * Draws all the functions in the collection in a GUI window using the
	 * given parameters for the GUI windo and the range & resolution
	 * @param width - the width of the window - in pixels
	 * @param height - the height of the window - in pixels
	 * @param rx - the range of the horizontal axis
	 * @param ry - the range of the vertical axis
	 * @param resolution - the number of samples with in rx: the X_step = rx/resulution 
	 */
	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {

		StdDraw.setCanvasSize(width,height);
		double ranger=(rx.get_max()-rx.get_min())/resolution;
		int RangeY=(int)((ry.get_max()-ry.get_min())/20);
		int RangeX=(int)((rx.get_max()-rx.get_min())/20);

		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());

		//Setting the horizontal and vertical lines
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (double i = rx.get_min(); i <=rx.get_max(); i+=RangeX) {
			StdDraw.line(i,ry.get_min(),i,ry.get_max());
		}

		for (double i =ry.get_min(); i <=ry.get_max(); i=i+=RangeY) {
			StdDraw.line(rx.get_min(),i,rx.get_max(), i);
		}

		//Paints the Y and X axises with numbers
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(),0,rx.get_max(),0);
		StdDraw.line(0,ry.get_min(),0,ry.get_max());
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));

		for (int i =0,j=0;( i <=rx.get_max()||j>=rx.get_min()); i+=RangeX,j-=RangeX) {
			if(i<=rx.get_max()) StdDraw.text(i-0.30, -0.30, Integer.toString(i));
			if(j>=rx.get_min())StdDraw.text(j-0.30, -0.30, Integer.toString(j));
		}	
		//y numbers

		for (int i =0,j=0;( i <=ry.get_max()||j>=ry.get_min()); i+=RangeY,j-=RangeY) {
			if(i<=ry.get_max()) StdDraw.text(0-0.30, i-0.30,Integer.toString(i));
			if(j>=ry.get_min()) StdDraw.text(0-0.30, j-0.30,Integer.toString(j));
		}




		for (int i = 0;i<Functions.size(); i++) {
			Color RandomColor= new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			StdDraw.setPenColor(RandomColor);
			for(int j=1;j<resolution-1;j++) {
				double x1=rx.get_min()+(ranger*j);
				double x2=rx.get_min()+(ranger*(j+1));
				double value1=(double)Math.round((Functions.get(i).f(x1))*100000d)/100000d;
				double value2=(double)Math.round((Functions.get(i).f(x2))*100000d)/100000d;
				StdDraw.line(x1,value1, x2,value2);
			}

		}
		StdDraw.setPenColor(Color.RED);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(0, 0);





	}

	/* 
	 *  * Draws all the functions in the collection in a GUI window using the given JSON file
	 * @param json_file - the file with all the parameters for the GUI window. 
	 * Note: is the file id not readable or in wrong format should use default values.
	 */
	@Override
	public void drawFunctions(String json_file) {
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




