package br.com.leomanzini.naval.executor;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;
import br.com.leomanzini.naval.entities.Ships;
import br.com.leomanzini.naval.utils.ShipPositions;

public abstract class GameExecutor {

	private static final Scanner sc = new Scanner(System.in);
	
	public static void printStartMenu() {
		System.out.println("##################################################");
		System.out.println("#                    Welcome to                  #");
		System.out.println("#                  Naval Battle!!                #");
		System.out.println("#                                                #");
		System.out.println("#          Author: Leonardo H. Manzini           #");
		System.out.println("##################################################");
		System.out.println("Do you want to play a match? (Yes/No)");
		String option = sc.next();
		char letter = option.toUpperCase().charAt(0);
		switch(letter) {
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

	public static Board instanciateGameMode() throws InterruptedException, IOException {
		System.out.println("Choose a game board size");
		System.out.println("Enter with the number of lines to the board: ");
		int coordinateX = sc.nextInt();
		System.out.println("Enter with the number of columns to the board: ");
		int coordinateY = sc.nextInt();

		return new Board(coordinateX, coordinateY);
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
		catchPlayerShot(player);
		if (validatePlayerShot(player)) {
			computerPlayer.setPlayerPosition(catchPlayerPositions(player));
			if (validatePlayerPosition(computerPlayer)) {
				insertActionsToBoard(computerPlayer);
			} else {
				validate = false;
			}

		} else {
			validate = false;
			System.out.println("Invalid Coordinate.");
		}
		return validate;
	}

	public static void catchPlayerShot(Player player) {
		System.out.println("Enter with your shot position: ");
		String playerShot = sc.next();
		player.setPlayerShot(playerShot);
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
		}
		if (player.getPlayerPosition()[player.getYPOSITION()] > player.getCoordinateY()) {
			feedback = false;
			System.out.println("Number position can't be higher than: " + player.getCoordinateY());
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
			if (player.getPlayerBoard()[playerPosition[player.getXPOSITION()]][player.getYPOSITION()] == ShipPositions.MISSED.getIntValue()
					|| player.getPlayerBoard()[playerPosition[player.getXPOSITION()]][player.getYPOSITION()] == ShipPositions.HIT.getIntValue()) {
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
		if (player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
								   [player.getPlayerPosition()[player.getYPOSITION()]] == ShipPositions.SHIP.getIntValue()) {
			player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
					               [player.getPlayerPosition()[player.getYPOSITION()]] = ShipPositions.HIT.getIntValue();
			player.setPlayerShips(player.getPlayerShips() - 1);
			System.out.println("\nYou hit a ship!");
		} else {
			player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
					               [player.getPlayerPosition()[player.getYPOSITION()]] = ShipPositions.MISSED.getIntValue();
			System.out.println("\nYou missed the shot...\n");
		}
	}

	public static boolean winCondition(boolean loop, Player player1, Player computerPlayer) {
		if (computerPlayer.getPlayerShips() <= 0) {
			System.out.println(player1.getName() + " is the winner!\n");
			return false;
		}
		if (player1.getPlayerShips() <= 0) {
			System.out.println(computerPlayer.getName() + " is the winner!");
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
		closeAll();
	}

	private static void closeAll() {
		sc.close();
	}
}
