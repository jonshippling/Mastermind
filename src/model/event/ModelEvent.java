
package model.event;

import model.GameModel;

/**
 * Represents an event that will be run on the given GameModel.
 * @author Richard Laughlin
 */
public interface ModelEvent {
	
	/**
	 * Execute this model event on the given GameModel
	 * @param gm the model this event is executed on
	 */
	public void execute(GameModel gm);
	
}
