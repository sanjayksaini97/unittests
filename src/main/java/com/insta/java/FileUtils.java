package com.insta.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class FileUtils {
	
	public List<String> getFileContent(String fileName) throws IOException {
		List<String> list = new ArrayList<>();
		//String fileName = "/tenant-list.txt";
		Class<Matcher> clazz = Matcher.class;
	    InputStream inputStream = clazz.getResourceAsStream("/" + fileName);
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)) ;
	        String line;
	        while ((line = br.readLine()) != null) {
	            list.add(line.trim());
	        }
	   
	    return list;
	}
}
