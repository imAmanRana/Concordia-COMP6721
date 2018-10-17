/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package comp6721;

import java.util.Scanner;

import dataStructure.ElevenPuzzleState;
import dataStructure.Heuristic;
import searchAlgorithms.AStar;
import searchAlgorithms.BFS;
import searchAlgorithms.DFS;
import util.Constants;

/**
 * Solution for the 11d Puzzle
 * 
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public class Solution {

	static Scanner sc = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("11d PUZZLE SOLVER");
		System.out.print("Please enter the current state : ");

		String input = sc.nextLine();

		int[] currentState = getCurrentState(input);

		ElevenPuzzleState ep = new ElevenPuzzleState(currentState);

		int algo = -1;
		Heuristic heuristic;
		boolean search=false;
		do {
			algo = displayAlgoSelection();

			switch (algo) {
			case 1:
				DFS.performDFS(ep);
				search=true;
				break;
			case 2:
				heuristic = selectHeuristic();
				BFS.performBFS(ep, heuristic);
				search=true;
				break;
			case 3:
				heuristic = selectHeuristic();
				AStar.performAStar(ep, heuristic);
				search=true;
				break;
			case 4:
				System.out.println("Have a Nice Day!");
				break;
			default:
				System.out.println("Invalid Choice. Please try again.");
				break;
			}

		} while (search!=true && algo != 4);

	}

	/**
	 * User selects the Heuristic
	 * 
	 * @return
	 */
	private static Heuristic selectHeuristic() {
		int value = -1;
		Heuristic hr = null;
		do {
			value = displayHeuristicSelection();

			if (value > 0 && value <= Heuristic.values.length) {
				hr = Heuristic.values[value - 1];
			} else if (value == 0) {
				System.out.println("Have a Nice Day!");
				System.exit(0);
			} else {
				System.out.println("Invalid Choice. Please try again.");
			}

		} while (value < 0 || value > Heuristic.values.length);
		return hr;
	}

	private static int displayHeuristicSelection() {
		System.out.println("\nSelect your Heuristic");
		for (Heuristic hr : Heuristic.values()) {
			System.out.println((hr.ordinal() + 1) + ". " + hr);
		}
		System.out.println("0. Quit Selection");
		System.out.print("Your Choice : ");
		return sc.nextInt();
	}

	private static int displayAlgoSelection() {
		System.out.println("\nSelect your Algorithm");
		System.out.println("1. Depth First Search");
		System.out.println("2. Best First Search");
		System.out.println("3. A* Algorithm");
		System.out.println("4. Quit Application");
		System.out.print("Your Choice : ");
		return sc.nextInt();
	}

	private static int[] getCurrentState(String input) {

		String[] values = input.split(Constants.STRING_SPACE);
		int[] currentState = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			currentState[i] = Integer.parseInt(values[i].trim());
		}
		return currentState;
	}

}
