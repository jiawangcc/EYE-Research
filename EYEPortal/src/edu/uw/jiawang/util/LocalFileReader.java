package edu.uw.jiawang.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LocalFileReader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String file = "/Users/Jia/Downloads/myData.csv";
		int lc = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if (line.split(",").length == 0) {
		    		continue;
		    	}
		    	System.out.println(line.split(",")[0]);
		    	lc++;
		        
		    }
		    // line is not visible here.
		}
		System.out.println(lc);

	}

}
