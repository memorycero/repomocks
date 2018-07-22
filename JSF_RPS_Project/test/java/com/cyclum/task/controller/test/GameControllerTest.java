/**
 * GameControllerTest.java 1.0
 */
package com.cyclum.task.controller.test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.cyclum.task.beans.GameRound;
import com.cyclum.task.beans.GameValues;
import com.cyclum.task.beans.GlobalScore;
import com.cyclum.task.controller.GameController;

/**
 * Testing class for GameController.
 * @author Mdraa
 *
 */
public class GameControllerTest {

	/** Logger. */
	private static final Logger LOGGER =  Logger.getLogger(GameControllerTest.class.getName());

	/** SESSION ID. */
	private static final String SESSION_ID = "AJHDNUXDIAJDO";

	/** Game controller. */
	@Mock
	private GameController mockController;

	/**
	 * Actions before test.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockController = Mockito.spy(new GameController());
	}

	/**
	 * Actions after test.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		//do nothing
	}

	/**
	 * Test playRoundTest_01.
	 */
	@Test
	public void playRoundTest01() {
		LOGGER.log(Level.INFO, "[Init][playRoundTest_01][OK] Playing one round.");
		try {

			Mockito.doReturn(SESSION_ID).when(mockController)
			.getUserSessionId();

			//Playing one round
			mockController.playRound();

			//Getting results.
			List<GameRound> roundList = mockController.getUserRoundsList();

			//List should contain only one round.
			Assert.assertEquals(roundList.size(), 1);

			GameRound round = roundList.get(0);
			//First player choice could be any value from GameValues
			Assert.assertTrue(Arrays.asList(GameValues.values())
					.contains(GameValues.valueOf(round.getFirstPlayerChoice())));

			//Second player choice must be Rock value.
			Assert.assertTrue(round.getSecondPlayerChoice()
					.equals(GameValues.ROCK.name()));

			//Checking global score
			checkGlobalScrore(mockController);

		} catch (Throwable th) {
			Assert.fail(th.getMessage());
		}

		LOGGER.log(Level.INFO, "[End][playRoundTest_01][OK] Playing one round.");
	}

	/**
	 * Test playRoundTest_02.
	 */
	@Test
	public void playRoundTest02() {
		LOGGER.log(Level.INFO, "[Init][playRoundTest_02][KO] Playing one round.");
		try {
			//An unexpected error throws an exception.
			Mockito.doThrow(new NullPointerException()).when(mockController).getUserSessionId();
			mockController.playRound();
		} catch (Throwable th) {
			Assert.assertTrue(true);
		}
		LOGGER.log(Level.INFO, "[End][playRoundTest_02][KO] Playing one round.");
	}

	/**
	 * Test restartTest_01.
	 */
	@Test
	public void restartTest01() {
		LOGGER.log(Level.INFO, "[Init][restartTest_01][OK] Restarting the game.");
		try {

			Mockito.doReturn(SESSION_ID).when(mockController).getUserSessionId();

			//Playing one round
			mockController.playRound();

			//Restarting the game.
			mockController.restart();

			//Getting results.
			List<GameRound> roundList = mockController.getUserRoundsList();

			//List of actual rounds should be empty.
			Assert.assertTrue(roundList.isEmpty());

			//Checking global score.
			//Should be the same as in playRoundTest_01.
			checkGlobalScrore(mockController);

		} catch (Throwable th) {
			Assert.fail(th.getMessage());
		}
		LOGGER.log(Level.INFO, "[End][restartTest_01][OK] Restarting the game.");
	}

	/**
	 * Test restartTest_01.
	 */
	@Test
	public void restartTest02() {
		LOGGER.log(Level.INFO, "[Init][restartTest_02][KO] Restarting the game.");

		try {
			//An unexpected error throws an exception.
			Mockito.doThrow(new NullPointerException())
				.when(mockController).getUserSessionId();
			mockController.restart();
		} catch (Throwable th) {
			Assert.assertTrue(true);
		}

		LOGGER.log(Level.INFO, "[End][restartTest_02][KO] Restarting the game.");
	}

	/**
	 * Method which check global score.
	 * @param controller GameController
	 */
	private void checkGlobalScrore(final GameController controller) {

		//Getting global score.
		GlobalScore globalScore = controller.getGlobalScore();

		//Total rounds should be equal to 1.
		Assert.assertTrue(globalScore.getTotalRounds()
				.equals(new Integer(1)));

		//Rest of values should equals 1.
		Assert.assertTrue(globalScore.getFirstPlayerWins()
				+ globalScore.getSecondPlayerWins()
				+ globalScore.getTotalDraws() == 1);
	}

}
