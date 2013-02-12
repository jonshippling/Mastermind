
package model.event;

import model.Color;
import model.GameModel;

/**
 * Represents a request to advance the game.
 * @author Richard Laughlin
 */
public class AdvanceRequestEvent implements ModelEvent {

	public Color[] uiGuess;
	
	public AdvanceRequestEvent() {
		this.uiGuess = null;
	}
	
	public AdvanceRequestEvent(Color[] response) {
		this.uiGuess = response;
	}
	
	@Override
	public void execute(GameModel gm) {
		gm.next(uiGuess);
	}

}
