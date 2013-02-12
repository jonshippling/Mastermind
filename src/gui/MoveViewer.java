package gui;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import model.GameModel;
import model.Move;
import model.MoveStack;

/**
 * An observer that should observe the movestack and display the moves made in accordance to
 * the movestack's moves
 *
 * @author Daniel Wallach
 * @author Jason Greaves
 */
public class MoveViewer extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	public MoveView[] views = new MoveView[GameModel.NUMGUESS];
	
	public MoveViewer(GameUI parent)
	{
		
		setBorder(new LineBorder(Color.BLACK));
		setLayout(new GridLayout(1, GameModel.NUMGUESS));
		for(int x = 0; x < GameModel.NUMGUESS; ++x)
		{
			views[x] = new MoveView();
			add(views[x]);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		MoveStack moves = (MoveStack)arg0;
		final LinkedList<Move> stack = moves.getStack();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for(int x = 0; x < GameModel.NUMGUESS; ++x)
				{
					if(x < stack.size())
						views[x].updateDisplay(stack.get(x));
					else
						views[x].updateDisplay(null);
				}
			}
		});
		
	}
	
}
