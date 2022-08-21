package br.com.leomanzini.naval.executor;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;
import br.com.leomanzini.naval.entities.Ships;
import br.com.leomanzini.naval.utils.ShipPositions;

public abstract class GameExecutor {

	private static final Scanner sc = new Scanner(System.in);
	
	public static void startGame(Board board, Player player1, Player computerPlayer, Ships ships) {
		try {
			board = instanciateGameMode();
			player1 = instantiateHumanPlayer();
			computerPlayer = instantiateComputerPlayer();
			ships = new Ships(board.getCoordinateX(), board.getCoordinateY());
			
			player1.setPlayerBoard(board.createVoidBoard());
			computerPlayer.setPlayerBoard(board.createVoidBoard());
			
			initiateNumberOfShips(ships);
			
			fillPlayerBoard(player1, board, ships);
			fillPlayerBoard(computerPlayer, board, ships);
			
			gameLoop(board, player1, computerPlayer);
			
			if(newGame()) {
				printStartMenu();
				startGame(board, player1, computerPlayer, ships);
			} else {
				closeAll();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	public static void printStartMenu() {
		System.out.println("##################################################");
		System.out.println("#                    Welcome to                  #");
		System.out.println("#                  Naval Battle!!                #");
		System.out.println("#                                                #");
		System.out.println("#          Author: Leonardo H. Manzini           #");
		System.out.println("##################################################");
	}
	
	public static void startOption() {
		System.out.println("Do you want to play a match? (Yes/No)");
		String option = sc.next();
		char letter = option.toUpperCase().charAt(0);
		switch (letter) {
		case 'Y':
			System.out.println("\nEnjoy the game!\n");
			break;
		default:
			System.out.println("\nWe hope to play with you later!\n");
			System.exit(0);
		}
	}

	public static void printGameRules() {
		System.out.println("Understanding the game board: ");
		System.out.println("S is a Ship;");
		System.out.println("X is a missed shot;");
		System.out.println("D is a destroyed ship.\n");
	}

	public static Board instanciateGameMode() throws InputMismatchException {
		int coordinateX = 2, coordinateY = 2;
		try {
			System.out.println("Choose a game board size");
			System.out.println("Enter with the number of lines to the board: ");
			coordinateX = sc.nextInt();
			coordinateX = validateCoordinateX(coordinateX);
			System.out.println("Enter with the number of columns to the board: ");
			coordinateY = sc.nextInt();
			coordinateY = validateCoordinateY(coordinateY, coordinateX);
			
		} catch (Exception e) {
			System.out.println("Enter with a integer number.");
			System.exit(-1);
		}
		return new Board(coordinateX, coordinateY);
	}
	
	public static int validateCoordinateX(int coordinateX) {
		while (true) {
			if (coordinateX > 26 | 0 >= coordinateX | coordinateX == 1) {
				while (coordinateX > 26 | 0 >= coordinateX | coordinateX == 1) {
					System.out.println("Max number of lines is 26 and min is 2.");
					System.out.println("Enter with a valid input: ");
					coordinateX = sc.nextInt();
				}
			} else {
				break;
			}
		}
		return coordinateX;
	}
	
	public static int validateCoordinateY(int coordinateY, int coordinateX) {
		while (true) {
			if (coordinateY > coordinateX | 0 >= coordinateY | coordinateY == 1) {
				while (coordinateY > coordinateX | 0 >= coordinateY | coordinateY == 1) {
					System.out.println("Max number of columns is the same as the lines and the min is 2.");
					System.out.println("Enter with a valid input: ");
					coordinateY = sc.nextInt();
				}
			} else {
				break;
			}
		}
		return coordinateY;
	}
	
	public static Player instantiateHumanPlayer() {
		System.out.println("Enter with the player name: ");
		String name = sc.next();

		return new Player(name);
	}

	public static Player instantiateComputerPlayer() {
		return new Player();
	}

	public static void initiateNumberOfShips(Ships ships) {
		System.out.println("\nThe max number of ships you can insert at the board is: " + ships.getMaxNumberOfShips());
		System.out.println("Enter with the number of ships you want to insert: ");
		int numberOfShips = sc.nextInt();
		ships.setNumberOfChips(numberOfShips);
		System.out.println();
	}

	public static void fillPlayerBoard(Player player, Board board, Ships ships) {
		player.setPlayerBoard(board.insertShipsAtPlayerBoard(ships.getNumberOfChips()));
		player.setCoordinateX(board.getCoordinateX());
		player.setCoordinateY(board.getCoordinateY());
		player.setPlayerShips(ships.getNumberOfChips());
	}

	public static void printPlayerBoard(Board board, Player player) {
		board.printBoard(player);
	}

	public static boolean humanPlayerAction(Player player, Player computerPlayer) {
		boolean validate = true;
		catchPlayerShot(player, computerPlayer);
		if (validatePlayerShot(player)) {
			computerPlayer.setPlayerPosition(catchPlayerPositions(player));
			if (validatePlayerPosition(computerPlayer)) {
				insertActionsToBoard(computerPlayer);
			} else {
				validate = false;
			}

		} else {
			validate = false;
			System.out.println("Invalid Coordinate.\n");
		}
		return validate;
	}

	public static void catchPlayerShot(Player player, Player computerPlayer) {
		System.out.println("Enter with your shot position: ");
		String playerShot = sc.next();
		playerShot.toLowerCase();
		playerShot = validateShot(playerShot, computerPlayer);
		player.setPlayerShot(playerShot);
	}
	
	private static String validateShot(String playerShot, Player computerPlayer) {
		int x, y;
		while(true) {
			try { 
				x = playerShot.charAt(0) - 97;
				y = Integer.parseInt(playerShot.substring(1)) - 1;
				
				if (computerPlayer.getPlayerBoard()[x][y] == ShipPositions.MISSED.getIntValue()
						|| computerPlayer.getPlayerBoard()[x][y] == ShipPositions.HIT.getIntValue()) 
				{
					System.out.println("You already entered with this position, type a new: ");
					playerShot = sc.next();
					x = playerShot.charAt(0) - 97;
					y = Integer.parseInt(playerShot.substring(1)) - 1;
					
					if (x >= computerPlayer.getCoordinateX() || y >= computerPlayer.getCoordinateY()) {
						System.out.println("Invalid coordinate.");
						System.out.println("Type a new position: ");
						playerShot = sc.next();
						x = playerShot.charAt(0) - 97;
						y = Integer.parseInt(playerShot.substring(1)) - 1;
					} else {
						validateShot(playerShot, computerPlayer);
						break;
					}
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("\nInvalid coordinate.");
				System.out.println("The correct format is letter + number, like 'a1' or 'A1'");
				System.out.println("Letter position can't be higher than: " + (char) (computerPlayer.getCoordinateX() + 64));
				System.out.println("Number position can't be higher than: " + computerPlayer.getCoordinateY());
				System.out.println("\nType a new position: ");
				playerShot = sc.next();
				x = playerShot.charAt(0) - 97;
				y = Integer.parseInt(playerShot.substring(1)) - 1;
				continue;
			}
		}
		return playerShot;
	}

	public static boolean validatePlayerShot(Player player) {
		int totalNumbers = (player.getCoordinateY() > 10) ? 2 : 1;
		String checkExpression = "^[A-Za-z]{1}[0-9]{" + totalNumbers + "}$";
		return player.getPlayerShot().matches(checkExpression);
	}

	public static int[] catchPlayerPositions(Player player) {
		String shot = player.getPlayerShot().toLowerCase();
		int[] playerPosition = new int[2];
		playerPosition[player.getXPOSITION()] = shot.charAt(0) - 97;
		playerPosition[player.getYPOSITION()] = Integer.parseInt(shot.substring(1)) - 1;
		return playerPosition;
	}

	public static boolean validatePlayerPosition(Player player) {
		boolean feedback = true;
		if (player.getPlayerPosition()[player.getXPOSITION()] > player.getCoordinateX() - 1) {
			feedback = false;
			System.out.println("Letter position can't be higher than: " + (char) (player.getCoordinateX() + 64));
			System.out.println();
		}
		if (player.getPlayerPosition()[player.getYPOSITION()] > player.getCoordinateY()) {
			feedback = false;
			System.out.println("Number position can't be higher than: " + player.getCoordinateY());
			System.out.println();
		}
		return feedback;
	}

	public static void computerPlayerAction(Player player1, Player computerPlayer) {
		catchComputerShot(player1, computerPlayer);
		insertActionsToBoard(player1);
	}

	public static void catchComputerShot(Player player, Player computerPlayer) {
		int[] playerPosition = new int[2];
		playerPosition[player.getXPOSITION()] = getRandomShot(player.getCoordinateX());
		playerPosition[player.getYPOSITION()] = getRandomShot(player.getCoordinateY());
		while (true) {
			if (player.getPlayerBoard()[playerPosition[player.getXPOSITION()]][player
					.getYPOSITION()] == ShipPositions.MISSED.getIntValue()
					|| player.getPlayerBoard()[playerPosition[player.getXPOSITION()]][player
							.getYPOSITION()] == ShipPositions.HIT.getIntValue()) {
				playerPosition[player.getXPOSITION()] = getRandomShot(player.getCoordinateX());
				playerPosition[player.getYPOSITION()] = getRandomShot(player.getCoordinateY());
			} else {
				break;
			}
		}
		player.setPlayerPosition(playerPosition);
	}

	public static int getRandomShot(int limit) {
		Random computerCoordinates = new Random();
		int numberGenerated = computerCoordinates.nextInt(limit);
		return (numberGenerated == limit) ? --numberGenerated : numberGenerated;
	}

	public static void insertActionsToBoard(Player player) {
		if (player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]][player.getPlayerPosition()[player
				.getYPOSITION()]] == ShipPositions.SHIP.getIntValue()) {
			player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]][player.getPlayerPosition()[player
					.getYPOSITION()]] = ShipPositions.HIT.getIntValue();
			player.setPlayerShips(player.getPlayerShips() - 1);
			if (player.getHuman()) {
				System.out.println("\nComputer hit a ship!\n");
			} else {
				System.out.println("\nYou hit a ship!\n");
			}
		} else {
			player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]][player.getPlayerPosition()[player
					.getYPOSITION()]] = ShipPositions.MISSED.getIntValue();
			if (player.getHuman()) {
				System.out.println("\nComputer missed the shot...\n");
			} else {
				System.out.println("\nYou missed the shot...\n");
			}
		}
	}

	public static boolean winCondition(boolean loop, Player player1, Player computerPlayer) {
		if (computerPlayer.getPlayerShips() <= 0) {
			System.out.println(player1.getName() + " is the winner!\n");
			return false;
		}
		if (player1.getPlayerShips() <= 0) {
			System.out.println(computerPlayer.getName() + " is the winner!\n");
			return false;
		}
		return true;
	}

	public static void gameLoop(Board board, Player player1, Player computerPlayer) {
		boolean loop = true;
		do {
			printPlayerBoard(board, player1);
			printPlayerBoard(board, computerPlayer);
			if (humanPlayerAction(player1, computerPlayer)) {
				computerPlayerAction(player1, computerPlayer);
				if (!(loop = winCondition(loop, player1, computerPlayer)))
					break;
			}
		} while (loop);
		printPlayerBoard(board, player1);
		printPlayerBoard(board, computerPlayer);
	}
	
	public static boolean newGame() {
		System.out.println("\nDo you want to play a new match? (Yes/No)");
		String option = sc.next();
		char letter = option.toUpperCase().charAt(0);
		switch (letter) {
		case 'Y':
			System.out.println("\nEnjoy the new game!\n");
			return true;
		default:
			System.out.println("\nThank you for play! We hope to see you again!");
			System.out.println("Leonardo Henrique Manzini - Mid System Developer.");
			System.out.println("Find me at GitHub: https://github.com/LeoManzini");
			System.out.println("Find me at Linkedin: https://www.linkedin.com/in/leonardo-manzini/");
			return false;
		}
	}

	private static void closeAll() {
		sc.close();
	}
}
