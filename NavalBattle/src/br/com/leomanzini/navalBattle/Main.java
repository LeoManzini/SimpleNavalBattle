package br.com.leomanzini.navalBattle;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	//Declaring some global variables
	static String namePlayer1, namePlayer2;
	static int coordinateX, coordinateY, numberOfShips, maxNumberOfShips;
	static int boardPlayer1[][], boardPlayer2[][];
	static Scanner sc = new Scanner(System.in);
	
	public static void setBoardSize () {
		sc = null;
		for(;;) {
			boolean ex = false;
			try {
				sc = new Scanner(System.in);
				System.out.println("Enter with the board lines: ");
				coordinateX = sc.nextInt();
				
				System.out.println("Enter with the board columns: ");
				coordinateY = sc.nextInt();
				if (coordinateY > 26 || 0 > coordinateY) {
					while (coordinateY > 26 || 0 > coordinateY) {
						System.out.println("Max number of columns is 26, enter with a valid input!");
						coordinateY = sc.nextInt();
					}
				}
				ex = true;
			} catch(InputMismatchException err) {
				System.out.println("Enter with a integer number!\n");
			}
			if(ex) {
				break;
			}
		}
	}
	
	public static void setPlayerName () {
		System.out.println("Enter with the player 1 name: ");
		namePlayer1 = sc.next();
		System.out.println("Enter with the player 2 name: ");
		namePlayer2 = sc.next();
	}
	
	public static void calculateMaxNumberOfShips () {
		maxNumberOfShips = (coordinateX * coordinateY) / 3;
	}
	
	public static void setNumberOfShips () {
		sc = null;
		for(;;) {
			boolean ex = false;
			try {
				sc = new Scanner(System.in);
				System.out.println("Max number of ships for the current board size: " + maxNumberOfShips);
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
						if (columns == 0) {
							newBoard[x][y] = 1;
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
	
	public static void insertShipsAtPlayersBoard () {
		boardPlayer1 = insertShipsAtNewBoard();
		boardPlayer2 = insertShipsAtNewBoard();
	}
	
	public static void print(String playerName, int[][] board, boolean yourBoard) {
		System.out.println("|----- " + playerName + " -----|");
		String boardLetter = "    ";
		char columnLetter = 65;
		for(int i = 0; i < coordinateY; i++) {
			boardLetter += (columnLetter++) + " ";
		}
		System.out.println(boardLetter);
		String boardLine = "";
		int lineNumber = 1;
		for (int line [] : board) {
			if (lineNumber < 10) {
				boardLine = (lineNumber++) + "  |";
			} else {
				boardLine = (lineNumber++) + " |";
			}
			for (int column : line) {
				switch (column) {
					case 0 : // Don't have nothing
						boardLine += " |";
						break;
					case 1 : // Have a ship in this place
						if (yourBoard) {
							boardLine += "S|";
							break;
						} else {
							boardLine += " |";
							break;
						}
					case 2 : // Wrong shot
						boardLine += "X|";
						break;
					case 3 : // Hit the shot
						boardLine += "D|";
						break;
				}
			}
			System.out.println(boardLine);
		}
	}
	
	public static void playerAction () {
		System.out.println("Enter with your shot position: ");
		String playerShot = sc.next();
		
		int totalNumbers = (coordinateY > 10) ? 2 : 1;
		
		String checkExpression = "^[A-Za-z]{1}[0-9]{" + totalNumbers + "}$";
		
		if (playerShot.matches(checkExpression)) {
			String shot = playerShot.toLowerCase();
			int positionX = shot.charAt(0) - 97;
			int positionY = Integer.parseInt(shot.substring(1)) - 1;
			
			if(validatePlayerPosition(positionX, positionY)) {
				System.out.println("Valid coordinate.");
			}
					
		} else {
			System.out.println("Invalid Coordinate.");
		}
	}
	
	public static boolean validatePlayerPosition(int positionX, int positionY) {
		boolean feedback = true;
		if(positionX > coordinateX -1) {
			feedback = false;
			System.out.println("Letter position can't be higher than: " + (char) (coordinateX + 64));
		}
		if(positionY > coordinateY) {
			feedback = false;
			System.out.println("Number position can't be higher than: " + coordinateY);
		}
		return feedback;
	}
	
	public static void printBoard () {
		print(namePlayer1, boardPlayer1, true);
		System.out.println();
		print(namePlayer2, boardPlayer2, false);
	}
	
	public static void main(String args[]) {
		setPlayerName();
		setBoardSize();
		calculateMaxNumberOfShips();
		setPlayerBoard();
		setNumberOfShips();
		insertShipsAtPlayersBoard();
		printBoard();
		playerAction();
	}
}
