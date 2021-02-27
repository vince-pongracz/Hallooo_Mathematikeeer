package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class GameController {

	/**
	 * Default constructor
	 */
	public GameController() {
	}

	/**
	 * 
	 */
	private boolean gameIsRunning;

	/**
	 * 
	 */
	private int currentRound;

	/**
	 * 
	 */
	private int playersNum;

	/**
	 * 
	 */
	private int settlerNum;

	/**
	 * 
	 */
	private Set<AIRobot> robots;

	/**
	 * 
	 */
	private Set<Player> players;

	/**
	 * 
	 */
	private void dropSettlers() {
		// TODO implement here
	}

	/**
	 * 
	 */
	private void setupGame() {
		// TODO implement here
	}

	/**
	 * @return
	 */
	private Iterator<Player> getIterOnPlayers() {
		// TODO implement here
		return players.iterator();
	}

	/**
	 * @param name
	 */
	public void removePlayer(String name) {
		// TODO implement here
	}

	/**
	 * @param palyerLeaving
	 */
	public void leaveGame(Player palyerLeaving) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public int getRound() {
		// TODO implement here
		return 0;
	}

	/**
	 * 
	 */
	public void evaluateRound() {
		// TODO implement here
	}

	/**
	 * 
	 */
	private void evaluateFlair() {
		// TODO implement here
	}

	/**
	 * @param newPlayer
	 */
	public void addPlayer(Player newPlayer) {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void inGame() {
		// TODO implement here
	}

	/**
	 * 
	 */
	private void round() {
		// TODO implement here
	}

	/**
	 * @param bot 
	 * @return
	 */
	public boolean removeBot(AIRobot bot) {
		// TODO implement here
		return false;
	}

	/**
	 * @param bot
	 */
	public void addBot(AIRobot bot) {
		// TODO implement here
	}

}