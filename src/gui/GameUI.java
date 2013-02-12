package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Color;
import model.GameModel;
import model.Move;
import model.MoveStack;
import model.event.AdvanceRequestEvent;
import model.event.NewGameEvent;
import model.event.RedoRequestEvent;
import model.event.SetSecretCodeEvent;
import model.event.UndoRequestEvent;
import model.event.UpdateLoggingStateEvent;
import model.event.UpdateWaitTimeEvent;
import model.Logger;

import controller.EventDispatch;

/**
 * This class is the GUI of the entire game. It also gives the code
 * 
 * @author Jason Greaves
 */
public class GameUI extends JFrame implements Observer
{
	/**
	 * Represents which player currently has their turn.
	 * @author Jason Greaves
	 */
	public static enum Turn
	{
		BREAKER, MAKER
	};

	/**
	 * Enum which is used to tell player types apart.
	 * @author Jason Greaves
	 */
	public static enum PlayerType
	{
		COMPUTER, HUMAN
	};
	
	private static final long serialVersionUID = 1L;
	
	private InputUI maker, breaker;
	private JButton undoButton, redoButton;
	private JCheckBoxMenuItem logButton, jediComPlayer, easyComPlayer, humanPlayer;
	
	private JPanel bottomUIPanel;
	private CardLayout bottomCardLayout;

	private MoveViewer moveView;
	private EventDispatch dispatch;
	private GameModel model;

	private Turn currentTurn = Turn.BREAKER;

	/**
	 * Creates a new GameUI.
	 * @param dispatcher the event dispatch to associate with.
	 * @param maker the player type of the maker.
	 * @param breaker the player type of the breaker.
	 */
	public GameUI(EventDispatch dispatcher, PlayerType maker, PlayerType breaker)
	{
		super("Mastermind");
		
		this.dispatch = dispatcher;
		model = dispatch.getModel();

		model.getStack().addObserver(this);
		
		createUI();
		initMenuBar();
		
		setBreakerType(breaker);
		setMakerType(maker);
		
		// Set MoveView as an Observer
		model.getStack().addObserver(moveView);

		// This is the game's window
		//setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				logButton.setSelected(true);
				logPrompt();
			}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {}
			
