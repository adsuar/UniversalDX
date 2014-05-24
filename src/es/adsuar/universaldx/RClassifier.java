package es.adsuar.universaldx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import es.adsuar.utils.FileUtils;
import es.adsuar.utils.Logging;

public class RClassifier {

	// Connector to the RServer
	RConnection c;

	// Properties of the project
	private static Properties properties;

	// Base Folder
	private String baseFolder;

	/**
	 * Class constructor
	 */
	public RClassifier() throws RserveException, REXPMismatchException {
		Logging.info(getHeader() + "Loading class.");

		// Later I'll use property files.

		try {
			properties = new Properties();
			FileInputStream fis = new FileInputStream(
					"./resources/config/universaldx.properties");
			properties.load(fis);
		} catch (Exception e) {
			Logging.info(" The file universaldx.properties doesn't exist.");
			System.exit(-1);
		}

		baseFolder = properties.getProperty("baseFolder");

		// Create the connection to the RServer
		c = new RConnection();

		// Get the R version
		REXP x = c.eval("R.version.string");
		System.out.println(x.asString());

		// I load my own R dependencies and functions
		try {
			loadLibrary();
		} catch (IOException e) {
			Logging.info("The R custom library hasn't been loaded.");
			System.exit(-1);
		}

		Logging.info(getHeader() + "Class loaded.");
	}

	/**
	 * Method that loads dependencies and functions
	 * 
	 * @throws IOException
	 * @throws RserveException
	 * @throws REXPMismatchException
	 */
	private void loadLibrary() throws IOException, RserveException,
			REXPMismatchException {
		Logging.info(getHeader() + "Loading my R library.");
		// Setting the name of the my R library
		String file = FileUtils.readFile(baseFolder
				+ "/resources/R/RClassifier.R");

		// Setting the name of the file where corpus is stored in R
		c.assign("corpusFile", baseFolder + "/resources/data/leaf.csv");

		System.out.println("Corpus File Name stated");
		// System.out.println(c.eval("corpusFile").asString());

		c.eval(file);

		Logging.info(getHeader() + "My R library loaded.");
	}

	/**
	 * Method that invokes the function that loads corpus data
	 * 
	 * @throws RserveException
	 * @throws REXPMismatchException
	 */
	public void loadData() throws RserveException, REXPMismatchException {
		Logging.info(getHeader() + "Loading corpus data.");

		c.eval("corpus <- loadCorpus(corpusFile)");

		Logging.info(getHeader() + "Corpus data loaded.");
	}

	/**
	 * Method that trains the Naive Bayes classifier.
	 * 
	 * @throws RserveException
	 * @throws REXPMismatchException
	 */
	public void trainClassifier() throws RserveException, REXPMismatchException {
		Logging.info(getHeader() + "Training classifier.");

		c.eval("trained <- trainClassifier(corpus)");

		Logging.info(getHeader() + "Classifier trained.");
	}

	/**
	 * Method that classifies a new entry.
	 * 
	 * @throws RserveException
	 * @throws REXPMismatchException
	 */
	public void classifyNewEntry() throws RserveException,
			REXPMismatchException {
		Logging.info(getHeader() + "Classify a new entry.");

		REXP x = c.eval("testCorpus <- loadCorpus(\"" + baseFolder
				+ "/resources/data/test.csv\")");

		x = c.eval("dim(testCorpus)");
		
		REXP y = c.eval("dim(corpus)");
		
		if(Integer.parseInt(x.asStrings()[1]) > (Integer.parseInt(y.asStrings()[1]) - 1)) {
			System.err.println("The test corpus has more columns than the training set.");
			throw new RserveException(c,"The test corpus has more columns than the training set.");
		}
		
		/**
		 * As it is stated at the naiveBayes help, note that the column names
		 * of ‘newdata’ are matched against the training data ones.".
		 * 
		 * Thus, we have to preserve the names.
		 */
		c.eval("colnames(testCorpus) <- c('V2','V3','V4','V5','V6','V7','V8','V9','V10','V11','V12','V13','V14','V15','V16')");
		x = c.eval("classify(trained,testCorpus[,])");

		for (int factor = 0; factor < x.asFactor().size(); factor++) {
			Logging.info("The prediction for the entry #" + factor + " is "
					+ x.asFactor().at(factor));
		}

		Logging.info(getHeader() + "New entry classified.");
	}

	/**
	 * Method that generates the header for logging messages
	 * 
	 * @return Header.
	 */
	private String getHeader() {
		String sDC = "";

		return "[UniversalDX] ["
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "] " + sDC;
	}

	/**
	 * Method that launches the training and classification tasks.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RClassifier rc = null;

		/**
		 * We establish the connection.
		 */
		try {
			rc = new RClassifier();
		} catch (RserveException e) {
			System.err.println("The connection hasn't been stablished.");
			System.exit(-1);
		} catch (REXPMismatchException e) {
			System.err.println("The connection hasn't been stablished.");
			System.exit(-1);
		}

		/**
		 * We load the corpus into the environment.
		 */
		try {
			rc.loadData();
		} catch (RserveException e) {
			System.err.println("The corpus data hasn't been loaded.");
			System.exit(-1);
		} catch (REXPMismatchException e) {
			System.err.println("The corpus data hasn't been loaded.");
			System.exit(-1);
		}

		/**
		 * We train the Naive Bayes classifier.
		 */
		try {
			rc.trainClassifier();
		} catch (RserveException e) {
			System.err.println("The classifier hasn't been trained.");
			System.exit(-1);
		} catch (REXPMismatchException e) {
			System.err.println("The classifier hasn't been trained.");
			System.exit(-1);
		}

		/**
		 * We make a prediction about the a new set of candidates.
		 */
		try {
			rc.classifyNewEntry();
		} catch (RserveException e) {
			System.err.println("The prediction hasn't been done.");
			System.exit(-1);
		} catch (REXPMismatchException e) {
			System.err.println("The prediction hasn't been done.");
			System.exit(-1);
		}
	}
}