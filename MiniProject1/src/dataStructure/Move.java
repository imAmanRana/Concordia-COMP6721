/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.Constants;

/**
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public enum Move {

	UP(Arrays.asList(0, 1, 2, 3),-Constants.COLUMNS), 
	UP_RIGHT(Arrays.asList(0, 1, 2, 3, 7, 11),-Constants.ROWS), 
	RIGHT(Arrays.asList(3, 7, 11),Constants.ONE),
	DOWN_RIGHT(Arrays.asList(8, 9, 10, 11, 7, 3),Constants.COLUMNS+Constants.ONE), 
	DOWN(Arrays.asList(8, 9, 10, 11),Constants.COLUMNS),
	DOWN_LEFT(Arrays.asList(8, 9, 10, 11, 0, 4),Constants.ROWS), 
	LEFT(Arrays.asList(0, 4, 8),-Constants.ONE),
	UP_LEFT(Arrays.asList(0, 1, 2, 3, 4, 8),-(Constants.COLUMNS+Constants.ONE));

	List<Integer> invalidPositionsToMoveFrom = new ArrayList<>();
	
	int positionShift;
	
	
	private Move(List<Integer> invalidPositions,int positionShift) {
		this.invalidPositionsToMoveFrom = invalidPositions;
		this.positionShift = positionShift;
	}
	
	public List<Integer> getInvalidPositionsToMoveFrom(){
		return invalidPositionsToMoveFrom;
	}
	
	public int getPositionShift() {
		return positionShift;
	}
	
}
