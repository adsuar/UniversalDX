package es.adsuar.universaldx;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
	// private static Properties properties;

	/**
	 * Class constructor
	 */
	public RClassifier() throws RserveException, REXPMismatchException {
		Logging.info(getHeader() + "Loading class.");

		// Later I'll use property files.
		/*
		 * try { properties = new Properties();
		 * properties.load(this.getClass().getClassLoader()
		 * .getResourceAsStream("./config/universaldx.properties")); } catch
		 * (Exception e) {
		 * Logging.info(" The file universaldx.properties doesn't exist.");
		 * e.printStackTrace(); System.exit(-1); }
		 */

		// Create the connection to the RServer
		c = new RConnection();

		// Get the R version
		REXP x = c.eval("R.version.string");
		System.out.println(x.asString());

		// I load my own R dependencies and functions
		try {
			loadLibrary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String file = FileUtils
				.readFile("/home/adsuar/workspace/trabajo/UniversalDX/resources/R/RClassifier.R");

		// Setting the name of the file where corpus is stored in R
		c.assign("corpusFile",
				"/home/adsuar/workspace/trabajo/UniversalDX/resources/data/leaf.csv");

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

		REXP x = c.eval("corpus <- loadCorpus(corpusFile)");

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

		REXP x = c.eval("trained <- trainClassifier(corpus)");

		Logging.info(getHeader() + "Classifier trained.");
	}

	/**
	 * Method that classifies a new entry.
	 * 
	 * @throws RserveException
	 * @throws REXPMismatchException
	 */
	public void classifyNewEntry(List<Double> objectToClassify)
			throws RserveException, REXPMismatchException {
		Logging.info(getHeader() + "Classify a new entry.");

		/*
		 * PrintWriter out = null;
		 * 
		 * try { out = new PrintWriter(
		 * "/home/adsuar/workspace/trabajo/UniversalDX/resources/data/test.csv"
		 * ); } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 * 
		 * out.println(RUtils.List2RCSV(objectToClassify));
		 * 
		 * out.close();
		 */

		REXP x = c
				.eval("testCorpus <- loadCorpus(\"/home/adsuar/workspace/trabajo/UniversalDX/resources/data/test.csv\")");

		x = c.eval("prediction <- classify(trained,testCorpus[,-1])");
		x = c.eval("prediction");

		for (int factor = 0; factor < x.asFactor().size(); factor++) {
			System.out.println("LA PREDICCIÓN PARA LA ENTRADA " + factor
					+ " ES " + x.asFactor().at(factor));
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
		// TODO Auto-generated method stub

		RClassifier rc = null;
		try {
			rc = new RClassifier();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("The connection hasn't been stablished.");
			System.exit(-1);
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			System.err.println("The connection hasn't been stablished.");
			System.exit(-1);
		}

		try {
			rc.loadData();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rc.trainClassifier();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Double> objectToClassify = Arrays.asList(6.0, 0.58232, 1.343,
				0.29271, 0.95055, 0.99825, 0.71499, 0.026876, 0.13146,
				0.068134, 0.13204, 0.017136, 0.0036798, 0.0014697, 1.6315);

		try {
			rc.classifyNewEntry(objectToClassify);
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}