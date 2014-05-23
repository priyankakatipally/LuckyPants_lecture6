package com.luckypants.command;

import java.io.InputStream;

public class ProvidePackagedFileCommand {
	public InputStream execute(String filename){
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
			return is;
		} catch (Exception e) {
			return null;
		}
	}
}
