package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Marquee extends JLabel implements ActionListener
{
	/**
	 * Integer value for direction.
	 */
	public final static int SCROLL_RIGHT = -1;
	/**
	 * Integer value for direction
	 */
	public final static int SCROLL_LEFT = 1;

	private int dir = SCROLL_LEFT;
	private int speed = 1;

	private int currLocation = 0;
	private String displayString;
	private Timer time;
	private String str;
	private int screenLength;

	/**
	 * Creates a new marquee with the given string
	 * 
	 * @param dir
	 * @param str
	 */

	public Marquee(String str)
	{
		super();
		this.setMarqueeText(str);
		time = new Timer(100 / speed, this);
		time.setRepeats(true);
		time.start();
	}

	public Marquee(String str, int dir)
	{
		this(str);
		setDirection(dir);
	}

	/**
	 * Starts the marquee thread if stopped
	 */
	public void start()
	{
		time.start();
	}

	/**
	 * Stops the marquee thread at its current location
	 */
	public void stop()
	{
		time.stop();
	}

	/**
	 * Sets the speed that the marquee scrolls at.
	 * Positive values scroll to the right, negative to the left.
	 * 
	 * Valid values range from [0] - [10]
	 * 
	 * @param speed
	 * @return if the new value for speed was valid.
	 */
	public boolean setSpeed(int speed)
	{
		if (speed >= 0 && speed <= 10)
		{
			this.speed = speed;
			time.setDelay(100 / speed);
			return true;
		}
		return false;
	}

	/**
	 * Gets the speed of the marquee scroll
	 * Positive values scroll to the right, negative to the left.
	 * 
	 * Valid values range from [0] - [10]
	 * 
	 * @return the speed the marquee is scrolling at
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * Sets the direction that the marquee scrolls.
	 * Meant to be used to with the bound constants SCROLL_RIGHT and SCROLL_LEFT
	 * 
	 * @param direction
	 *            integer for the direction
	 * @return if the new value for direction was accepted
	 */
	public boolean setDirection(int direction)
	{
		switch (direction)
		{
			case SCROLL_RIGHT:
				dir = SCROLL_RIGHT;
				break;
			case SCROLL_LEFT:
				dir = SCROLL_LEFT;
				break;
			default:
				return false;
		}
		return true;
	}

	/**
	 * Gets the direction that the marquee is scrolling
	 * 
	 * Valid values = Marquee.SCROLL_LEFT or Marquee.SCROLL_RIGHT
	 * 
	 * @return the direction of currect scrolling;
	 */
	public int getDirection()
	{
		return dir;
	}

	/**
	 * Defines the single line of text this component will display. If the value
	 * of text is null or empty string, nothing is displayed.
	 * 
	 * The default value of this property is null.
	 */
	public void setMarqueeText(String text)
	{
		refreshString(text);
		displayString = text;
	}

	/**
	 * Returns the text string that the label displays.
	 * 
	 * @return a String
	 */
	public String getMarqueeText()
	{
		return displayString;
	}

	/**
	 * remakes the string that is used for display. This string is the entire display string.
	 * 
	 * Later a substring is taken for the current display
	 * 
	 * @param text - the text to be displayed in the center of the string.
	 */
	private void refreshString(String text)
	{
		String empty = "";
		for (int x = 0; x < getWidth() / 3; ++x)
		{
			empty += " ";
		}
		str = empty + text;
		screenLength = empty.length() + text.length();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		refreshString(displayString);

		currLocation += dir;
		
		if(currLocation <= 0 || currLocation >= screenLength)
		{
			currLocation = Math.abs(dir - 1) * screenLength / 2;
		}
		super.setText(str.substring(currLocation));
	}

	public static void main(String[] args)
	{
		JFrame test = new JFrame();
		test.setLayout(new BorderLayout());
		test.setSize(500, 200);
		test.setVisible(true);

		Marquee m = new Marquee("THIS IS AWESOME!!!");
		m.setSpeed(4);

		Marquee m2 = new Marquee("BOOYAH!!!", Marquee.SCROLL_RIGHT);
		m2.setSpeed(4);

		test.add(m, BorderLayout.NORTH);
		test.add(m2, BorderLayout.SOUTH);
		test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
