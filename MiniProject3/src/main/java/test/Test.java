/*
 * COMP6271 - Introduction to Artificial Intelligence | Fall2018
 * Mini Project 3
 * Professor - Leila Kosseim
 * Automatic Language Identification Model
 */

package test;

import java.io.File;
import java.util.Map;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">Profile</a>
 */
public abstract class Test {

	protected Map<String, Double> enModel;
	protected Map<String, Double> frModel;
	protected Map<String, Double> esModel;

	protected Map<String, Long> enModelCount;
	protected Map<String, Long> frModelCount;
	protected Map<String, Long> esModelCount;

	protected Test(Map<String, Double> enModel, Map<String, Double> frModel, Map<String, Double> esModel,
			Map<String, Long> enModelCount, Map<String, Long> frModelCount, Map<String, Long> esModelCount) {
		this.enModel = enModel;
		this.frModel = frModel;
		this.esModel = esModel;
		this.enModelCount = enModelCount;
		this.frModelCount = frModelCount;
		this.esModelCount = esModelCount;
	}

	public abstract String test(String originalInput, String filteredInput, int vocabularySize, double delta,
			File output);

	public abstract double findProbabilityForZeroOccurrence(Map<String, Long> modelCount, String key);

}
