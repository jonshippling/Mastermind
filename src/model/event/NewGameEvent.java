package model.event;

import model.GameModel;

public class NewGameEvent implements ModelEvent {

	@Override
	public void execute(GameModel gm) {
		gm.newGame();
	}

}
