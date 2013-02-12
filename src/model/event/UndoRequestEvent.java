
package model.event;

import model.GameModel;

/**
 * Represents a request to undo the last move.
 * @author Richard Laughlin
 */
public class UndoRequestEvent implements ModelEvent {

	@Override
	public void execute(GameModel gm) {
		gm.undo();
	}

}
