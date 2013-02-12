package model;

import java.util.ArrayList;
import java.util.Random;

public abstract class Computer implements IPlayer {
	
	protected Random random = new Random();
	
	protected Long startTime;
	protected long waitLength;
	protected Color[] code;
	
	protected Computer() {
		code = new Color[GameModel.NUMPEGS];
		waitLength = 0;
	}
	
	@Override
	public boolean waitIncrement() {
		
		//Prep
		if(startTime == null) {
			startTime = System.currentTimeMillis();
		}
		
		//Sleep for a bit
		try {
			Thread.sleep(50);
		} catch(Exception e) {
			Logger.getInstance().log("An interrupt happened while the computer was waiting.");
		}
		
		//Check for complete.
		if(startTime + waitLength <= System.currentTimeMillis()) {
			startTime = null;
			return false;
		} else {
			return true;
		}
		
	}
		
	@Override
	public void setWaitTime(long time) {
		waitLength = time;
	}

	/**
	 * Randomly generates a code for the breaker to solve
	 * 
	 * @return the code to be broken
	 */
	@Override
	public Color[] generateCode() {
		Color[] code = new Color[GameModel.NUMPEGS];

		for(int x = 0; x < 4; ++x) {
			code[x] = GameModel.COLORS[random.nextInt(GameModel.COLORS.length)];
		}
		return code;
	}

	/**
	 * Generates response to a provided Move object
	 */
	@Override
	public void generateResponse(Move guess) {
		Color[] guessed = guess.getGuess();
		int numBlack = 0;
		int numWhite = 0;
		int blank = 0;
		           
		boolean[] matchedG = new boolean[GameModel.COLORS.length];
		boolean[] matchedC = new boolean[GameModel.COLORS.length];
		
		for(int x = 0; x < GameModel.NUMPEGS; ++x)
		{
			if(guessed[x].equals(code[x]))
			{
				numBlack += 1;
				matchedG[x] = true;
				matchedC[x] = true;
			}
		}
		for(int x = 0; x < GameModel.NUMPEGS; ++x)
		{
			if(!matchedG[x])
			{
				for(int y = 0; y < GameModel.NUMPEGS; ++y)
				{
					if(x != y && !matchedC[y] && guessed[x].equals(code[y]))
					{
						numWhite += 1;
						matchedG[x] = true;
						matchedC[y] = false;
					}
				}
			}
		}
		ArrayList<Color> ret = new ArrayList<Color>();
		for(int x = 0; x < numBlack; ++x)
		{
			ret.add(Color.BLACK);
		}
		for(int x = 0; x < numWhite; ++x)
		{
			ret.add(Color.WHITE);
		}
		
		if(ret.size() != GameModel.NUMPEGS){
			blank = numBlack + numWhite;
			for(int i=0;i<blank;++blank)
			{
				ret.add(Color.GRAY);
			}
		}
		guess.setResponse(ret.toArray(new Color[GameModel.NUMPEGS]));
	}

}
