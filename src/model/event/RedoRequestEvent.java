package model.event;

import model.GameModel;

/**
 * Represents a Redo request.
 * @author Richard Laughlin
 */
public class RedoRequestEvent implements ModelEvent {

	@Override
	public void execute(GameModel gm) {
		gm.redo();
	}

}
