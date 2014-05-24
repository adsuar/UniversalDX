package es.adsuar.utils;

import java.util.List;

/**
 * My own R utils.
 * 
 * @author Antonio Jesús Adsuar Gómez
 * 
 */

public class RUtils {
	/**
	 * Class constructor.
	 */
	public RUtils() {
		;
	}

	/**
	 * Method that converts a list of double objects into its definition as a R
	 * array.
	 * 
	 * @param ld
	 *            List to be converted.
	 * @return String with the definition of the list as a R array.
	 */
	public static String List2RArray(List<Double> ld) {
		String arrayDeclaration = "c(";
		int count = 0;

		for (Double d : ld) {
			if (count++ != 0)
				arrayDeclaration += "," + d.toString();
			else
				arrayDeclaration += d.toString();
		}

		arrayDeclaration += ")";

		return arrayDeclaration;
	}

	/**
	 * Method that converts a list of double objects into a CSV line entry with
	 * comma separator.
	 * 
	 * @param ld
	 *            List to be converted.
	 * @return String with the definition of the list as a CSV line entry with
	 *         comma separator.
	 */
	public static String List2RCSV(List<Double> ld) {
		String arrayDeclaration = "";
		int count = 0;

		for (Double d : ld) {
			if (count++ != 0)
				arrayDeclaration += "," + d.toString();
			else
				arrayDeclaration += d.toString();
		}

		return arrayDeclaration;
	}
}
