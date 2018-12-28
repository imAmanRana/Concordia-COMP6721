/*
 * COMP6271 - Introduction to Artificial Intelligence | Fall2018
 * Mini Project 3
 * Professor - Leila Kosseim
 * Automatic Language Identification Model
 */

package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Amandeep Singh
 * @see <a href="https://www.linkedin.com/in/imamanrana/" target=
 *      "_blank">Profile</a>
 */
public class Utils {

	private static final List<Character> escapeCharacters = Arrays.asList(new Character[] { '\\', '.', '[', ']', '{',
			'}', '(', ')', '<', '>', '*', '+', '-', '=', '?', '^', '$', '|' });

	public static void writeProbabilityMapToFile(Map<String, Long> map, int Ngram, File file) {
		try {
			Properties p = new Properties();
			p.putAll(calculateProbabilities(map, Ngram));
			p.store(new FileOutputStream(file), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param map
	 * @param file
	 */
	public static void writeCountMapToFile(Map<String, Long> map, File file, int nGram) {
		try {
			Properties p = new Properties();
			p.putAll(longToStringMap(map, nGram));
			p.store(new FileOutputStream(file), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param map
	 * @return
	 */
	private static Map<String, String> longToStringMap(Map<String, Long> map, int nGram) {
		Map<String, String> stringMap = new HashMap<>();
		map.entrySet().stream().forEach(
				entry -> stringMap.put(storeRepresentation(entry.getKey(), nGram), entry.getValue().toString()));
		return stringMap;

	}

	/**
	 * @param map
	 * @param ngram
	 * @return
	 */
	public static Map<String, String> calculateProbabilities(Map<String, Long> map, int ngram) {

		long total = 0;
		if (ngram == Constants.UNIGRAM_VALUE)
			total = countTotalElements(map);

		Map<String, String> probabilityMap = new HashMap<>();
		String representation;
		for (String key : map.keySet()) {
			representation = storeRepresentation(key, ngram);
			if (ngram == Constants.BIGRAM_VALUE) {
				total = countTotalOccurencesForBigram(map, representation.charAt(3));
			}
			probabilityMap.put(representation, Double.valueOf(probability(map.get(key), total)).toString());
		}
		return probabilityMap;
	}

	/**
	 * @param key
	 * @return
	 */
	public static String storeRepresentation(String key, int nGram) {
		StringBuilder sb = new StringBuilder(key);
		if (nGram == Constants.UNIGRAM_VALUE) {
			sb.insert(0, '(');
			sb.insert(sb.length(), ')');
		} else {
			sb.reverse();
			sb.insert(0, '(');
			sb.insert(2, '|');
			sb.insert(sb.length(), ')');
		}

		return sb.toString();
	}

	/**
	 * @param key
	 * @return
	 */
	public static long countTotalOccurencesForBigram(Map<String, Long> model, char c) {
		return model.entrySet().stream().filter(entry -> (entry.getKey().charAt(0) == c)).mapToLong(e -> e.getValue())
				.sum();
	}

	public static double probability(double occurrences, double total) {
		return (occurrences + Constants.DELTA) / (total + Constants.VOCABULARY_SIZE * Constants.DELTA);
	}

	/**
	 * @param map
	 * @return
	 */
	public static long countTotalElements(Map<String, Long> map) {
		return map.entrySet().stream().parallel().mapToLong(e -> e.getValue()).sum();
	}

	public static Map<String, Double> readMapFromFile(File file) {

		Properties prop = null;
		Map<String, Double> map = new HashMap<>();
		try {
			prop = new Properties();
			prop.load(new FileInputStream(file));
			prop.forEach((k, v) -> map.put(k.toString(), Double.parseDouble(v.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Long> readCountMapFromFile(File file) {

		Properties prop = null;
		Map<String, Long> map = new HashMap<>();
		try {
			prop = new Properties();
			prop.load(new FileInputStream(file));
			prop.forEach((k, v) -> map.put(k.toString(), Long.parseLong(v.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @param file
	 * @return
	 */
	public static File checkThenCreateFile(String directory, String file) {

		File files = new File(directory);
		if (!files.exists())
			files.mkdirs();
		files = new File(directory + file);
		if (!files.exists()) {
			try {
				files.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return files;
	}

	/**
	 * @param sentences
	 * @return
	 */
	public static List<String> readFile(File sentences) {
		try {
			return Files.readAllLines(sentences.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDirectory(boolean isBasic) {
		return isBasic ? Constants.BASIC : Constants.ADVANCE;
	}

	public static boolean getFilter(int i, boolean isBasic) {
		if (isBasic) {
			// only letters
			return (65 <= i && i <= 90) || (97 <= i && i <= 122);
		} else {
			// advanced system character set
			/*
			 * HORIZONTAL TABULATION NEW LINE FORM FEED CARRIAGE RETURN SPACE ’ any
			 * character with ASCII value in range [33,126]
			 * 
			 * @ http://www.asciitable.com/
			 */
			return Character.isSpace((char) i) || (33 <= i && i <= 126 || (char) i == '’');
		}
	}

	public static char performCharacterConversion(int c, boolean isBasic) {
		char new_c;
		if (isBasic) {
			new_c = Character.toLowerCase((char) c);
		} else {
			if (Character.isSpace((char) c)) {
				/*
				 * Converts following characters to space HORIZONTAL TABULATION '\n' U+000A NEW
				 * LINE '\f' U+000C FORM FEED '\r' U+000D CARRIAGE RETURN ' ' U+0020 SPACE
				 */
				new_c = ' ';
			} else if ('’' == (char) c) {
				new_c = '\'';
			} else {
				new_c = (char) c;
			}
		}

		return new_c;
	}

	/**
	 * @param biword
	 * @return
	 */
	public static String escapeBiword(String biword) {
		StringBuilder sb = new StringBuilder(biword);
		for (char c : escapeCharacters) {
			if (biword.charAt(0) == c) {
				sb.insert(0, "\\");
			}
			if (biword.charAt(1) == c) {
				sb.insert(sb.length() - 1, "\\");
			}
		}
		return sb.toString();
	}

}
