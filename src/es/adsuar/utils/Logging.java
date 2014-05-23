package es.adsuar.utils;

import java.util.logging.Logger;

public class Logging {
	public Logging() {

	}

	public static void info(String message) {
		Logger.getAnonymousLogger().info(message);
	}
}
