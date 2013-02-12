package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Color;
import model.GameModel;

/**
 * Created by IntelliJ IDEA.
 * User: Jasong
 * Date: 4/2/12
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateCodeUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private ColorChooser chooser = new ColorChooser(GameModel.COLORS);

    public GenerateCodeUI(final GameUI ui, Color[] colorArray)
    {
    	setTitle("Input Secret Code");
    	setLayout(new BorderLayout());
    	setSize(400,400);
    	
		setLocationRelativeTo(null);
    	
    	add(chooser, BorderLayout.CENTER);
    	
        JButton submitCode = new JButton("Submit Secret Code");
        submitCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color[] code = genCode();
                dispose();
                ui.sendInitialCode(code);
                ui.setVisible(true);
            }
        });
        this.add(submitCode, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * this is the array list of colors that will be the secret code that the codebreaker will have to guess
     * @return return the code the ui has set.
     */
    public Color[] genCode()
    {
        return chooser.getColors();
    }
}
