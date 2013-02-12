package model;

import model.ComputerDif3;
import model.GameModel;
import junit.framework.TestCase;

public class GameModelTest extends TestCase{
	private GameModel game;

	public void testNext()
	{
		game = new GameModel(null);
		game.changeBreaker(new ComputerDif3(game));
		game.changeMaker(new ComputerDif3(game));
		//It must first see if it makes a secret code, since there is none initially
		game.next(null);
		assertFalse(game.getStack().getCode().length == 0);
		//After that, it asks for a move from the breaker.
		game.next(null);
		assertFalse(game.getStack().top().getGuess() != null);
		//Once a move is on the stack, it will search for a response from the maker
		game.next(null);
		assertFalse(game.getStack().top().getResponse() != null);
	}
}
