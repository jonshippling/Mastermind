package model;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Jasong
 * Date: 4/3/12
 * Time: 8:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComputerDif2 extends Computer
{
	private ArrayList<Color[]> remainingMoves = new ArrayList<Color[]>();
	private ArrayList<Move> moveHistory = new ArrayList<Move>();

	public ComputerDif2()
	{
		super();
		remainingMoves
				.add(new Color[] { GameModel.COLORS[0], GameModel.COLORS[1],
						GameModel.COLORS[2], GameModel.COLORS[3] });

		for (Color col1 : GameModel.COLORS)
			for (Color col2 : GameModel.COLORS)
				for (Color col3 : GameModel.COLORS)
					for (Color col4 : GameModel.COLORS)
						remainingMoves
								.add(new Color[] { col1, col2, col3, col4 });

	}

	/**
	 * makes a guess
	 */
	public Move guess()
	{
		Move guess = new Move(remainingMoves.get(0));
		
		moveHistory.add(guess);
		return guess; 
	}
	
	/**
	 * returns the name of the the player
	 */
	public String getName()
	{
		return "Computer Difficulty: Strategy";
	}
}
