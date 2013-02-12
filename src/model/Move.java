package model;

/**
 * Move class
 * Contains the move and the response
 * 
 * @author Daniel Wallach
 */
public class Move
{
	private Color[] guess;
	private Color[] response;

	/**
	 * Constructor
	 * 
	 * @param colors
	 *            - some number of colors that make up the guess
	 */
	public Move(Color...colors)
	{
		guess = colors;
	}

	/**
	 * @return the guess
	 */
	public Color[] getGuess()
	{
		return guess;
	}

	/**
	 * @return the response
	 */
	public Color[] getResponse()
	{
		return response;
	}

	/**
	 * set the response to be read
	 * 
	 * @param colors a set of colors for the response
	 */
	public void setResponse(Color... colors)
	{
		response = colors;
	}
}
