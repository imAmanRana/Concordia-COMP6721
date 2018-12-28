/*
 * COMP6271 - Introduction to Artificial Intelligence | Fall2018
 * Mini Project 3
 * Professor - Leila Kosseim
 * Automatic Language Identification Model
 */

package comp6271;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import test.TestBigram;
import test.TestUnigram;
import utils.Constants;
import utils.Utils;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target="_blank">Profile</a>
 */
public class Main {

	Scanner sc = new Scanner(System.in);
	boolean isBasic;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("WELCOME TO THE AUTOMATIC LANGUAGE IDENTIFICATION SYSTEM");
		Main obj = new Main();
		int option=-1;
		System.out.println("Please choose your System : ");
		System.out.println("1 - Basic System");
		System.out.println("2 - Experiment System");
		System.out.println("3 - Exit");
		System.out.print("Choice : ");
		option = obj.sc.nextInt();
		switch(option) {
			case 1:
				obj.basicSystem();
			break;
			case 2:
				obj.advancedSystem();
			break;
			case 3:
				System.out.println("GOOD BYE!");
			break;
			default:
				System.out.println("Not a valid option.");
			break;
		}
	}
	
	/**
	 * 
	 */
	private void basicSystem() {
		int option = -1;
		isBasic = true;
		Constants.VOCABULARY_SIZE = 26;
		while(option!=3) {
			System.out.println("\n\nBASIC SETUP");
			option = provideOptions();
			switch (option) {
			case 1:
				trainSystem();
				break;
			case 2:
				testSystem();
				break;
			case 3:
				System.out.println("GOOD BYE!");
				break;
			default:
				System.out.println("Please input a valid option.");
				break;
			}
		}

	}
	
	/**
	 * 
	 */
	private void advancedSystem() {
		int option = -1;
		isBasic = false;
		Constants.VOCABULARY_SIZE = 95;
		while (option != 3) {
			System.out.println("\n\nADVANCED SETUP");
			option = provideOptions();
			switch (option) {

			case 1:
				trainSystem();
				break;
			case 2:
				testSystem();
				break;
			case 3:
				System.out.println("GOOD BYE!");
				break;
			default:
				System.out.println("Please input a valid option.");
				break;
			}
		}

	}

	/**
	 * 
	 */
	private void trainSystem() {
		
		String input = null;
		if(checkIfTheSystemIsAlreadyTrained()) {
			System.out.print("The System looks already trained. Do you want to train it again?(y|n): ");
			input = sc.next();
			if(!input.trim().equalsIgnoreCase("Y"))
				return;
		}
		
		Map<String,Long> book;
		
		//Unigram Training
		System.out.println("Training Unigram for English Books");
		File modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_EN);
		File countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_EN_COUNT);
		book = carefulMerge(trainForUnigram( Constants.EN_DIRECTORY, Constants.EN_BOOK1),trainForUnigram(Constants.EN_DIRECTORY, Constants.EN_BOOK2));

		Utils.writeCountMapToFile(book,countFile,Constants.UNIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.UNIGRAM_VALUE,modelFile);
		
		System.out.println("Training Unigram for French Books");
		modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_FR);
		countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_FR_COUNT);
		book = carefulMerge(trainForUnigram(Constants.FR_DIRECTORY,Constants.FR_BOOK1), trainForUnigram(Constants.FR_DIRECTORY,Constants.FR_BOOK2));
		Utils.writeCountMapToFile(book,countFile,Constants.UNIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.UNIGRAM_VALUE,modelFile);
		
		System.out.println("Training Unigram for Spanish Books");
		modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_OT);
		countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.UNIGRAM_OT_COUNT);
		book = carefulMerge(trainForUnigram(Constants.ES_DIRECTORY,Constants.ES_BOOK1), trainForUnigram(Constants.ES_DIRECTORY,Constants.ES_BOOK2));
		Utils.writeCountMapToFile(book,countFile,Constants.UNIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.UNIGRAM_VALUE,modelFile);
		
		
		//Bigram Training
		System.out.println("Training Bigram for English Books");
		modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_EN);
		countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_EN_COUNT);
		book = carefulMerge(trainForNgram(Constants.EN_DIRECTORY, Constants.EN_BOOK1,2), trainForNgram(Constants.EN_DIRECTORY, Constants.EN_BOOK2,2));
		Utils.writeCountMapToFile(book,countFile,Constants.BIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.BIGRAM_VALUE,modelFile);
		
		System.out.println("Training Bigram for French Books");
		modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_FR);
		countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_FR_COUNT);
		book = carefulMerge(trainForNgram(Constants.FR_DIRECTORY, Constants.FR_BOOK1,2), trainForNgram(Constants.FR_DIRECTORY, Constants.FR_BOOK2,2));
		Utils.writeCountMapToFile(book,countFile,Constants.BIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.BIGRAM_VALUE,modelFile);
		
		
		System.out.println("Training Bigram for Spanish Books");
		modelFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_OT);
		countFile = Utils.checkThenCreateFile(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic), Constants.BIGRAM_OT_COUNT);
		book = carefulMerge(trainForNgram(Constants.ES_DIRECTORY, Constants.ES_BOOK1,2), trainForNgram(Constants.ES_DIRECTORY, Constants.ES_BOOK2,2));
		Utils.writeCountMapToFile(book,countFile,Constants.BIGRAM_VALUE);
		Utils.writeProbabilityMapToFile( book,Constants.BIGRAM_VALUE,modelFile);
		
		System.out.println("TRAINING COMPLETED");
	}
	

	/**
	 * @return
	 */
	private int provideOptions() {
		System.out.println("1 - Train System");
		System.out.println("2 - Test System");
		System.out.println("3 - Exit");
		System.out.print("Choice : ");
		return sc.nextInt();
	}

	/**
	 * 
	 */
	private void testSystem() {
		
		Map<String,Double> enMap,frMap,esMap;
		Map<String,Long> enMapCount,frMapCount,esMapCount;
		
		if(!checkIfSentencesFileExist(Constants.TEST_FILE_DIRECTORY+Constants.TEST_FILE)) {
			System.out.println("The test file containing sentences does not exisit"+Constants.TEST_FILE_DIRECTORY+ Constants.TEST_FILE);
			return;
		}
			
		if(TestUnigram.getInstance()==null) {
			System.out.println("Caching saved unigram models from file. Won't take much time.");
			enMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic)+ Constants.UNIGRAM_EN));
			enMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.UNIGRAM_EN_COUNT));
			
			frMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic)+ Constants.UNIGRAM_FR));
			frMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.UNIGRAM_FR_COUNT));
			
			esMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic)+ Constants.UNIGRAM_OT));
			esMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.UNIGRAM_OT_COUNT));
			
			new TestUnigram(enMap,frMap,esMap,enMapCount,frMapCount,esMapCount);
		}
		
		
		
		if(TestBigram.getInstance()==null) {
			System.out.println("Caching saved bigram models from file. Won't take much time.");
			enMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_EN));
			enMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_EN_COUNT));
			
			frMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_FR));
			frMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_FR_COUNT));
			
			esMap = Utils.readMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_OT));
			esMapCount = Utils.readCountMapFromFile(new File(Constants.MODEL_DIRECTORY +Utils.getDirectory(isBasic) + Constants.BIGRAM_OT_COUNT));
			new TestBigram(enMap,frMap,esMap,enMapCount,frMapCount,esMapCount);
		}
		
		File sentences = new File(Constants.TEST_FILE_DIRECTORY+Constants.TEST_FILE);
		
		System.out.println("Reading sentences from file: "+sentences.getPath());
		List<String> sentenceList = Utils.readFile(sentences);
		
		String unigram,bigram;
			
		for(int i=0;i<sentenceList.size();i++) {
			System.out.println("\n"+(i+1)+". "+sentenceList.get(i));
			File output = Utils.checkThenCreateFile(Constants.OUTPUT_DIRECTORY+Utils.getDirectory(isBasic), String.format(Constants.OUTPUT_FILE, i+1));
			
			unigram = TestUnigram
						.getInstance()
						.test(sentenceList.get(i),
							filterInput(sentenceList.get(i)),
							Constants.VOCABULARY_SIZE,
							Constants.DELTA,output);
			
			bigram =  TestBigram
						.getInstance()
						.test(sentenceList.get(i),
							filterInput(sentenceList.get(i)),
							Constants.VOCABULARY_SIZE,
							Constants.DELTA,output);
			System.out.println("Unigram : "+unigram+"\t Bigram : "+bigram);
			//te.add(unigram+","+bigram+"\n");
		}
		
		//System.out.println(te);
		System.out.println("\nTESTING COMPLETE. Please see the output folder : "+Constants.OUTPUT_DIRECTORY+Utils.getDirectory(isBasic));
			
	}
	/**
	 * @param string
	 * @return
	 */
	private String filterInput(String string) {
		
		return string.chars()
				.parallel()
				.filter(i->Utils.getFilter(i, isBasic))
				.map(i-> Utils.performCharacterConversion(i,isBasic))
				.mapToObj(i->String.valueOf((char)i))
				.collect(Collectors.joining(""));
	}



	/**
	 * @param string
	 * @return
	 */
	private boolean checkIfSentencesFileExist(String filePath) {
		return (new File(filePath)).exists();
	}



	/**
	 * @param loadModel
	 * @param loadModel2
	 * @return
	 */
	private Map<String, Long> carefulMerge(Map<String, Long> model1, Map<String, Long>  model2) {
		
		model1.forEach((k,v) -> {
			if(model2.containsKey(k)) {
				model2.put(k, v+model2.get(k));
			}else {
				model2.put(k, v);
			}
		});
		
		return model2;
	}

	
	/**
	 * @return
	 */
	private boolean checkIfTheSystemIsAlreadyTrained() {
		File f = new File(Constants.MODEL_DIRECTORY+Utils.getDirectory(isBasic)+Constants.UNIGRAM_EN);
		return f.exists();
	}



	/**
	 * @param modeltoSave 
	 * @param enBook1
	 */
	private Map<String,Long> trainForNgram(String bookDirectory,String book, int n) {
		String s = filterBook(bookDirectory+book);
		Map<String,Long> bigram = new HashMap<>();;
		String biword,escapedBiword;
		for(int i=0;i<s.length()-n+1;i++) {
			biword = s.substring(i, i+n);
			escapedBiword = Utils.escapeBiword(biword);
			if(!bigram.containsKey(biword)) {
				bigram.put(biword, (long)(s.split(escapedBiword, -1).length-1));
			}
		}
		return bigram;
	}


	/**
	 * @param bookPath
	 * @return
	 */
	private String filterBook(String bookPath) {
		String filteredString=null;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(bookPath))){
			filteredString = 
					reader.lines()
					.parallel()
					.map(String::chars)
					.flatMapToInt(i->i)
					.filter(i->Utils.getFilter(i, isBasic))
					.map(i-> Utils.performCharacterConversion(i,isBasic))
					.mapToObj(i->String.valueOf((char)i))
					.collect(Collectors.joining(""));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filteredString;
	}



	/**
	 * @param bookPath 
	 * @param modelToSave 
	 * @param modelDirectory 
	 * 
	 */
	private Map<String, Long> trainForUnigram(String bookDirectory, String bookName) {
		Map<String, Long> charactersMap=null;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(bookDirectory+bookName))){
			charactersMap = 
					reader.lines()
					.parallel()
					.map(String::chars)
					.flatMapToInt(i->i)
					.filter(i->Utils.getFilter(i, isBasic))
					.map(i->Utils.performCharacterConversion(i,isBasic))
					.mapToObj(i->String.valueOf((char)i))
					.collect(Collectors.groupingByConcurrent(s -> s,Collectors.counting()));
					
					
		
					//System.out.println("concurrent words=" + words);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return charactersMap;
	}

}
