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
public class TestBigram extends Test {

	private static TestBigram instance;

	/**
	 * @param enModel
	 * @param frModel
	 * @param esModel
	 */
	public TestBigram(Map<String, Double> enModel, Map<String, Double> frModel, Map<String, Double> esModel,
			Map<String, Long> enModelCount, Map<String, Long> frModelCount, Map<String, Long> esModelCount) {
		super(enModel, frModel, esModel, enModelCount, frModelCount, esModelCount);
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
		String language = null;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
			writer.write("----------------\n");
			writer.write("BIGRAM MODEL:\n");
			String s;
			for (int i = 0; i < filteredInput.length() - 1; i++) {
				s = filteredInput.substring(i, i + 2);
				writer.write("\nBIGRAM: " + s + "\n");

				s = Utils.storeRepresentation(s, Constants.BIGRAM_VALUE);
				// French
				prob = frModel.get(s) != null ? frModel.get(s) : findProbabilityForZeroOccurrence(frModelCount, s);
				frProb += Math.log10(prob);
				writer.write(String.format(Constants.TRACE_FORMAT, "FRENCH", s, prob, frProb));

				// English
				prob = enModel.get(s) != null ? enModel.get(s) : findProbabilityForZeroOccurrence(enModelCount, s);
				enProb += Math.log10(prob);

				writer.write(String.format(Constants.TRACE_FORMAT, "ENGLISH", s, prob, enProb));

				// Spanish
				prob = esModel.get(s) != null ? esModel.get(s) : findProbabilityForZeroOccurrence(esModelCount, s);
				esProb += Math.log10(prob);

				writer.write(String.format(Constants.TRACE_FORMAT, "OTHER", s, prob, esProb));

			}
			language = (frProb > enProb) ? (frProb > esProb) ? "French" : "Other"
					: (enProb > esProb ? "English" : "Other");
			writer.write(String.format(Constants.BIGRAM_DETECTION, language));

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
		long total = Utils.countTotalOccurencesForBigram(modelCount, s.charAt(1));
		return Utils.probability(0, total);
	}

}
