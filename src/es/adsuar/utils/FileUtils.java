package es.adsuar.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * My own file utils.
 * 
 * @author Antonio Jesús Adsuar Gómez
 * 
 */

public class FileUtils {
	/**
	 * Class constructor
	 */
	FileUtils() {
		;
	}

	/**
	 * Method that reads a file and converts its content into a String.
	 * @param pathname Pathname where the file is stored at.
	 * @return String that contains the whole file content.
	 * @throws IOException
	 */
	public static String readFile(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}
}
