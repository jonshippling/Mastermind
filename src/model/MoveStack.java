package model;

import java.util.LinkedList;
import java.util.Observable;

/**
 * MoveStack class
 * Contains all of the moves from the game
 *
 * @author Aaron Damrau
 * @author Richard Laughlin
 */
public class MoveStack extends Observable
{
	private LinkedList<Move> moveStack;
	private Color[] secretCode;
	private boolean gameOver;
	
	private int cursor = -1;

	public MoveStack()
	{
		moveStack = new LinkedList<Move>();
		gameOver = false;
	}	

	public Move top() {

		if(cursor >= 0) {
			return moveStack.get(cursor);
		} else {
			return null;
		}
	}
	
	/**
	 * Add a move onto the stack
	 * Will remove moves that were undid
	 */
	public void addMove(Move m)
	{
		if(cursor != moveStack.size() - 1 && cursor != -1)
		{
			while(moveStack.getLast() != moveStack.get(cursor))
			{
				moveStack.removeLast();
			}
		}
		if(cursor == -1)
		{
			moveStack = new LinkedList<Move>();
		}
		moveStack.add(m);
		cursor++;
		this.setChanged();
		this.notifyObservers();
	}

	public void notifyAllObservers() {
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Undoes the last move
	 * moves the cursor to a previous move
	 * 
	 * @return true if a move was successfully undone.
	 */
	public boolean undo() {
		if(cursor != -1)
		{
			Logger.getInstance().logUndo();
			Move topMove = top(); 

			if(topMove.getResponse() == null) {
				moveStack.remove(moveStack.size()-1);
			}

			--cursor;
			this.setChanged();
			this.notifyObservers();
			return true;
		}
		return false;
	}

	/**
	 * Redos the last undo
	 * moves the cursor forward if undo was called previously
	 * 
	 * @return true if a move was sucessfully redone.
	 */
	public boolean redo() {
		if(cursor < moveStack.size() - 1)
		{
			Logger.getInstance().logRedo();
			++cursor;
			this.setChanged();
			this.notifyObservers();
			return true;
		}
		return false;
	}

	public void setCode(Color[] secret) {
		secretCode = secret;
	}
	
	/**
	 * Returns the secret code
	 * 
	 * @return the secret code
	 */
	public Color[] getCode(){
		return secretCode;
	}
	
	/**
	 * Returns a stack up to the cursor
	 * @return stack
	 */
	public LinkedList<Move> getStack()
	{
		return new LinkedList<Move>(moveStack.subList(0, cursor + 1));
	}

	public boolean checkCodebreakerWin() {
		if(cursor > -1 && top().getResponse() != null) {
			
			Color[] guess = top().getGuess();
			
			gameOver = true;
			for(int i=0; i < GameModel.NUMPEGS; ++i) {
				if(secretCode[i] != guess[i]) {
					gameOver = false;
					break;
				}
			}
		}
		return gameOver;
	}
	
	public boolean checkCodemakerWin() {
		if(cursor > -1 && top().getResponse() != null) {
			gameOver = (cursor == GameModel.NUMGUESS-1);
		}
		return gameOver;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void clear() {
		cursor = -1;
		moveStack.clear();
		gameOver = false;

		setChanged();
		notifyObservers();
	}
}
