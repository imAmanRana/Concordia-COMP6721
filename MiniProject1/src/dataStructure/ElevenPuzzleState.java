/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import util.Constants;
import util.Utils;

/**
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public class ElevenPuzzleState {

	private ElevenPuzzleState parent;
	int[] currentState;
	private int gCost;
	private int hCost;
	private int fCost;
	private char move;
	private int ordinalValue;

	public ElevenPuzzleState(int[] currentState) {
		this.currentState = currentState;
		setMove('0');
		setOrdinalValue(-1);
	}

	public ElevenPuzzleState(int[] currentState, int move, int ordinal, ElevenPuzzleState parent) {
		this.currentState = currentState;
		this.setMove((char) move);
		this.ordinalValue = ordinal;
		this.setParent(parent);
	}

	/**
	 * @return the currentState
	 */
	public int[] getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(int[] currentState) {
		this.currentState = currentState;
	}

	/**
	 * @return the parent
	 */
	public ElevenPuzzleState getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ElevenPuzzleState parent) {
		this.parent = parent;
	}

	/**
	 * @return the gCost
	 */
	public int get_G_Cost() {
		return gCost;
	}

	/**
	 * @param gCost the gCost to set
	 */
	public void set_G_Cost(int gCost) {
		this.gCost = gCost;
	}

	/**
	 * @return the hCost
	 */
	public int get_H_Cost() {
		return hCost;
	}

	/**
	 * @param hCost the hCost to set
	 */
	public void set_H_Cost(int hCost) {
		this.hCost = hCost;
	}

	/**
	 * @return the fCost
	 */
	public int get_F_Cost() {
		return fCost;
	}

	/**
	 * @param fCost the fCost to set
	 */
	public void set_F_Cost(int fCost) {
		this.fCost = fCost;
	}

	/**
	 * @return the move
	 */
	public char getMove() {
		return move;
	}

	/**
	 * @param move the move to set
	 */
	public void setMove(char move) {
		this.move = move;
	}

	/**
	 * @return the ordinalValue
	 */
	public int getOrdinalValue() {
		return ordinalValue;
	}

	/**
	 * @param ordinalValue the ordinalValue to set
	 */
	public void setOrdinalValue(int ordinalValue) {
		this.ordinalValue = ordinalValue;
	}

	public List<ElevenPuzzleState> generateSuccessors(Heuristic heuristic) {
		List<ElevenPuzzleState> successors = generateSuccessors();
		if (heuristic != null) {
			successors.stream().forEach(state -> state.updateCost(this.get_G_Cost(), heuristic));
		}
		return successors;
	}

	private void updateCost(int gCost, Heuristic heuristic) {

		set_H_Cost(findHeuristicCost(heuristic));
		this.fCost = this.gCost + this.hCost;
	}

	private int unitCost() {
		return 1;
	}

	private int findHeuristicCost(Heuristic heuristic) {
		int heuristicCost = 0;
		switch (heuristic) {

		case HAMMING_DISTANCE:
			heuristicCost = calculateHammingDistance();
			break;
		case MODIFIED_MANHATTAN:
			heuristicCost = calculateManhattanDistace();
			break;
		case PERMUTATION_INVERSION:
			heuristicCost = calculatePermutationInversionDistance();
			break;
		case SUPER_IMPOSE:
			heuristicCost = calculateSuperImposeValue();
			break;
		case WEIGHTED_MANHATTAN:
			heuristicCost = calculateWeightedManhattan();
			break;
		}

		return heuristicCost;
	}

	private int calculateWeightedManhattan() {
		int value, requiredX, requiredY, manhattanDistance = 0;
		int xShift, yShift, move, weight;

		for (int currentX = 0; currentX < Constants.ROWS; currentX++) {
			for (int currentY = 0; currentY < Constants.COLUMNS; currentY++) {
				value = currentState[currentX * Constants.COLUMNS + currentY];

				if (value != 0) {
					requiredX = (value - 1) / Constants.COLUMNS;
					requiredY = (value - 1) % Constants.COLUMNS;

					xShift = Math.abs(requiredX - currentX);
					yShift = Math.abs(requiredY - currentY);
					move = (xShift >= yShift ? xShift : yShift); // find the max among X & Y shifts

					if (move == 3) {
						weight = 5 * move; // 50% * 10
					} else if (move == 2) {
						weight = 3 * move; // 30% * 10
					} else if (move == 1) {
						weight = 2 * move; // 20% * 10
					} else {
						weight = 0; // 0% * 10
					}
					manhattanDistance += weight;
				}

			}
		}
		return manhattanDistance;
	}

	private int calculateSuperImposeValue() {

		int value = 0;

		for (int i = 0; i < Constants.PUZZLE_SIZE; i++) {
			value += Math.abs(Constants.GOAL_STATE[i] - currentState[i]);
		}

		return value;
	}

	private int calculateHammingDistance() {
		int distance = 0;

		for (int i = 0; i < Constants.PUZZLE_SIZE; i++) {
			if (currentState[i] != 0 && currentState[i] != i + 1)
				distance++;
		}
		return distance;
	}

	private int calculatePermutationInversionDistance() {

		int count;
		int distance = 0;
		for (int i = 0; i < Constants.PUZZLE_SIZE; i++) {
			count = 0;
			for (int j = i + 1; j < Constants.PUZZLE_SIZE; j++) {
				if (currentState[i] != 0 && currentState[j] != 0 && currentState[i] > currentState[j]) {
					count++;
				}
			}

			distance += count;
		}

		return distance;

		/*
		 * return (int)IntStream.range(0, Constants.PUZZLE_SIZE) .mapToLong(i ->
		 * IntStream.range(i + 1, Constants.PUZZLE_SIZE) .filter(element -> (element !=
		 * 0 && currentState[i] != 0 && element < currentState[i])).count()).sum();
		 */
	}

	private int calculateManhattanDistace() {
		int value, requiredX, requiredY, manhattanDistance = 0;
		int xShift, yShift;

		for (int currentX = 0; currentX < Constants.ROWS; currentX++) {
			for (int currentY = 0; currentY < Constants.COLUMNS; currentY++) {
				value = currentState[currentX * Constants.COLUMNS + currentY];

				if (value != 0) {
					requiredX = (value - 1) / Constants.COLUMNS;
					requiredY = (value - 1) % Constants.COLUMNS;

					xShift = Math.abs(requiredX - currentX);
					yShift = Math.abs(requiredY - currentY);

					manhattanDistance += (xShift >= yShift ? xShift : yShift); // find the max among X & Y shifts

				}

			}
		}
		return manhattanDistance;
	}

	public List<ElevenPuzzleState> generateSuccessors() {

		List<ElevenPuzzleState> successors = new ArrayList<>();

		int emptyTilePosition = locateTile(0);

		/*
		 * CODE FOR DFS - 1 2 3 0 5 6 7 4 9 10 11 8 if(Utils.canMove(Move.DOWN,
		 * emptyTilePosition)) { successors.add(generateNewState(Move.DOWN,
		 * emptyTilePosition)); } for (Move mv : Move.values()) { if (mv!=Move.DOWN &&
		 * Utils.canMove(mv, emptyTilePosition)) { successors.add(generateNewState(mv,
		 * emptyTilePosition)); } }
		 */

		for (Move mv : Move.values()) {
			if (Utils.canMove(mv, emptyTilePosition)) {
				successors.add(generateNewState(mv, emptyTilePosition));
			}
		}

		return successors;
	}

	private ElevenPuzzleState generateNewState(Move mv, int emptyTilePosition) {

		ElevenPuzzleState newState = new ElevenPuzzleState(
				swapPositions(Arrays.copyOf(currentState, currentState.length), emptyTilePosition,
						emptyTilePosition + mv.getPositionShift()),
				97 + emptyTilePosition + mv.getPositionShift(), mv.ordinal(), this);
		newState.set_G_Cost(this.gCost + unitCost());
		return newState;
	}

	private int[] swapPositions(int[] array, int oldPosition, int newPosition) {
		array[oldPosition] = array[newPosition];
		array[newPosition] = 0;
		return array;
	}

	private int locateTile(int value) {
		return IntStream.range(0, Constants.PUZZLE_SIZE).filter(i -> value == currentState[i]).findFirst().orElse(-1);
	}

	public boolean isGoalState() {

		return Arrays.equals(currentState, Constants.GOAL_STATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(currentState);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElevenPuzzleState other = (ElevenPuzzleState) obj;
		if (!Arrays.equals(currentState, other.currentState))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(currentState).forEach(a -> sb.append(a).append(" "));
		return sb.toString();
	}

	public String printState() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d", currentState[0]) + " | " + String.format("%2d", currentState[1]) + " | "
				+ String.format("%2d", currentState[2]) + " | " + String.format("%2d", currentState[3]) + " | ");
		sb.append("\n");

		sb.append(String.format("%2d", currentState[4]) + " | " + String.format("%2d", currentState[5]) + " | "
				+ String.format("%2d", currentState[6]) + " | " + String.format("%2d", currentState[7]) + " | ");
		sb.append("\n");

		sb.append(String.format("%2d", currentState[8]) + " | " + String.format("%2d", currentState[9]) + " | "
				+ String.format("%2d", currentState[10]) + " | " + String.format("%2d", currentState[11]) + " | ");

		return sb.toString();
	}

}
