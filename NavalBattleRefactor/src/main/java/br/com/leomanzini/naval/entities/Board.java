package br.com.leomanzini.naval.entities;

import java.util.Random;
import java.util.Scanner;

import br.com.leomanzini.naval.utils.ShipPositions;

public class Board {

	private int coordinateX;
	private int coordinateY;
	private int gameBoard;
	private Scanner sc = new Scanner(System.in);

	public Board() {
	}

	public Board(int coordinateX, int coordinateY) {
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}

	public int[][] createVoidBoard() {
		return new int[this.coordinateX][this.coordinateY];
	}

	public int[][] insertShipsAtPlayerBoard(int numberOfShips) {
		int[][] fillBoard = createVoidBoard();
		Random randNumber = new Random();
		int remainingNumberOfShips = numberOfShips;

		do {
			int x = 0, y = 0;
			for (int[] lines : fillBoard) {
				for (int columns : lines) {
					if (randNumber.nextInt(100) <= 10) {
						if (columns == ShipPositions.NOTHING.getIntValue()) {
							fillBoard[x][y] = ShipPositions.SHIP.getIntValue();
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
		} while (remainingNumberOfShips > 0);
		return fillBoard;
	}

	public void printNumbering() {
		int columnNumber = 1;
		String boardNumber = "    ";
		for (int i = 0; i < coordinateY; i++) {
			if (columnNumber < 10) {
				boardNumber += (columnNumber++) + "   ";
			} else {
				boardNumber += (columnNumber++) + "  ";
			}
		}
		System.out.println(boardNumber);
	}

	public void printBoard(Player player) {
		System.out.println("|---------- Printing " + player.getName() + "'s board ----------|");
		printNumbering();
		String boardLine = "";
		char lineLetter = 65;
		for (int line[] : player.getPlayerBoard()) {
			boardLine = (lineLetter++) + " | ";
			for (int column : line) {
				switch (column) { 
				case 0:
					boardLine += "  | ";
					break;
				case 1:
					if (player.getHuman()) {
						boardLine += "S | ";
						break;
					} else {
						boardLine += "  | ";
						break;
					}
				case 2:
					boardLine += "X | ";
					break;
				case 3:
					boardLine += "D | ";
					break;
				}
			}
			System.out.println(boardLine);
		}
		System.out.println();
	}

	public void close() {
		sc.close();
	}

	public int getGameBoard() {
		return gameBoard;
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
}
