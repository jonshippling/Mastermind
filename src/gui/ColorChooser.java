package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;
import model.Color;
import model.GameModel;

public class ColorChooser extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ColorRow[] colorRows = new ColorRow[GameModel.NUMPEGS];
    
	
	public ColorChooser(Color[] colors)
	{
		setLayout(new GridLayout(GameModel.NUMPEGS, 1));
		for(int i = 0; i < GameModel.NUMPEGS; i++)
        {
			colorRows[i] = new ColorRow(colors);
			add(colorRows[i]);
        }
	}
	
	/**
	 * @return the code represented by this chooser
	 */
	public Color[] getColors()
	{
		Color[] code = new Color[GameModel.NUMPEGS];
		for(int i=0; i < GameModel.NUMPEGS; ++i)
		{
			code[i] = colorRows[i].getSelected();
		}
		return code;
	}
}
