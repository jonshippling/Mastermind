package gui;

import javax.swing.JLabel;
import java.awt.*;


/**
 * This is the BlankUI class. It the GUI for when playing against a Computer player.
 *
 * @author Jason Greaves
 */
public class ComputerUI extends InputUI
{
	private static final long serialVersionUID = 1L;

	private GameUI parent;
	
	JLabel spaceHolder;
	
	/**
	 * Creates a Computer UI. 
	 * @param gameUI the gameui that is the parent of this ComputerUI.
	 */
    public ComputerUI(GameUI gameUI)
    {
        setPreferredSize(new Dimension(0,Toolkit.getDefaultToolkit().getScreenSize().height/5));
        parent = gameUI;
        initComputerPane();
    }

    /**
     * This shows the computer's train of thought which will always be thinking..
     */
    private void initComputerPane()
    {
        spaceHolder = new JLabel("Thinking...");
        spaceHolder.setVerticalTextPosition((int)Component.CENTER_ALIGNMENT);
        spaceHolder.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(spaceHolder);
    }

    /**
     * Sets the turn from maker to breaker. Since the computer doesn't need this, it does nothing
     * @param turn Strung that decides whose turn it is
     */
    @Override
    public void setTurn(GameUI.Turn turn) {

    }

	@Override
	void turnCallback() {
		parent.codeGenerated(null);
	}

	@Override
	void setGameOver() {
		spaceHolder.setText("");
	}
}


