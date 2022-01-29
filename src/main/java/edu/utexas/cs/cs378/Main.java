package edu.utexas.cs.cs378;

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
		String file = "taxi-data-sorted-large.csv.bz2";
		

		if(args.length>0)
			file=args[0];
		
		//default batch size 500000
		int batchSize = 500000;
		
		if(args.length>1)
			batchSize = Integer.parseInt(args[1]);
		
		//ArrayList to store list of all temp files.
		ArrayList<String> fileList = new ArrayList<String>(); 
		
		MapToDataFile.mapIt(file, batchSize, fileList);
		
		System.out.println("Now, we start reading the temp data and reducing it.");
		
		Reducer.reduceFromFile(fileList);	
	}
	
}