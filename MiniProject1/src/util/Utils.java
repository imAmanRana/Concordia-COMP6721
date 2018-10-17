/*
 * COMP6721 - Artificial Intelligence | Fall2018
 * Mini Project 1 
 * 11d-puzzle
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

import dataStructure.ElevenPuzzleState;
import dataStructure.Move;

/**
 * @author Amandeep Singh
 * @see <a href="https://github.com/imAmanRana/">Profile</a>
 *
 */
public class Utils {
	
	public static boolean canMove(Move mv, int position) {
		return !mv.getInvalidPositionsToMoveFrom().contains(position);
	}

	public static void writeToFile(Stack<ElevenPuzzleState> solutionPath, String fileName) {
		
		File files = new File(Constants.FILE_PATH);
        if (!files.exists()) 
            files.mkdirs(); 
        files = new File(Constants.FILE_PATH+fileName);
        if(!files.exists())
			try {
				files.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(files))){
			ElevenPuzzleState state;
			while(!solutionPath.isEmpty()) {
				state = solutionPath.pop();
				bw.write(state.getMove()+Constants.STRING_SPACE+Arrays.toString(state.getCurrentState())+System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
