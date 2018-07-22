package com.cyclum.task.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.cyclum.task.beans.GameRound;
import com.cyclum.task.beans.GlobalScore;

/**
 * Game controller.
 * 
 * @author Mdraa
 *
 */
public class GameController {
	
	/** Map instance containing actual rounds for each session. */
	private Map<String, List<GameRound>> actRoundsMapBySessionId;
	
	/** Global score. */
	private GlobalScore globalScore;
	
	/**
	 * Contructor.
	 */
	public GameController() {
		super();
		this.actRoundsMapBySessionId = new HashMap<String, List<GameRound>>();
		this.globalScore = new GlobalScore();
	}
	
	/**
	 * Play round method.
	 * 
	 * @return
	 */
	public String playRound() {
		//SessionId
		String sessionId = getUserSessionId();
			
		List<GameRound> userRoundsList = actRoundsMapBySessionId.get(sessionId);
		GameRound round = new GameRound();
		
		if(userRoundsList == null) {
			userRoundsList = new ArrayList<GameRound>();
		}
		
		userRoundsList.add(round);
		actRoundsMapBySessionId.put(sessionId, userRoundsList);
		
		//Calculate global score.
		getGlobalScore().updateGlobalScore(round);
			
		return null;
	}

	/**
	 * Method which restart actual game.
	 */
	public String restart() {
		
		String sessionId = getUserSessionId();
		if (actRoundsMapBySessionId.containsKey(sessionId)) {
			actRoundsMapBySessionId.get(sessionId).clear();
		}
		
		return null;
	}

	/**
	 * @return the userRoundsList
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
	 * Method which returns user sessionId.
	 * 
	 * @return sessionId
	 */
	public String getUserSessionId() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
	}

}
