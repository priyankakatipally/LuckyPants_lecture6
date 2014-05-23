package com.luckypants.command;

import java.io.File;

public class ProvideFileOnTheServerCommand {
	public File execute(String fullPath){
		try {
			File file = new File(fullPath);

			return file;
		} catch (Exception e) {
			return null;
		}
	}
}
