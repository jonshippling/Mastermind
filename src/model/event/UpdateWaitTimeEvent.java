package model.event;

import model.GameModel;

public class UpdateWaitTimeEvent implements ModelEvent {

	private long waitTime;
	
	public UpdateWaitTimeEvent(long newTime) {
		this.waitTime = newTime * 1000;
	}
	
	@Override
	public void execute(GameModel gm) {
		gm.setWaitTime(waitTime);
	}

}