			@Override
			public void windowClosed(WindowEvent e) {
				dispatch.dispatchEvent(new UpdateLoggingStateEvent(null, false));
				dispatch.setRunning(false);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});

        GenerateCodeUI genCodeUI = new GenerateCodeUI(this, GameModel.COLORS);
        genCodeUI.setVisible(true);
        
	}

	/**
	 * This method will create the UI. It also prompts the user for
	 * the secret code.
	 */
	private void createUI()
	{
		moveView = new MoveViewer(this);

		JPanel undoPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		undoPanel.setPreferredSize(new Dimension(1, Toolkit.getDefaultToolkit()
				.getScreenSize().height / 8));

		undoButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(currentTurn == Turn.MAKER) {
					toggleTurn();
				}
				
				dispatch.dispatchEvent(new UndoRequestEvent());
			}
		});

		redoButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispatch.dispatchEvent(new RedoRequestEvent());
			}
		});

		undoPanel.add(undoButton);
		undoPanel.add(redoButton);
		
		//Setup the Bottom panel related stuff.
		bottomUIPanel = new JPanel();
		bottomCardLayout = new CardLayout();
		bottomUIPanel.setLayout(bottomCardLayout);
		
		// container to put GUI things in the frame
		setLayout(new BorderLayout());
		this.add(moveView, BorderLayout.CENTER);
		this.add(undoPanel, BorderLayout.SOUTH);
		this.add(bottomUIPanel, BorderLayout.EAST);
	}

	/**
	 * Creates the menu bar objects at the top of the window.
	 */
	private void initMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGameButton = new JMenuItem("New Game");
		newGameButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispatch.dispatchEvent(new NewGameEvent());
				
				setMakerType(PlayerType.HUMAN);
				setBreakerType(PlayerType.HUMAN);
				
				GameUI.this.currentTurn = Turn.MAKER;
				
				GameUI.this.humanPlayer.setSelected(true);
				GameUI.this.humanPlayer.setEnabled(false);
				
				GameUI.this.easyComPlayer.setSelected(false);
				GameUI.this.easyComPlayer.setEnabled(true);
				
				GameUI.this.jediComPlayer.setSelected(false);
				GameUI.this.jediComPlayer.setEnabled(true);
				
				undoButton.setEnabled(true);
				redoButton.setEnabled(true);
				
				GenerateCodeUI genCodeUI = new GenerateCodeUI(GameUI.this, GameModel.COLORS);
				genCodeUI.setVisible(true);
		        setVisible(false);
			}
		});
		
		logButton = new JCheckBoxMenuItem("Log");
		logButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logPrompt();
			}
		});
		
		JMenuItem setWaitTimeButton = new JMenuItem("Set wait time");
		setWaitTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WaitTimeSelectionDialog waitDialog = new WaitTimeSelectionDialog(GameUI.this, 0, 30, 5, 15);
				if(waitDialog.getSelectedOption() != null && 
						((Integer)waitDialog.getSelectedOption())  == JOptionPane.OK_OPTION) {
					dispatch.dispatchEvent(new UpdateWaitTimeEvent(waitDialog.selectedValue));
				}
			}
		});
		
		JMenuItem exitButton = new JMenuItem("Exit");

		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		fileMenu.add(newGameButton);
		fileMenu.add(logButton);
		fileMenu.add(setWaitTimeButton);
		fileMenu.add(exitButton);

		menuBar.add(fileMenu);

		JMenu playerMenu = new JMenu("Player");
		humanPlayer = new JCheckBoxMenuItem("Human");
		easyComPlayer = new JCheckBoxMenuItem("Simple");
		jediComPlayer = new JCheckBoxMenuItem("Jedi");

		humanPlayer.setSelected(true);
		humanPlayer.setEnabled(false);

		humanPlayer.addActionListener(new PlayerSelectionListener(dispatch,this,
				humanPlayer, easyComPlayer, jediComPlayer));
		easyComPlayer.addActionListener(new PlayerSelectionListener(dispatch,this,
				easyComPlayer, humanPlayer, jediComPlayer));
		jediComPlayer.addActionListener(new PlayerSelectionListener(dispatch,this,
				jediComPlayer, easyComPlayer, humanPlayer));

		playerMenu.add(humanPlayer);
		playerMenu.add(easyComPlayer);
		playerMenu.add(jediComPlayer);

		menuBar.add(playerMenu);
		this.setJMenuBar(menuBar);

	}
	
	private void logPrompt() {
		if(logButton.isSelected()) {
			
			boolean exists = true;
			while(exists) {
				String filename = JOptionPane.showInputDialog("What is the log file name?");
				if(filename != null && !"".equals(filename)) {
					java.io.File fileObject = new java.io.File(filename);
					
					if(fileObject.exists()) {
					
						if(fileObject.isFile()) {
							int result = JOptionPane.showConfirmDialog(GameUI.this, 
									"That file exists. Do you want to overwrite?");
							
							if(result == JOptionPane.NO_OPTION) {
								continue;
							} else if(	result == JOptionPane.CANCEL_OPTION || 
										result == JOptionPane.CLOSED_OPTION) {
								logButton.setSelected(false);
								break;
							}
						} else {
							JOptionPane.showMessageDialog(GameUI.this, "That is not a file!", "Error!", JOptionPane.ERROR_MESSAGE);
							continue;
						}
					}
					
					
					dispatch.dispatchEvent(new UpdateLoggingStateEvent(filename, true));
					break;
				} else {
					logButton.setSelected(false);
					break;
				}
			}
			
		} else {
			dispatch.dispatchEvent(new UpdateLoggingStateEvent(null, false));
		}
	}
	
	/**
	 * Changes the type of the player currently in play.
	 * 
	 * @param side
	 */
	public void setTurn(Turn turn) {
		currentTurn = turn;
		if(currentTurn == Turn.BREAKER) {
			breaker.turnCallback();
		} else {
			maker.turnCallback();
		}
		
		bottomCardLayout.show(bottomUIPanel, turn.toString());
	}
	
	public void toggleTurn() {
		currentTurn = (currentTurn == Turn.BREAKER) ? Turn.MAKER : Turn.BREAKER;
		
		if(currentTurn == Turn.BREAKER) {
			breaker.turnCallback();
		} else {
			maker.turnCallback();
		}
		
		bottomCardLayout.show(bottomUIPanel, currentTurn.toString());
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}
	
	public void setMakerType(PlayerType change) {
		
		if(this.maker != null) {
			bottomUIPanel.remove(maker);
			//bottomCardLayout.next(bottomUIPanel);
		}
		
		if (change == PlayerType.HUMAN) {
			this.maker = new HumanUI(GameModel.COLORS, GameModel.FEEDBACK_COLOR, this, Turn.MAKER);
		} else {
			this.maker = new ComputerUI(this);
		}
		
		bottomUIPanel.add(maker, Turn.MAKER.toString());
	}
	
	public void setBreakerType(PlayerType change)
	{
		if(this.breaker != null) {
			bottomUIPanel.remove(breaker);
			//bottomCardLayout.next(bottomUIPanel);
		}
		
		if (change == PlayerType.HUMAN) {
			this.breaker = new HumanUI(GameModel.COLORS, GameModel.FEEDBACK_COLOR, this, Turn.BREAKER);
		} else {
			this.breaker = new ComputerUI(this);
		}
		
		bottomUIPanel.add(breaker, Turn.BREAKER.toString());
	}

	public void gameOver() {
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		
		humanPlayer.setEnabled(false);
		easyComPlayer.setEnabled(false);
		jediComPlayer.setEnabled(false);
		
		maker.setGameOver();
		breaker.setGameOver();
		
	}
	
	/**
	 * Changes the turn and passes the code to the dispatcher.
	 * 
	 * @param code the code to send to the dispatcher.
	 */
	public void codeGenerated(Color[] code)
	{
		dispatch.dispatchEvent(new AdvanceRequestEvent(code));
	}

	/**
	 * Sends the secret code to the dispatcher.
	 * @param initCode the secret code.
	 */
    public void sendInitialCode(Color[] initCode)
    {
        dispatch.dispatchEvent(new SetSecretCodeEvent(initCode));
    }

	@Override
	public void update(Observable o, Object arg) {
		MoveStack moves = (MoveStack)o;
		final LinkedList<Move> stack = moves.getStack();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if(stack.size() > 0) {
					if(stack.getLast().getResponse() == null) {
						setTurn(Turn.MAKER);
					} else {
						setTurn(Turn.BREAKER);
					}
				} else {
					setTurn(Turn.BREAKER);
				}
			}
		});
		
		if(stack.size() > 0 && stack.getLast().getResponse() != null) {
			//Win conditions
			if(moves.checkCodebreakerWin())
			{
				gameOver();
				JOptionPane.showMessageDialog(this, "The Codebreaker has won the game!");
				Logger.getInstance().log("The Codebreaker has won the game!");
			} else if(moves.checkCodemakerWin())
			{
				gameOver();
				JOptionPane.showMessageDialog(this, "The Codemaker has won the game!");
				Logger.getInstance().log("The Codemaker has won the game!");
			}
		}
	}

}
