/*
 * COMP6271 - Introduction to Artificial Intelligence | Fall2018
 * Mini Project 3
 * Professor - Leila Kosseim
 * Automatic Language Identification Model
 */

package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import utils.Constants;
import utils.Utils;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">Profile</a>
 */
public class TestUnigram extends Test {

	private static TestUnigram instance;

	/**
	 * @param enModel
	 * @param frModel
	 * @param esModel
	 */
	public TestUnigram(Map<String, Double> enModel, Map<String, Double> frModel, Map<String, Double> esModel,Map<String, Long> enModelCount, Map<String, Long> frModelCount, Map<String, Long> esModelCount) {
		super(enModel, frModel, esModel,enModelCount, frModelCount, esModelCount);
		instance = this;
	}

	public static Test getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.Test#test(java.lang.String, int, double)
	 */
	@Override
	public String test(String originalInput, String filteredInput, int vocabularySize, double delta, File output) {

		double enProb = 0, frProb = 0, esProb = 0, prob;
		String language=null;
		double fr_probForZeroOccurrences = findProbabilityForZeroOccurrence(frModelCount,Constants.EMPTY_STRING);
		double en_probForZeroOccurrences = findProbabilityForZeroOccurrence(enModelCount,Constants.EMPTY_STRING);
		double es_probForZeroOccurrences = findProbabilityForZeroOccurrence(esModelCount,Constants.EMPTY_STRING);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {

			writer.write(originalInput + "\n");
			writer.write("UNIGRAM MODEL:\n");

			for (String s : filteredInput.split("")) {
				writer.write("\nUNIGRAM: " + s + "\n");

				s = Utils.storeRepresentation(s, Constants.UNIGRAM_VALUE);
				// French
				prob = frModel.get(s) != null ? frModel.get(s) : fr_probForZeroOccurrences;
				frProb += Math.log10(prob);
				writer.write(String.format(Constants.TRACE_FORMAT, "FRENCH", s, prob, frProb));

				// English
				prob = enModel.get(s) != null ? enModel.get(s) : en_probForZeroOccurrences;
				enProb += Math.log10(prob);

				writer.write(String.format(Constants.TRACE_FORMAT, "ENGLISH", s, prob, enProb));

				// Spanish
				prob = esModel.get(s) != null ? esModel.get(s) : es_probForZeroOccurrences;
				esProb += Math.log10(prob);

				writer.write(String.format(Constants.TRACE_FORMAT, "OTHER", s, prob, esProb));

			}
			language = (frProb > enProb) ? (frProb > esProb) ? "French" : "Other"
					: (enProb > esProb ? "English" : "Other");
			writer.write(String.format(Constants.UNIGRAM_DETECTION, language));
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return language;
	}
	
	/**
	 * @param s
	 * @param frModelCount
	 * @return
	 */
	public double findProbabilityForZeroOccurrence(Map<String, Long> modelCount, String s) {
		long total = Utils.countTotalElements(modelCount);
		return Utils.probability(0, total);
	}

}
