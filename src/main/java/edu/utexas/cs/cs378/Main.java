package edu.utexas.cs.cs378;

import java.util.Map;
import java.io.File;
import java.util.ArrayList;

public class Main {

	/**
	 * A main method to run examples.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {

		// pass the file name as the first argument. 
		// We can also accept a .bz2 file
		
		// This line is just for Kia :) 
		// You should pass the file name and path as first argument of this main method. 
		ArrayList<String> fileList = new ArrayList<String>();
		String file = "./src/main/java/edu/utexas/cs/cs378/taxi-data-sorted-small.csv.bz2";
		

		if(args.length>0)
			file=args[0];
		
		//addd an arraylist
		int batchSize = 1000000;
		
		if(args.length>1)
			batchSize = Integer.parseInt(args[1]);
		
		String outputTempFile  = "temp.txt";
		
		
		MapToDataFile.mapIt(file, batchSize, outputTempFile, fileList);
		
		System.out.println("Now, we start reading the temp data and reducing it.");
		
		Reducer.reduceFromFile(fileList);	
		
		//MapToDataFile.appendToTempFile(results, "results.txt");
		

	}
	
}