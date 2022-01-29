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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Comparator;


import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;



public class MapToDataFile {

	
	
	static int errors = 0;
	static int file_num = 0;

	/**
	 * 
	 * @param file
	 * @param batchSize
	 * @param fileList
	 */
	static void mapIt(String file, int batchSize, ArrayList<String> fileList) {

		try {
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fin);

			// Here we uncompress .bz2 file
			CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			// Initialize a natch
			StringBuilder batch = new StringBuilder("");

		

			mapToFile(batchSize, br, batch, fileList);



			fin.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompressorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param batchSize
	 * @param br
	 * @param batch
	 * @param lineCounter
	 * @throws IOException
	 */
	static void mapToFile(int batchSize, BufferedReader br, StringBuilder batch, ArrayList<String> fileList) 
			throws IOException {
		String line;
		Map<String, Float> tempData = new HashMap<String, Float>(batchSize);
		
		Long lineCounter = 0l;
		// Start reading the file line by line.
		while ((line = br.readLine()) != null) {

			lineCounter += 1;

			// add the current text line to the data batch that we want to process.
			batch.append(line);
			batch.append("\n");

			if (lineCounter % batchSize == 0) {
				tempData = MapToDataFile.processLine(batch.toString());

				System.out.println(lineCounter + "  Lines processed!");

				// We can write the map into disk and read it back if it is too big.
				StringBuilder temp_name = new StringBuilder("temp");
				temp_name.append(file_num++);
				temp_name.append(".txt");
				MapToDataFile.appendToTempFile(tempData, temp_name.toString());
				fileList.add(temp_name.toString());

				// reset the batch to empty string and restart.
				batch = new StringBuilder("");
			}
		}

		//If num lines not perfectly divisible by the batch size, handle rest. 
		if (lineCounter % batchSize != 0) {
			tempData = MapToDataFile.processLine(batch.toString());

			System.out.println(lineCounter + "  Lines processed!");

			// Writing out remaining lines left in the batch. 
			StringBuilder temp_name = new StringBuilder("temp");
			temp_name.append(file_num++);
			temp_name.append(".txt");
			MapToDataFile.appendToTempFile(tempData, temp_name.toString());
			fileList.add(temp_name.toString());
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 */

	public static Map<String, Float> processLine(String input) {

		String[] lines = input.split("\\R|\\n");
				
		Map<String, Float> processed = new HashMap<String, Float>();

		for(String line : lines) {
			int valid = 0;
			String tokens[] = line.trim().split(",");
			if (tokens.length != 17) {
				valid = -1;
			}
				for (int j = 4; j < 16 && valid == 0; j++) {
					if(j == 10) {
						continue;
					}
					try {
					}
					catch (NumberFormatException e) {
						valid = -1;
					} 
				}

			if(errors != 5 && valid == -1) {
				Map<String, Float> errMap = new HashMap<String, Float>(){{
					this.put(String.join(",", tokens), -1.0f);
				}};
				MapToDataFile.appendToTempFile(errMap , "errors.txt");
				errors++;
			} 
			if(valid == 0) {
				processed.put(line, Float.parseFloat(tokens[16]));
			}
		}	

		return processed;

	}

	public static void appendToTempFile(Map<String, Float> data, String outputFile) {

		// new file object
		File file = new File(outputFile);
		BufferedWriter bf = null;

		try {

			// create new BufferedWriter for the output file
			// true means append
			bf = new BufferedWriter(new FileWriter(file, true));

			// iterate map entries
			LinkedHashMap<String, Float> tempSorted = new LinkedHashMap<>();
		
			data.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
				.forEachOrdered(x -> tempSorted.put(x.getKey(), x.getValue()));

			for (Map.Entry<String, Float> entry : tempSorted.entrySet()) {
				
				//Special case for error lines, don't need to append values, just write the line
				if(entry.getValue() == -1) {
					bf.write(entry.getKey() + "\n");
					return;
				}
				//For proper lines, append the value for easier sorting later.
				bf.write(entry.getKey() + "  /" + entry.getValue());

				// new line
				bf.newLine();
			}

			bf.flush();
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
