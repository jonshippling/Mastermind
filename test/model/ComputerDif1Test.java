package model;

import model.ComputerDif1;
import model.GameModel;
import model.Move;

import junit.framework.TestCase;

public class ComputerDif1Test extends TestCase
{
	private ComputerDif1 comp;
	
	protected void setUp() throws Exception
	{
		 comp = new ComputerDif1();
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
		Move guess = comp.guess();
		assert(guess.getGuess().length == GameModel.NUMPEGS);
		for(Color color : guess.getGuess())
		{
			assert(contains(GameModel.COLORS, color));
		}
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
