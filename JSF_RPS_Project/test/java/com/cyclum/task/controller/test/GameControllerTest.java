package com.cyclum.task.controller.test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for GameController.
 * 
 * 
 * @author Mdraa
 *
 */
public class GameControllerTest {

	/** Logger */
	private static final Logger LOGGER =  Logger.getLogger(GameControllerTest.class.getName());
	
	/** Game controller. */
	private GameController controller;
	
	
	@Before
	public void setUp() throws Exception {
		//do nothing
	}

	@After
	public void tearDown() throws Exception {
		//do nothing
	}
	
	/**
	 * Test playRoundTest_01
	 */
	@Test
	public void playRoundTest_01() {
		LOGGER.log(Level.INFO, "[Init][playRoundTest_01][OK] Playing one round.");
		try {
			
			//Playing one round
			controller.playRound();
			
			//Getting results.
			List<GameRound> roundList = controller.getUserRoundsList();
			
			//List should contain only one round.
			Assert.assertEquals(roundList.size(), 1);
			
			GameRound round = roundList.get(0);
			//First player choice could be any value from GameValues
			Assert.assertTrue(Arrays.asList(GameValues.values()).contains(GameValues.valueOf(round.getFirstPlayerChoice())));
			
			//Second player choice must be Rock value.
			Assert.assertTrue(round.getSecondPlayerChoice().equals(GameValues.ROCK.name()));
			
			//Checking global score
			checkGlobalScrore(controller);
			
		} catch(Throwable th) {
			Assert.fail(th.getMessage());
		}
		
		LOGGER.log(Level.INFO, "[End][playRoundTest_01][OK] Playing one round.");
	}
	
	/**
	 * Test restartTest_01()
	 */
	@Test
	public void restartTest_01() {
		LOGGER.log(Level.INFO, "[Init][restartTest_01][OK] Restarting the game.");
		try {
			
			//Playing one round
			controller.playRound();
			
			//Restarting the game.
			controller.restart();
			
			//Getting results.
			List<GameRound> roundList = controller.getUserRoundsList();
			
			//List of actual rounds should be empty.
			Assert.assertTrue(roundList.isEmpty());
			
			//Checking global score. Should be the same as in playRoundTest_01.
			checkGlobalScrore(controller);
			
		} catch(Throwable th) {
			Assert.fail(th.getMessage());
		}
		LOGGER.log(Level.INFO, "[End][restartTest_01][OK] Restarting the game.");
	}
	
	/**
	 * Method which check global score.
	 */
	private void checkGlobalScrore(GameController controller) {
		
		//Getting global score.
		GlobalScore globalScore = controller.getGlobalScore();
		
		//Total rounds should be equal to 1.
		Assert.assertTrue(globalScore.getTotalRounds().equals(new Integer(1)));
		
		//Rest of values should equals 1.
		Assert.assertTrue(globalScore.getFirstPlayerWins()
				 + globalScore.getSecondPlayerWins()
				 + globalScore.getTotalDraws() == 1);
	}

}
