package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import model.AIType;
import model.event.ChangePlayerRequestEvent;
import controller.EventDispatch;

public class PlayerSelectionListener implements ActionListener
{
	private JCheckBoxMenuItem item;
	private JCheckBoxMenuItem[] others;
	private EventDispatch dispatch;
	private GameUI game;
	
	public PlayerSelectionListener(EventDispatch dispatch, GameUI game, JCheckBoxMenuItem selected, JCheckBoxMenuItem...others)
	{
		this.item = selected;
		this.others = others;
		this.dispatch = dispatch;
		this.game = game;
	}

    /**
     * Actionlistener that sets up the checkboxes for the player menubar properly.
     * @param arg0
     */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		item.setEnabled(false);
		item.setSelected(true);
		for(JCheckBoxMenuItem other : others)
		{
			other.setEnabled(true);
			other.setSelected(false);
		}
		
		AIType ai = AIType.HUMAN;
		if(item.getText().equals("Jedi"))
		{
			ai = AIType.COMPUTER_DIFF3;
			game.setBreakerType(GameUI.PlayerType.COMPUTER);
		}
		else if(item.getText().equals("Simple"))
		{
			ai = AIType.COMPUTER_DIFF1;
			game.setBreakerType(GameUI.PlayerType.COMPUTER);
		}
		else if(item.getText().equals("Human"))
		{
			ai = AIType.HUMAN;
			game.setBreakerType(GameUI.PlayerType.HUMAN);
		}
		
		dispatch.dispatchEvent(new ChangePlayerRequestEvent(false, ai));
		game.setTurn(game.getCurrentTurn());
	}
}
