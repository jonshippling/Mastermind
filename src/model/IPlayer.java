package model;

/**
 * An Interface for the players of the game Mastermind  
 * 
 * @author Jonathon Shippling <jjs5471@rit.edu>
 *
 */
public interface IPlayer {

	public boolean waitIncrement();
	public void setWaitTime(long amount);
	
	/**
	 * Generates a code to be guessed by the code breaker, 
	 * Is only used by human players
	 * 
	 * @return returns the color sequence selected
	 */
	public Color[] generateCode();
	
	/**
	 * Generates response to a guess made by the code breaker,
	 * Is only used by human players
	 * 
	 * @param guess created by the code breaker
	 * @return a move representing the number of pegs the breaker had correct
	 */
	public void generateResponse(Move guess);
	
	/**
	 * Creates a guess for the code
	 * Used by both humans and computers
	 * 
	 */
	public Move guess();
	
	/**
	 * Returns the string name of the player, ie Human, Computer Difficulty X
	 *
	 */
	public String getName();
}
