package br.com.leomanzini.entities;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.utils.ShipPositions;

public class Board {
	
	private static final Logger LOG = LogManager.getLogger(Board.class);
	private int boardSize;
	private int coordinateX;
	private int coordinateY;
	private int playerOneBoard[][];
	private int playerTwoBoard[][];
	private ShipPositions positions;
	private Scanner sc = new Scanner(System.in);
	
	public Board(int coordinateX, int coordinateY) {
		validateCoordinates(coordinateX, coordinateY);
	}
	
	public void validateCoordinates(int coordinateX, int coordinateY) {
		while(true) {
			boolean ex = false;
			try {
				if (coordinateX > 26 || 0 > coordinateX) {
					while (coordinateX > 26 || 0 > coordinateX) {
						LOG.info("Max number of lines is 26, enter with a valid input!");
						coordinateX = sc.nextInt();
					}
				}
				LOG.info("Enter with the board columns: ");
				coordinateY = sc.nextInt();
				ex = true;
			} catch(InputMismatchException e) {
				LOG.error("Enter with a integer number!\n", e);
			}
			if(ex) {
				this.coordinateX = coordinateX;
				this.coordinateY = coordinateY;
				break;
			}
		}  
	}
	
	public void setPlayerBoard () {
		this.playerOneBoard = createVoidBoard();
		this.playerTwoBoard = createVoidBoard();
	}
	
	public int[][] createVoidBoard () {
		return new int [this.coordinateX][this.coordinateY];
	}
	
	public int[][] insertShipsAtNewBoard (int numberOfShips) {
		int[][] newBoard = createVoidBoard();
		int remainingNumberOfShips = numberOfShips;
		Random randNumber = new Random();
		int x = 0, y = 0;
		do {
			x = 0;
			y = 0;
			for (int[] lines : newBoard) {
				for (int columns : lines) {
					if (randNumber.nextInt(100) <= 10) {
						if (columns == ShipPositions.NOTHING.getIntValue()) {
							newBoard[x][y] = ShipPositions.SHIP.getIntValue();
							remainingNumberOfShips--;
							break;
						}
						if (remainingNumberOfShips <= 0) {
							break;
						}
					}
					y++;
				}
				y = 0;
				x++;
				if (remainingNumberOfShips <= 0) {
					break;
				}
			}
		} while(remainingNumberOfShips > 0);
		return newBoard;
	}
	
	public void insertShipsAtPlayersBoard (int numberOfShips) {
		this.playerOneBoard = insertShipsAtNewBoard(numberOfShips);
		this.playerTwoBoard = insertShipsAtNewBoard(numberOfShips);
	}
	
	public void printNumbering () {
		int columnNumber = 1;
		String boardNumber = "   ";
		for(int i = 0; i < this.coordinateY; i++) {
			boardNumber += (columnNumber++) + " ";
		}
		System.out.println(boardNumber);
	}
}
