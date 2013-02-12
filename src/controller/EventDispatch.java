
package controller;

import java.util.concurrent.ConcurrentLinkedQueue;

import model.GameModel;
import model.event.ModelEvent;

/**
 * A class that routes Events from Observers to Models.
 * @author Richard Laughlin
 */
public class EventDispatch implements Runnable {
	
	private boolean isRunning;
	private ConcurrentLinkedQueue<ModelEvent> eventQueue;
	private GameModel model;
	
	/**
	 * Creates a new EventDispatch.
	 */
	public EventDispatch() {
		eventQueue = new ConcurrentLinkedQueue<ModelEvent>();
		isRunning = false;
		model = new GameModel(this);
		
		//Thread the event dispatch
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Dispatches a ModelEvent to any relevant models.
	 * This method is threadsafe.
	 * @param me the event to dispatch
	 */
	public void dispatchEvent(ModelEvent me) {
		eventQueue.offer(me);
	}
	
	/**
	 * @return true iff the thread is still processing.
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * Sets the value of the loop keep alive.
	 * @param value the value to set the keep alive to
	 */
	public void setRunning(boolean value) {
		isRunning = value;
	}

	/**
	 * @return the model that was created with this EventDispatch
	 */
	public GameModel getModel() {
		return model;
	}
	
	@Override
	public void run() {
		try {
			isRunning = true;
			while(isRunning) {
				
				//Dispatch Model Events
				ModelEvent nextEvent = eventQueue.poll();
				if(nextEvent != null) {
					try {
						nextEvent.execute(model);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				//Sleep for a bit
				Thread.sleep(50);		
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
