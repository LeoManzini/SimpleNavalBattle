package br.com.leomanzini.naval.entities;

public class Player {
	
	private String name;
	private int playerBoard[][];
	private int playerShips;
	private boolean human;
	
	public Player() {
		setName("Computer");
		setHuman(false);
	}
	
	public Player(String name) {
		setName(name);
		setHuman(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[][] getPlayerBoard() {
		return playerBoard;
	}

	public void setPlayerBoard(int[][] playerBoard) {
		this.playerBoard = playerBoard;
	}

	public int getPlayerShips() {
		return playerShips;
	}

	public void setPlayerShips(int playerShips) {
		this.playerShips = playerShips;
	}

	public boolean getHuman() {
		return human;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}
}
