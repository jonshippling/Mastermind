package model.event;

import model.GameModel;

public class UpdateLoggingStateEvent implements ModelEvent {

	private String filename;
	private boolean enabled;
	
	public UpdateLoggingStateEvent(String filename, boolean enabled) {
		this.filename = filename;
		this.enabled = enabled;
	}

	@Override
	public void execute(GameModel gm) {
		gm.setLoggingEnabled(filename, enabled);
	}
	
}
