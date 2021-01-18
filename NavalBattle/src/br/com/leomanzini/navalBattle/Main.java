package br.com.leomanzini.navalBattle;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	/**
	 * Board
	 * 0 - Nothing there;
	 * 1 - Ship;
	 * 2 - Missed the shot;
	 * 3 - Hit the shot.
	 */
	static final int NOTHING = 0;
	static final int SHIP = 1;
	static final int MISSED = 2;
	static final int HIT = 3;
	static final int XPOSITION = 0;
	static final int YPOSITION = 1;
	
	/**
	 * Declaring some global variables
	 */
	static String namePlayer1, namePlayer2;
	static int coordinateX, coordinateY, numberOfShips, maxNumberOfShips;
	static int boardPlayer1[][], boardPlayer2[][];
	static Scanner sc = new Scanner(System.in);
	static int player1Ships, player2Ships;
	
	/**
	 * Set the size of the board for both players
	 */
	public static void setBoardSize () {
		sc = null;
		for(;;) {
			boolean ex = false;
			try {
				sc = new Scanner(System.in);
				System.out.println("Enter with the board lines: ");
				coordinateX = sc.nextInt();
				if (coordinateX > 26 || 0 > coordinateY) {
					while (coordinateX > 26 || 0 > coordinateX) {
						System.out.println("Max number of lines is 26, enter with a valid input!");
						coordinateX = sc.nextInt();
					}
				}
				
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
	
	/**
	 * Set the both player names
	 */
	public static void setPlayerName () {
		System.out.println("Enter with the player 1 name: ");
		namePlayer1 = sc.next();
		System.out.println("Enter with the player 2 name: ");
		namePlayer2 = sc.next();
	}
	
	/**
	 * Calculate the max number of ships for the current board size
	 */
	public static void calculateMaxNumberOfShips () {
		maxNumberOfShips = (coordinateX * coordinateY) / 3;
	}
	
	/**
	 * Set the number of ships for both players
	 */
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
	
	public static void setTotalPlayerShipsCounter() {
		player1Ships = numberOfShips;
		player2Ships = numberOfShips;
	}
	
	/**
	 * Create the void board
	 * @return matrix with the board size
	 */
	public static int[][] createVoidBoard () {
		return new int [coordinateX][coordinateY];
	}
	
	/**
	 * Set the board for the players
	 */
	public static void setPlayerBoard () {
		boardPlayer1 = createVoidBoard();
		boardPlayer2 = createVoidBoard();
	}
	
	/**
	 * Insert ships into an empty board
	 * @return a new board ready to battle!
	 */
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
						if (columns == NOTHING) {
							newBoard[x][y] = SHIP;
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
	
	/**
	 * Insert the player ships into their boards
	 */
	public static void insertShipsAtPlayersBoard () {
		boardPlayer1 = insertShipsAtNewBoard();
		boardPlayer2 = insertShipsAtNewBoard();
	}
	
	/**
	 * Print the board number header
	 */
	public static void printNumbering () {
		int columnNumber = 1;
		String boardNumber = "   ";
		for(int i = 0; i < coordinateY; i++) {
			boardNumber += (columnNumber++) + " ";
		}
		System.out.println(boardNumber);
	}
	
	/**
	 * Print the board letter header
	 * @param playerName
	 * @param board
	 * @param yourBoard
	 */
	public static void print(String playerName, int[][] board, boolean yourBoard) {
		System.out.println("|----- " + playerName + " -----|");
		printNumbering();
		String boardLine = "";
		char lineLetter = 65;
		for (int line [] : board) {
			boardLine = (lineLetter++) + " |";
			/*if (lineLetter < 10) {
				boardLine = (lineLetter++) + "  |";
			} else {
				boardLine = (lineLetter++) + " |";
			}*/
			for (int column : line) {
				switch (column) {
					case NOTHING : 
						boardLine += " |";
						break;
					case SHIP : 
						if (yourBoard) {
							boardLine += "S|";
							break;
						} else {
							boardLine += " |";
							break;
						}
					case MISSED : 
						boardLine += "X|";
						break;
					case HIT :
						boardLine += "D|";
						break;
				}
			}
			System.out.println(boardLine);
		}
	}
	
	/**
	 * Receive the coordinates for the shot
	 * @return the string with the shot coordinate
	 */
	public static String setPlayerShot() {
		System.out.println("Enter with your shot position: ");
		return sc.next();
	}
	
	/**
	 * Validate if the shot that the player inserted is correct
	 * @param playerShot String with the shot coordinates
	 * @return true if is correct, false if isn't
	 */
	public static boolean validatePlayerShot(String playerShot) {
		int totalNumbers = (coordinateY > 10) ? 2 : 1;
		String checkExpression = "^[A-Za-z]{1}[0-9]{" + totalNumbers + "}$";
		return playerShot.matches(checkExpression);
	}
	
	/**
	 * Handles and separates the shot coordinates entered into two: x coordinate and y coordinate
	 * @param playerShot shot coordinates
	 * @return an array with the first position holding the x coordinate and the second the y coordinate
	 */
	public static int[] getPlayerPositions(String playerShot) {
		String shot = playerShot.toLowerCase();
		int[] playerPosition = new int[2];
		playerPosition[XPOSITION] = shot.charAt(0) - 97;
		playerPosition[YPOSITION] = Integer.parseInt(shot.substring(1)) - 1;
		return playerPosition;
	}
	
	/**
	 * Validate if the inserted coordinates for a shot can be found at the board
	 * @param playerPosition array with the shot coordinates
	 * @return true if they can be found and false if cannot
	 */
	public static boolean validatePlayerPosition(int[] playerPosition) {
		boolean feedback = true;
		if(playerPosition[XPOSITION] > coordinateX -1) {
			feedback = false;
			System.out.println("Letter position can't be higher than: " + (char) (coordinateX + 64));
		}
		if(playerPosition[YPOSITION] > coordinateY) {
			feedback = false;
			System.out.println("Number position can't be higher than: " + coordinateY);
		}
		return feedback;
	}
	
	/**
	 * Insert the each player shot, if you're the player 1, reach the shots to the player 2 board and vice versa
	 * @param playerPosition array with the shot coordinates
	 * @param player which player are shooting
	 */
	public static void insertActionsToBoard (int[] playerPosition, int player) {
		if(player == 1) {
			if (boardPlayer2[playerPosition[XPOSITION]][playerPosition[YPOSITION]] == SHIP) {
				boardPlayer2[playerPosition[XPOSITION]][playerPosition[YPOSITION]] = HIT;
				player2Ships--;
				System.out.println("You hit a ship!");
			} else {
				boardPlayer2[playerPosition[XPOSITION]][playerPosition[YPOSITION]] = MISSED;
				System.out.println("You missed the shot...");
			}
		} else {
			if (boardPlayer1[playerPosition[XPOSITION]][playerPosition[YPOSITION]] == SHIP) {
				boardPlayer1[playerPosition[XPOSITION]][playerPosition[YPOSITION]] = HIT;
				player1Ships--;
				System.out.println("You hit a ship!");
			} else {
				boardPlayer1[playerPosition[XPOSITION]][playerPosition[YPOSITION]] = MISSED;
				System.out.println("You missed the shot...");
			}
		}
	}
	
	/**
	 * The actions of the both players
	 * @return true if the shot was correct and false if was not
	 */
	public static boolean playerAction () {
		boolean validate = true;
		String playerShot = setPlayerShot();
		if (validatePlayerShot(playerShot)) {
			int[] playerPosition = getPlayerPositions(playerShot);
			if(validatePlayerPosition(playerPosition)) {
				insertActionsToBoard(playerPosition, 1);
			} else {
				validate = false;
			}
					
		} else {
			validate = false;
			System.out.println("Invalid Coordinate.");
		}
		return validate;
	}
	
	/**
	 * Function to print the board
	 */
	public static void printBoard () {
		print(namePlayer1, boardPlayer1, true);
		System.out.println();
		print(namePlayer2, boardPlayer2, false);
	}
	
	public static void main(String args[]) {
		setPlayerName();
		setBoardSize();
		setPlayerBoard();
		calculateMaxNumberOfShips();
		setNumberOfShips();
		insertShipsAtPlayersBoard();
		setTotalPlayerShipsCounter();
		
		boolean activeGame = true;
		do {
			printBoard();
			if (playerAction()) {
				Random computerCoordinates = new Random();
				int[] playerPosition = new int[2];
				int numberGenerated = computerCoordinates.nextInt(coordinateX);
				playerPosition[XPOSITION] = (numberGenerated == coordinateX) ? --numberGenerated : numberGenerated;
				numberGenerated = computerCoordinates.nextInt(coordinateY);
				playerPosition[YPOSITION] = (numberGenerated == coordinateY) ? --numberGenerated : numberGenerated;
				insertActionsToBoard(playerPosition, 2);
			}
		} while(activeGame);
	}
}
