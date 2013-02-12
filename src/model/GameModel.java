package model;

import model.event.AdvanceRequestEvent;
import controller.EventDispatch;

/**
 * The model for the Mastermind game.
 * 
 * This class is where the actual logic and state of the mastermind game is kept.
 * 
 * @author Richard Laughlin
 * @author Aaron Damrau
 */
public class GameModel
{
	private IPlayer codebreaker;
	private IPlayer codemaker;
	private long waitTime;
	
	private MoveStack moves;
	
	private EventDispatch dispatch;
	
	public static final int NUMGUESS = 10;
	public static final int NUMCOLORS = 6;
	public static final int NUMPEGS = 4;
	public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLACK};
    public static final Color[] FEEDBACK_COLOR = {Color.WHITE, Color.GRAY, Color.BLACK};
    
    /**
     * Creates a new GameModel with two Human players.
     */
	public GameModel(EventDispatch dispatch) {
		this.moves = new MoveStack();
		this.dispatch = dispatch;
		waitTime = 30000;
		newGame();
	}
	
	/**
	 * sets whether or not to enable logging
	 * @param filename - the file to log too
	 * @param value - whether or not to log
	 */
	public void setLoggingEnabled(String filename, boolean value)
	{
		if(value) {
			Logger.getInstance().turnOnLogging(filename, true);
			
			for(Move m : moves.getStack()) {
				Logger.getInstance().logMove(m);
				if(m.getResponse() != null) {
					Logger.getInstance().logResponse(m);
				}
			}
			
		} else {
			Logger.getInstance().turnOffLogging();
		}
	}
	
	/**
	 * Resets the game and makes a new one
	 */
	public void newGame() {
		Logger.getInstance().turnOffLogging();
		this.codemaker = new Human();
		this.codebreaker = new Human();
		
		moves.clear();
		if(Logger.getInstance().isLogging())
			Logger.getInstance().turnOnLogging("test.txt", true);
	}
	
	/**
	 * Uses the current players to advance the game one step.
	 */
	public void next(Color[] uiGuess) {
		
		if(moves.isGameOver()) {
			return;
		}
		
		if( moves.top() == null || moves.top().getResponse()!= null ) {
			if(codebreaker.waitIncrement()) {
				dispatch.dispatchEvent(new AdvanceRequestEvent(uiGuess));
				return;
			}
			
			Move move;
			if (uiGuess == null) {
				move = codebreaker.guess();
			} else {
				move = new Move(uiGuess);
			}
			
			if(move != null) {
				Logger.getInstance().logMove(move);
				moves.addMove(move);
			}
		} else {
			
			if(uiGuess == null) {
				codemaker.generateResponse(moves.top());
			} else {
				moves.top().setResponse(uiGuess);
			}
			Logger.getInstance().logResponse(moves.top());
			
			moves.notifyAllObservers();
		}
			
	}
	
	/**
	 * Sets the secret code to be used until reset
	 * @param colors - the code to use
	 */
	public void setSecretCode(Color[] colors) {
		Logger.getInstance().logSecretCode(colors);
		moves.setCode(colors);
	}
	
	/**
	 * Undoes a move previous done.
	 */
	public void undo() {
		moves.undo();
	}
	
	/**
	 * Redoes a move previously undone.
	 */
	public void redo() {
		moves.redo();
	}
	
	/**
	 * Changes the Codemaker to the given player
	 * @param player the new player AI to use for Codemaker
	 */
	public void changeMaker(IPlayer player) {
		Logger.getInstance().logMakerChange(codebreaker, player);
		this.codemaker = player;
		this.codemaker.setWaitTime(waitTime);
	}
	
	/**
	 * Changes the Codebreaker to the given player
	 * @param player the new player AI to use for Codebreaker
	 */
	public void changeBreaker(IPlayer player) {
		Logger.getInstance().logBreakerChange(codebreaker, player);
		this.codebreaker = player;
		this.codebreaker.setWaitTime(waitTime);
	}
	
	public void setWaitTime(long waitTime) {
		this.codebreaker.setWaitTime(waitTime);
		this.codemaker.setWaitTime(waitTime);
		this.waitTime = waitTime;
	}
	
	public MoveStack getStack()
	{
		return this.moves;
	}
	
}
