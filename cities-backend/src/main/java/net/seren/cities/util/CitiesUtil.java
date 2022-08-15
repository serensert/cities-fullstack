package net.seren.cities.util;

public class CitiesUtil {
	
	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}
