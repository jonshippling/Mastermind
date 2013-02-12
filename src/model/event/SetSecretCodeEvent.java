package model.event;

import model.Color;

import model.GameModel;

public class SetSecretCodeEvent implements ModelEvent {

	private Color[] colors;
	
	public SetSecretCodeEvent(Color[] colors) {
		this.colors = colors;
	}
	
	@Override
	public void execute(GameModel gm) {
		gm.setSecretCode(colors);
	}

}
