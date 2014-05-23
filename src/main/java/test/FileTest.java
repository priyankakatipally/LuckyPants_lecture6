package test;

import java.io.File;


public class FileTest {
	public static void listFiles(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	System.out.println("DIRECTORY:"+fileEntry.getName());
	            listFiles(fileEntry);
	        } else {
	        	if(fileEntry.isHidden()){
	        		System.out.println("hidden:"+fileEntry.getName());
	        	}
	        	else System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	public static void main(String[] args){
		File folder = new File("/Users/priyanka/Desktop/test");
		listFiles(folder);
		folder = new File(".");
		System.out.println(folder.getAbsolutePath());
	}

}
