/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package searchAlgorithms;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import dataStructure.ElevenPuzzleState;
import dataStructure.Heuristic;
import util.Constants;
import util.Utils;

/**
 * ALGORITHM A*
 * 
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public class AStar {

	public static final String name = "puzzleAs";
	private static Heuristic heuristic;
	static Stack<ElevenPuzzleState> closedStates = new Stack<>();
	static Queue<ElevenPuzzleState> openStates = new PriorityQueue<>(Comparator
			.comparingInt(ElevenPuzzleState::get_F_Cost).thenComparingInt(ElevenPuzzleState::getOrdinalValue));
	static Stack<ElevenPuzzleState> solutionPath = new Stack<>();

	public static void performAStar(ElevenPuzzleState ep, Heuristic heuristic) {
		// add the root node
		openStates.add(ep);
		AStar.heuristic = heuristic;
		System.out.println("\nPerforming A* with " + heuristic + " heuristic\n");
		Instant start = Instant.now();
		exploreStates();
		Instant end = Instant.now();
		System.out.println("Time taken: " + Duration.between(start, end).toMillis() + " milliseconds");
	}

	private static void exploreStates() {
		boolean solutionExists = false;
		while (!openStates.isEmpty()) {
			ElevenPuzzleState state = openStates.remove();
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
				Utils.writeToFile(solutionPath, name + Constants.HYPEN + heuristic.name + ".txt");
				System.out.println("For complete trace of solution path,see : " + name + Constants.HYPEN
						+ heuristic.name + ".txt");
				solutionExists = true;
				break;
			} else {
				List<ElevenPuzzleState> successors = state.generateSuccessors(heuristic);
				successors.stream().filter(s -> !checkIfVisited(s)).forEach(s -> openStates.add(s));
			}

		}
		if (!solutionExists) {
			System.out.println(
					"Solution for the given state doesn't exists.Explored " + closedStates.size() + " states.");
		}

	}

	private static boolean checkIfVisited(ElevenPuzzleState s) {
		if (closedStates.contains(s))
			return true;
		else
			return false;
	}

}
