package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class InputStreamTest {
	
	public static void main(String[] args){
		InputStreamTest test = new InputStreamTest();
		InputStream input = test.getClass().getClassLoader().getResourceAsStream("readme.txt");
		try {
	        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
	        String line=null;
	            while((line=reader.readLine())!=null){
	                System.out.println(line);
	            }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
