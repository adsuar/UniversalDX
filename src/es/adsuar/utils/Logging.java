package es.adsuar.utils;

import java.util.logging.Logger;

/**
 * My own loggin utils.
 * 
 * @author Antonio Jesús Adsuar Gómez
 * 
 */

public class Logging {
	/**
	 * Class constructor.
	 */
	public Logging() {

	}

	/**
	 * Method that launches an info log.
	 * @param message Message to be logged.
	 */
	public static void info(String message) {
		Logger.getAnonymousLogger().info(message);
	}
}
