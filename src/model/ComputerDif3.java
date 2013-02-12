package model;

/**
 * Class for computer difficulty 3, AKA jedi difficulty.  This computer AI
 * automatically wins.
 * 
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */
public class ComputerDif3 extends Computer {
	
	private GameModel game;
	
	/**
	 * Constructor for the easy difficulty computer player
	 */
	public ComputerDif3(GameModel game){
		super();
		this.game = game;
	}

	/**
	 * Gets the code from the game and returns it, making an automatic winner.
	 */
	@Override
	public Move guess() {
		return new Move(game.getStack().getCode());
	}

	/**
	 * Returns "Computer Difficulty: Jedi"
	 *
	 * @return the string "Computer Difficulty: Jedi"
	 */
	 public String getName(){
		return "Computer Difficulty: Jedi";
	}
}
