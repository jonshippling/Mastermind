package model.event;

import model.AIType;
import model.ComputerDif1;
import model.ComputerDif3;
import model.GameModel;
import model.Human;
import model.IPlayer;

/**
 * A ModelEvent that changes which AI is managing a particular player.
 * @author Richard Laughlin
 */
public class ChangePlayerRequestEvent implements ModelEvent {

	private AIType aiType;
	private boolean changeMaker;
	
	/**
	 * Creates a new ChangePlayerRequest.
	 * @param changeMaker if true the maker is changed, if false the breaker.
	 * @param aiType the new AI Type to use.
	 */
	public ChangePlayerRequestEvent(boolean changeMaker, AIType aiType) {
		this.aiType = aiType;
		this.changeMaker = changeMaker;
	}
	
	@Override
	public void execute(GameModel gm) {
		
		IPlayer newPlayer = null;
		
		switch(aiType) {
		case HUMAN:
			newPlayer = new Human();
			break;
		case COMPUTER_DIFF1:
			newPlayer = new ComputerDif1();
			break;
		case COMPUTER_DIFF3:
			newPlayer = new ComputerDif3(gm);
			break;
		}
		
		if(newPlayer != null) {
			if(changeMaker) {
				gm.changeMaker(newPlayer);
			} else {
				gm.changeBreaker(newPlayer);
			}
		}
	}

}
