package gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.GameModel;
import model.Move;

/**
 * The Panel to display a single move in the mastermind game
 * 
 * @author Daniel Wallach
 */
public class MoveView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel[] guessPanels = new JPanel[GameModel.NUMPEGS + 1];
	private JPanel[] responsePanels = new JPanel[GameModel.NUMPEGS];

	public MoveView()
	{
		setLayout(new GridLayout(GameModel.NUMPEGS + 1, 1, 2, 2));
		guessPanels[GameModel.NUMPEGS] = new JPanel(new GridLayout(
				GameModel.NUMPEGS / 2, 2, 2, 2));

		for (int x = 0; x < GameModel.NUMPEGS; ++x)
		{
			guessPanels[x] = new JPanel();
			add(guessPanels[x]);
			guessPanels[x].setBorder(new LineBorder(Color.DARK_GRAY));
			responsePanels[x] = new JPanel();
			guessPanels[GameModel.NUMPEGS].add(responsePanels[x]);
			responsePanels[x].setBorder(new LineBorder(Color.DARK_GRAY));
		}
		add(guessPanels[GameModel.NUMPEGS]);
		setBorder(new LineBorder(Color.BLACK));
	}

	/**
	 * updates the display to the correct set of colors
	 * 
	 * @param move
	 *            - the move to display here
	 */
	public void updateDisplay(Move move)
	{	
		for (JPanel panel : guessPanels) {
			panel.setBackground(null);
		}
		
		for (JPanel panel : responsePanels) {
			panel.setBackground(null);
		}

		if(move != null)
		{
			if(move.getGuess() != null) {
				for (int x = 0; x < move.getGuess().length; ++x)
					guessPanels[x].setBackground(move.getGuess()[x].getAwtColor());
			}
			
			if(move.getResponse() != null) {
				for (int x = 0; x < move.getResponse().length; ++x)
					responsePanels[x].setBackground(move.getResponse()[x].getAwtColor());
			}
		}
	}
}
