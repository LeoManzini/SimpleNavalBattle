package br.com.leomanzini.naval.entities;

public class Player {
	
	private String name;
	private int playerBoard[][];
	private int playerShips;
	
	public Player() {
		setName("Computer");
	}
	
	public Player(String name) {
		setName(name);
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
}
