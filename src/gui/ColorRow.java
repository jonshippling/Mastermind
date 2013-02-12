package gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Color;
import model.GameModel;

public class ColorRow extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private JPanel[] panels = new JPanel[GameModel.NUMCOLORS];
	private Color[] colors = GameModel.COLORS;
	
	private JPanel selected;
	
	public ColorRow(Color[] colors)
	{
		this.colors = colors;
		setBorder(new LineBorder(Color.CYAN.getAwtColor(), 2));
		setLayout(new GridLayout(1,GameModel.NUMCOLORS));
		for(int j = 0; j < colors.length; ++j)
    	{
    		panels[j] = new JPanel();
    		panels[j].setBackground(colors[j].getAwtColor());
    		panels[j].addMouseListener(this);
    		add(panels[j]);
    	}
		selected = panels[0];
		update();
	}

	/**
	 * Updates the displayed selected item.
	 */
	public void update()
	{
		for(int x = 0; x < colors.length; ++x)
		{
			panels[x].setBackground(colors[x].getAwtColor().darker().darker().darker());
			panels[x].setBorder(null);
			
			if(panels[x] == selected)
			{
				if(colors[x] != Color.WHITE)
					selected.setBorder(new LineBorder(Color.WHITE.getAwtColor(), 5));
				else
					selected.setBorder(new LineBorder(Color.BLACK.getAwtColor(), 5));
				selected.setBackground(colors[x].getAwtColor());
			}
		}
	}
	
	/**
	 * Get the selected Color
	 * @return the selected Color
	 */
	public Color getSelected()
	{
		return Color.findColorByAwtColor(selected.getBackground());
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		for(JPanel panel : panels)
		{
			if(arg0.getSource() == panel)
				selected = (JPanel) arg0.getSource();
		}
		update();
	}
}
