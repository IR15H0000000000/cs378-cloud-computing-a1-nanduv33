package edu.utexas.cs.cs378;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reducer {

	private static BufferedReader br;
	private static BufferedReader br1;
	private static final String FINAL_OUTPUT = "SORTED-FILE-RESULT.txt";

	public static void reduceFromFile(ArrayList<String> fileList) {
		int num_files = fileList.size();
		
		String line;
		String line1;
		while (fileList.size() >= 2) {
			StringBuilder temp_name = new StringBuilder();
			if(fileList.size() == 2) {
				temp_name.append(FINAL_OUTPUT);
			}
			else {
				temp_name.append("temp");
				temp_name.append(num_files++);
				temp_name.append(".txt");
			}
			File file = new File(temp_name.toString());
			BufferedWriter bf = null;
			FileInputStream fin;
			FileInputStream fin1;
			try {
				fin = new FileInputStream(fileList.get(0));
				fileList.remove(0);
				fin1 = new FileInputStream(fileList.get(0));
				fileList.remove(0);
				BufferedInputStream bis = new BufferedInputStream(fin);
				br = new BufferedReader(new InputStreamReader(bis));
				BufferedInputStream bis1 = new BufferedInputStream(fin1);
				br1 = new BufferedReader(new InputStreamReader(bis1));
				bf = new BufferedWriter(new FileWriter(file, true));
				
				Long lineCounter = 0l;
				line = br.readLine();
				line1 = br1.readLine();
				// Start reading the file line by line.
				while (line != null && line1 != null) {							
					lineCounter += 1;
					String[] data= line.split("  /");
					String[] data1= line1.split("  /");
					float count = Float.parseFloat(data[1]); 
					float count1 = Float.parseFloat(data1[1]);
					if (count > count1) {
						bf.write(line);
						bf.newLine();
						line = br.readLine();
					}
					else {
						bf.write(line1);
						bf.newLine();
						line1 = br1.readLine();
					}
			
				}

				while (line != null) {
					//String[] data = line.split("  /");
					bf.write(line);
					line = br.readLine();
					bf.newLine();
				}
				while (line1 != null) {
					//String[] data1 = line1.split("  /");
					bf.write(line1);
					line1 = br1.readLine();
					bf.newLine();
				}
				bf.flush();
				fileList.add(file.toString());		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// always close the writer
					bf.close();
				} catch (Exception e) {
			}
		}
		}
		
	}

}
