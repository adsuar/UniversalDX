package es.adsuar.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {
	/**
	 * Class constructor
	 */
	FileUtils() {
		;
	}

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