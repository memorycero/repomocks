/**
 * GameController.java 1.0
 */
package com.cyclum.task.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import com.cyclum.task.beans.GameRound;
import com.cyclum.task.beans.GlobalScore;

/**
 * Game controller.
 * @author Mdraa
 *
 */
public class GameController implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/** Logger */
	private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

	/** Map instance containing actual rounds for each session. */
	private volatile Map<String, List<GameRound>> actRoundsMapBySessionId;

	/** Global score. */
	private GlobalScore globalScore;

	/**
	 * Contructor.
	 */
	public GameController() {
		super();
		this.actRoundsMapBySessionId = new ConcurrentHashMap<String, List<GameRound>>();
		this.globalScore = new GlobalScore();
	}

	/**
	 * Play round method.
	 * @return result
	 */
	public synchronized String playRound() {
		try {
			// SessionId
			String sessionId = getUserSessionId();

			//We retrieve all rounds of current user.
			List<GameRound> userRoundsList = actRoundsMapBySessionId.get(sessionId);
			//New round
			GameRound round = new GameRound();

			//If the list is null we create a new one.
			if (userRoundsList == null) {
				userRoundsList = new ArrayList<GameRound>();
			}

			userRoundsList.add(round);
			actRoundsMapBySessionId.put(sessionId, userRoundsList);

			// Calculate global score.
			getGlobalScore().updateGlobalScore(round);
		} catch (Throwable th) {
			LOGGER.log(Level.SEVERE, "Error playing a round !!!!");
		}

		return null;
	}

	/**
	 * Restart method.
	 * @return result
	 */
	public String restart() {
		try {
			String sessionId = getUserSessionId();
			if (actRoundsMapBySessionId.containsKey(sessionId)) {
				actRoundsMapBySessionId.get(sessionId).clear();
			}
		} catch (Throwable th) {
			LOGGER.log(Level.SEVERE, "Error restarting the game !");
		}

		return null;
	}

	/**
	 * @return actual user rounds list.
	 */
	public List<GameRound> getUserRoundsList() {
		return actRoundsMapBySessionId.get(getUserSessionId());
	}

	/**
	 * @return the globalScore
	 */
	public GlobalScore getGlobalScore() {
		return globalScore;
	}

	/**
	 * return sessionId.
	 * @return the sessionId
	 */
	public String getUserSessionId() {
		return FacesContext.getCurrentInstance()
				.getExternalContext().getSessionId(true);
	}

}
