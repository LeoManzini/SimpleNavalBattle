package br.com.leomanzini.naval.entities;

public class Player {
	
	private String name;
	private int playerBoard[][];
	private int coordinateX;
	private int coordinateY;
	private int playerShips;
	private boolean human;
	private String playerShot;
	private int playerPosition[];
	private final int XPOSITION = 0;
	private final int YPOSITION = 1;
	
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
	
	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
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

	public String getPlayerShot() {
		return playerShot;
	}

	public void setPlayerShot(String playerShot) {
		this.playerShot = playerShot;
	}

	public int[] getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(int[] playerPosition) {
		this.playerPosition = playerPosition;
	}

	public int getXPOSITION() {
		return XPOSITION;
	}

	public int getYPOSITION() {
		return YPOSITION;
	}
}
