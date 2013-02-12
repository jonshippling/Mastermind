package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Logger for the mastermind project.
 * Logs to a given file, that is given whenever you turn on logging.
 * 
 * Contains many different prewritten logs, and can do custom logs also
 * 
 * @author Daniel Wallach
 */
public class Logger
{
	private static Logger instance = null;

	private PrintWriter out;
	private boolean logging = false;

	private Logger()
	{
		if(instance != null)
			instance = new Logger();
	}

	/**
	 * Get a instance of the logger, create it if necessary
	 * 
	 * @return instance of the logger
	 */
	public static Logger getInstance()
	{
		instance = instance == null ? new Logger() : instance;
		return instance;
	}

	/**
	 * Turn on the logging function
	 * 
	 * @param filename
	 *            - file to save to
	 * @return false iff the file already exists, does not overwrite
	 */
	public boolean turnOnLogging(String filename)
	{
		return turnOnLogging(filename, false);
	}

	/**
	 * Turn on the logging,
	 * Specify a filename to log with, should be called for every game that logs
	 * 
	 * @param filename
	 *            file to log at
	 * @param overwrite
	 *            true iff the file should be overwritten
	 * 
	 * @return false iff the file already exists
	 */
	public boolean turnOnLogging(String filename, boolean overwrite)
	{
		File file = new File(filename);
		if (file.exists() && (!overwrite || !file.isFile()))
		{
			return false;
		}

		try
		{
			file.delete();
			file.createNewFile();
			out = new PrintWriter(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		logging = true;
		
		log("Logging Initialized");
		log("File: " + file.getName());
		
		return true;
	}

	/**
	 * Turn off the logging.
	 * called when game ends.
	 */
	public void turnOffLogging()
	{
		log("Game over");
		log("Logging Finished");
		log("File Closed");
		
		if (logging)
			out.close();
	}
	
	/**
	 * Log the message given. Adds it to the bottom of the final
	 * 
	 * @param message
	 *            - message to log
	 */
	public void log(String message)
	{
		if (logging)
		{
			System.out.println(message);
			out.println(message);
			out.flush();
		}
	}

	/**
	 * Log the given move.
	 * Record what was guessed.
	 * 
	 * @param move
	 */
	public void logMove(Move move)
	{
		if(move.getGuess() != null) {
			String log = "Code Guessed : ";
	
			for (Color color : move.getGuess())
			{
				log += " " + color;
			}
	
			log(log);
		}
	}

	/**
	 * Log the response to this move.
	 * Logs the number right and rightish
	 * 
	 * @param move
	 */
	public void logResponse(Move move)
	{
		if(move.getResponse() != null) {
			String log = "Code Response: ";
	
			for (Color color : move.getResponse())
			{
				log += " " + color;
			}
	
			log(log);
		}
	}

	/**
	 * Logs when a move is undone
	 */
	public void logUndo()
	{
		log("Move undone");
	}

	/**
	 * Logs a redo
	 */
	public void logRedo()
	{
		log("Move Redone");
	}

	/**
	 * Log the players changing.
	 * Needs old and new so that all info can be displayed
	 * 
	 * @param oldPlayer
	 * @param newPlayer
	 */
	public void logBreakerChange(IPlayer oldPlayer, IPlayer newPlayer)
	{
		log("Breaker: " + oldPlayer.getName() + " has been replaced by "
				+ newPlayer.getName());
	}
	
	/**
	 * Log the players changing.
	 * Needs old and new so that all info can be displayed
	 * 
	 * @param oldPlayer
	 * @param newPlayer
	 */
	public void logMakerChange(IPlayer oldPlayer, IPlayer newPlayer)
	{
		log("Maker: " + oldPlayer.getName() + " has been replaced by "
				+ newPlayer.getName());
	}
	
	/**
	 * 
	 * @return true iff logging is enabled
	 */
	public boolean isLogging()
	{
		return logging;
	}
	
	/**
	 * Sets whether or not to log
	 * @param log - whther or not to log
	 */
	public void setLogging(boolean log)
	{
		logging = log;
	}
	
	/**
	 * Logs the secret code to the file
	 * @param code - the code to log
	 */
	public void logSecretCode(Color[] code)
	{
		String log = "Secret Code: ";
		for(Color col : code)
		{
			log += col.toString() + ", ";
		}
		
		log(log);
	}
}
