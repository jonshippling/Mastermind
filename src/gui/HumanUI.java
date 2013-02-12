package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Color;
import model.GameModel;


/**
 * This is the HumanUI class. This is the GUIfor when a maker or breaker is being controlled by a human.
 *
 * @author Jason Greaves
 */
public class HumanUI extends InputUI
{
	private static final long serialVersionUID = 1L;
    
    private ColorChooser colorChoose;
    
    private JButton submitButton;

    public HumanUI(Color[] colorArray, Color[] feedBackArray, final GameUI gameUI, final GameUI.Turn turn){

        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, 0));
        
        submitButton = new JButton("Submit Code");
        submitButton.setPreferredSize(new Dimension(0, (Toolkit.getDefaultToolkit()
				.getScreenSize().height * 7/ 8) / (GameModel.NUMPEGS + 1)));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color[] code = generateCode();
                gameUI.codeGenerated(code);

            }
        });
        
        setLayout(new BorderLayout());
        
        this.add(submitButton, BorderLayout.SOUTH);
        setTurn(turn);
    }

    /**
     * This sets up the HumanUI in general. sets up the correct combo boxes.
     * @param turn Strung that decides whose turn it is
     */
    @Override
    public void setTurn(GameUI.Turn turn) {
        if(turn == GameUI.Turn.BREAKER) {
        	colorChoose = new ColorChooser(GameModel.COLORS);
        	submitButton.setText("Submit Guess");
        } else {
        	colorChoose = new ColorChooser(GameModel.FEEDBACK_COLOR);
        	submitButton.setText("Submit Feedback");
        }
        add(colorChoose);
    }

    /**
     * Generates an Arraylist of colors depending on the Combo boxes. Does it for both the maker and the breaker.
     * @return the code to be checked against the secret code.
     */
    public Color[] generateCode() {
        return colorChoose.getColors();
    }

	@Override
	void turnCallback() {
	}

	@Override
	void setGameOver() {
		colorChoose.setVisible(false);
		submitButton.setVisible(false);
	}
}
