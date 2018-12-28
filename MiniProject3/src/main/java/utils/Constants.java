/*
 * COMP6271 - Introduction to Artificial Intelligence | Fall2018
 * Mini Project 3
 * Professor - Leila Kosseim
 * Automatic Language Identification Model
 */

package utils;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target="_blank">Profile</a>
 */
public class Constants {
	
	//BOOKS
	public static final String EN_DIRECTORY = "./src/main/resources/TrainingCorpus/en/";
	public static final String EN_BOOK1 = "en-moby-dick.txt";
	public static final String EN_BOOK2 = "en-the-little-prince.txt";
	
	public static final String FR_DIRECTORY = "./src/main/resources/TrainingCorpus/fr/";
	public static final String FR_BOOK1 = "fr-vingt-mille-lieues-sous-les-mers.txt";
	public static final String FR_BOOK2 = "fr-le-petit-prince.txt";
	
	public static final String ES_DIRECTORY = "./src/main/resources/TrainingCorpus/es/";
	public static final String ES_BOOK1 = "es-don-quijote.txt";
	public static final String ES_BOOK2 = "es-niebla.txt";
	
	public static final String TEST_BOOK = "test.txt";
	
	
	//MODELS
	public static final String MODEL_DIRECTORY = "./src/main/resources/model/";
	public static final String BASIC = "basicSetup/";
	public static final String ADVANCE = "advanceSetup/";
	public static final String UNIGRAM_EN = "unigramEN.txt";
	public static final String UNIGRAM_FR = "unigramFR.txt";
	public static final String UNIGRAM_OT = "unigramOT.txt";
	public static final String BIGRAM_EN = "bigramEN.txt";
	public static final String BIGRAM_FR = "bigramFR.txt";
	public static final String BIGRAM_OT = "bigramOT.txt";
	public static final String UNIGRAM_EN_COUNT="unigramEN_count.txt";
	public static final String UNIGRAM_FR_COUNT="unigramFR_count.txt";
	public static final String UNIGRAM_OT_COUNT="unigramOT_count.txt";
	public static final String BIGRAM_EN_COUNT="bigramEN_count.txt";
	public static final String BIGRAM_FR_COUNT="bigramFR_count.txt";
	public static final String BIGRAM_OT_COUNT="bigramOT_count.txt";
	
	public static final String TEST_FILE_DIRECTORY = "./src/main/resources/test/";
	public static final String TEST_FILE = "sentences.txt";
	
	public static final String OUTPUT_DIRECTORY = "./src/main/resources/output/";
	public static final String OUTPUT_FILE = "out%s.txt";
	
	public static final String UNIGRAM = "unigram";
	public static final String BIGRAM = "bigram";
	public static final int UNIGRAM_VALUE = 1;
	public static final int BIGRAM_VALUE = 2;
	
	
	public static final String TRACE_FORMAT = "%s: P%s = %f ==> log prob of sentence so far: %f\n";
	public static final String UNIGRAM_DETECTION="\nAccording to the unigram model, the sentence is in %s\n";
	public static final String BIGRAM_DETECTION="\nAccording to the bigram model, the sentence is in %s";
	
	
	public static double DELTA = 0.5;
	public static int VOCABULARY_SIZE = 26;
	
	public static final String EMPTY_STRING = "";
	

}
