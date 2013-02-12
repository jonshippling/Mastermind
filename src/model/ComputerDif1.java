package model;

/**
 * Class for computer difficulty 1, AKA easy difficulty.  This computer AI
 * randomly guesses with no regards to the feedback given.
 * 
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */
public class ComputerDif1 extends Computer {
	
	/**
	 * Constructor for the easy difficulty computer player
	 */
	public ComputerDif1(){
		super();
	}

	/**
	 * Creates a random guess to the code
	 */
	@Override
	public Move guess() {
		return new Move(generateCode());
	}

	/**
	 * Returns "Computer Difficulty: Easy"
	 *
	 * @return the string "Computer Difficulty: Easy"
	 */
	 public String getName(){
		return "Computer Difficulty: Easy";
	}
	 
	
}
