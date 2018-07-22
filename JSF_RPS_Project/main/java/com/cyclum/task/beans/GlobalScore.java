package com.cyclum.task.beans;

/**
 * Class which contains global score data.
 * 
 * @author Mdraa
 *
 */
public class GlobalScore {

	/** Total rounds. */
	private Integer totalRounds;
	
	/** Number of first player wins. */
	private Integer firstPlayerWins;
	
	/** Number of second player wins. */
	private Integer secondPlayerWins;
	
	/** Number of total draws. */
	private Integer totalDraws;

	/**
	 * Constructor.
	 */
	public GlobalScore() {
		super();
		this.totalRounds = 0;
		this.firstPlayerWins = 0;
		this.secondPlayerWins = 0;
		this.totalDraws = 0;
	}
	
	/**
	 * @return the totalRounds
	 */
	public Integer getTotalRounds() {
		return totalRounds;
	}

	/**
	 * @return the firstPlayerWins
	 */
	public Integer getFirstPlayerWins() {
		return firstPlayerWins;
	}

	/**
	 * @return the secondPlayerWins
	 */
	public Integer getSecondPlayerWins() {
		return secondPlayerWins;
	}

	/**
	 * @return the totalDraws
	 */
	public Integer getTotalDraws() {
		return totalDraws;
	}
	
	/**
	 * Method which update global score with a new round.
	 */
	public void updateGlobalScore(final GameRound round) {

		GameResults gameResult = GameResults.valueOf(round.getWinner());
		switch (gameResult) {
		case WINNER_PLAYER1:
			firstPlayerWins++;
			break;
		case WINNER_PLAYER2:
			secondPlayerWins++;
			break;
		case DRAW:
			totalDraws++;
			break;
		default:
			break;
		}
		this.totalRounds++;

	}
	
}