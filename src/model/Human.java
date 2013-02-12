package model;

/**
 * Class representing the human player in the game.  The Human can either be
 * a code maker or a code breaker.
 * 
 * @author Jonathon Shippling <jjs5471@rit.edu>
 *
 */


public class Human implements IPlayer{

	/**
	 * Constructor for the human class
	 *
	 */
	public Human(){
		
	}
	
	/**
	 * @return 
	 * 
	 */
	@Override
	public Color[] generateCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void generateResponse(Move guess) {

	}

	/**
	 * 
	 */
	@Override
	public Move guess() {

		return null;
	}
	
	/**
	 * Returns "Human"
	 *
	 * @return the string "Human"
	 */
	 public String getName(){
		return "Human";
	}

	@Override
	public boolean waitIncrement() {
		return false;
	}
	
	@Override
	public void setWaitTime(long time) {
	}

}
