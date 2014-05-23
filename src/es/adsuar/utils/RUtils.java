package es.adsuar.utils;

import java.util.List;

public class RUtils {
	public RUtils() {
		;
	}

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
