/**
 * GameRound.java 1.0
 */
package com.cyclum.task.beans;

import java.io.Serializable;
import java.util.Random;

/**
 * Bean for a play round.
 * @author Mdraa
 *
 */
public class GameRound implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/** First player choice. */
	private GameValues firstPlayerChoice;

	/** Second player choice. */
	private GameValues secondPlayerChoice;

	/**
	 * Contructor.
	 */
	public GameRound() {

		// First player get a random choice.
		firstPlayerChoice = GameValues.values()[new Random().nextInt(GameValues.values().length)];

		//Second player always get rock.
		secondPlayerChoice = GameValues.ROCK;
	}

	/**
	 * @return the firstPlayerChoice
	 */
	public String getFirstPlayerChoice() {
		return firstPlayerChoice.name();
	}

	/**
	 * @return the secondPlayerChoice
	 */
	public String getSecondPlayerChoice() {
		return secondPlayerChoice.name();
	}

	/**
	 * @return the winner
	 */
	public String getWinner() {
		String winner = GameResults.DRAW.name();

		if (GameValues.PAPER.equals(firstPlayerChoice)) {
			winner = GameResults.WINNER_PLAYER1.name();
		} else if (GameValues.SCISSORS.equals(firstPlayerChoice)) {
			winner = GameResults.WINNER_PLAYER2.name();
		}

		return winner;
	}

}
