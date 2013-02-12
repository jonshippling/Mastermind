package gui;

import javax.swing.JPanel;

abstract class InputUI extends JPanel{
	private static final long serialVersionUID = 1L;

    /**
     * This is the initial setup of the panel depending on who is playing
     * @param turn
     */
    abstract void setTurn(GameUI.Turn turn);
    
    /**
     * This method is called when the InputUI panel is switched to.
     */
    abstract void turnCallback();

    abstract void setGameOver();
}
