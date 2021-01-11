package br.com.leomanzini.navalBattle;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	//Declaring some global variables
	static int coordinateX, coordinateY, numberOfShips, maxNumberOfShips;
	static int boardPlayer1[][], boardPlayer2[][];
	
	public static void getBoardSize () {
		Scanner sc = null;
		for(;;) {
			boolean ex = false;
			try {
				sc = new Scanner(System.in);
				System.out.println("Enter with the board lines: ");
				coordinateX = sc.nextInt();
				
				System.out.println("Enter with the board columns: ");
				coordinateY = sc.nextInt();
				
				ex = true;
			} catch(InputMismatchException err) {
				System.out.println("Enter with a integer number!\n");
			}
			if(ex) {
				break;
			}
		}
	}
	
	public static void calculateMaxNumberOfShips () {
		maxNumberOfShips = (coordinateX * coordinateY) / 3;
	}
	
	public static void getNumberOfShips () {
		Scanner sc = null;
		for(;;) {
			boolean ex = false;
			try {
				sc = new Scanner(System.in);
				System.out.println("Max number of ships for the currents boards: " + maxNumberOfShips);
				System.out.println("Enter with the number of ships: ");
				numberOfShips = sc.nextInt();
				
				if(numberOfShips <= 0 || numberOfShips > maxNumberOfShips) {
					System.out.println("Enter with a valid number of ships!\n");
				} else {
					ex = true;
					break;
				}
			} catch(InputMismatchException err) {
				System.out.println("Enter with a integer number!\n");
			}
			if(ex) {
				break;
			}
		}
	}
	
	public static int[][] createVoidBoard () {
		return new int [coordinateX][coordinateY];
	}
	
	public static void setPlayerBoard () {
		boardPlayer1 = createVoidBoard();
		boardPlayer2 = createVoidBoard();
	}
	
	public static int[][] insertShipsAtNewBoard () {
		int remainingNumberOfShips = numberOfShips;
		Random randNumber = new Random();
		int[][] newBoard = createVoidBoard();
		do {
			for (int[] lines : newBoard) {
				for (int columns : lines) {
					if (randNumber.nextInt(100) <= 10) {
						if (columns == 0) {
							columns = 1;
							remainingNumberOfShips--;
							break;
						}
						if (remainingNumberOfShips <= 0) {
							break;
						}
					}
				}
				if (remainingNumberOfShips <= 0) {
					break;
				}
			}
		} while(remainingNumberOfShips > 0);
		System.out.println("Player 1 ships inserted!");
		return newBoard;
	}
	
	public static void insertShipsAtPlayersBoard () {
		boardPlayer1 = insertShipsAtNewBoard();
		boardPlayer2 = insertShipsAtNewBoard();
	}
	
	public static void main(String args[]) {
		getBoardSize();
		calculateMaxNumberOfShips();
		setPlayerBoard();
		getNumberOfShips();
		insertShipsAtPlayersBoard();
	}
}
