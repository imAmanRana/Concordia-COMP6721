/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package dataStructure;

/**
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public enum Heuristic {

	HAMMING_DISTANCE("h1"), MODIFIED_MANHATTAN("h2"), PERMUTATION_INVERSION("h3"), SUPER_IMPOSE("h4"), WEIGHTED_MANHATTAN("h5");

	public static final Heuristic values[] = values();
	public String name;
	
	private Heuristic(String name) {
		this.name = name;
	}
}
