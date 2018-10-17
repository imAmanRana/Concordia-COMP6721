/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package searchAlgorithms;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import dataStructure.ElevenPuzzleState;
import util.Utils;

/**
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public class DFS {

	static final String name = "puzzleDFS";
	static Stack<ElevenPuzzleState> closedStates = new Stack<>();
	static Stack<ElevenPuzzleState> openStates = new Stack<>();
	static Stack<ElevenPuzzleState> solutionPath = new Stack<>();

	public static void performDFS(ElevenPuzzleState ep) {

		openStates.push(ep);
		System.out.println("\nPerforming DFS...\n");
		Instant start = Instant.now();
		exploreStates();
		Instant end = Instant.now();
		System.out.println("Time taken: " + Duration.between(start, end).toMillis() + " milliseconds");

	}

	private static void exploreStates() {
		boolean solutionExists = false;
		while (!openStates.isEmpty()) {
			ElevenPuzzleState state = openStates.pop();
			closedStates.push(state);
			if (state.isGoalState()) {
				System.out.println("GOAL STATE REACHED.");
				System.out.println("State Explored : " + (closedStates.size()));
				solutionPath.push(state);
				while (state.getParent() != null) {
					state = state.getParent();
					solutionPath.push(state);
				}
				System.out.println("Solution Path Size : " + solutionPath.size());
				Utils.writeToFile(solutionPath, name + ".txt");
				System.out.println("For complete trace of solution path,see : " + name + ".txt");
				solutionExists = true;
				break;
			} else {
				List<ElevenPuzzleState> successors = state.generateSuccessors();
				Collections.reverse(successors);
				successors.stream().filter(s -> !checkIfVisited(s)).forEach(s -> openStates.push(s));
			}
		}
		if (!solutionExists) {
			System.out.println(
					"Solution for the given state doesn't exists.Explored " + closedStates.size() + " states.");
		}
	}

	private static boolean checkIfVisited(ElevenPuzzleState s) {
		return closedStates.contains(s);
	}

}
