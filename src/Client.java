import javax.swing.SwingUtilities;

import gui.GameUI;
import controller.EventDispatch;

public class Client
{
	/**
	 * The main method.
	 * @param args not used
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//Create the event dispatch
				EventDispatch dispatch = new EventDispatch();
				
				//Create the game UI, which uses the event dispatch and then runs on the main thread.
				GameUI ui = new GameUI(dispatch, GameUI.PlayerType.HUMAN, GameUI.PlayerType.HUMAN);
			}
		});
	}
}