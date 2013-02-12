package model;

import model.GameModel;
import model.ComputerDif3;

import model.Move;

import junit.framework.TestCase;

public class ComputerDif3Test extends TestCase
{
	private ComputerDif3 comp;
	private GameModel game = new GameModel(null);
	
	protected void setUp() throws Exception
	{
		 comp = new ComputerDif3(game);
	}
	
	public void testGenerateResponse()
	{
		Color[] correctCode = comp.generateCode();
		assert(correctCode.length == GameModel.NUMPEGS);
		
		Move move = new Move(correctCode);
		
		comp.generateResponse(move);
		
		boolean isEmpty = false;
		boolean containsWhite = false;
		
		for(int i=0; i < GameModel.NUMPEGS; ++i) {
			if(move.getResponse()[i] == null) {
				isEmpty = true;
				break;
			}
			
			if(move.getResponse()[i] == Color.WHITE) {
				containsWhite = true;
				break;
			}
		}
			
		assert(!isEmpty);
		assert(!containsWhite);
	}
	
	public void testGuess()
	{
//
//		Move guess = comp.guess();
//		//assert(guess.getGuess().size() == game.NUMPEGS);
//		for(int x = 0; x < game.NUMPEGS; ++x)
//		{
//			assertEquals(guess.getGuess().get(x), game.getCode().get(x));
//		}
	}
	
	public void testGenerateCode()
	{
		assert(comp.generateCode().length == GameModel.NUMPEGS);
		for(Color color : comp.generateCode())
		{
			assert(contains(GameModel.COLORS, color));
		}
	}
	
	private boolean contains(Color[] colors, Color color)
	{
		for(Color col : colors)
			if(col.equals(color))
				return true;
		return false;
	}
}
