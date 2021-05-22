package br.com.leomanzini.naval;

import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;
import br.com.leomanzini.naval.entities.Ships;

public class MainGame {
	
	private static final Logger LOG = LogManager.getLogger(MainGame.class);
	private static final Scanner sc = new Scanner(System.in);
	
	private static Board instanciateGameMode() {
		LOG.info("Choose a game board size: ");
		LOG.info("1 - Small game board;");
		LOG.info("2 - Medium game board;");
		LOG.info("3 - Big game board;");
		LOG.info("4 - Bigger game board;");
		int option = sc.nextInt();
		
		return new Board(option);
	}
	
	private static Player instantiateHumanPlayer() {
		LOG.info("Enter with the player one name: ");
		String name = sc.next();
		
		return new Player(name);
	}
	
	private static Player instantiateComputerPlayer() {
		return new Player();
	}
	
	private static void initiateNumberOfShips (Ships ships) {
		LOG.info("The max number of ships are: " + ships.getMaxNumberOfShips());
		LOG.info("Enter with the ships you want to insert: ");
		int numberOfShips = sc.nextInt();
		ships.setNumberOfChips(numberOfShips);
	}
	
	private static void fillPlayerBoard(Player player, Board board, Ships ships) {
		player.setPlayerBoard(board.insertShipsAtPlayerBoard(ships.getNumberOfChips()));
		player.setCoordinateX(board.getCoordinateX());
		player.setCoordinateY(board.getCoordinateY());
		player.setPlayerShips(ships.getNumberOfChips());
	}
	
	private static void printPlayerBoard(Board board, Player player) {
		board.printBoard(player);
	}
	
	public static boolean humanPlayerAction(Player player, Player computerPlayer) {
		boolean validate = true;
		catchPlayerShot(player);
		if (validatePlayerShot(player)) {
			computerPlayer.setPlayerPosition(catchPlayerPositions(player));
			if(validatePlayerPosition(computerPlayer)) {
				insertActionsToBoard(player, computerPlayer);
			} else {
				validate = false;
			}
					
		} else {
			validate = false;
			LOG.info("Invalid Coordinate.");
		}
		return validate;
	}
	
	public static void catchPlayerShot(Player player) {
		LOG.info("Enter with your shot position: ");
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
		if(player.getPlayerPosition()[player.getXPOSITION()] > player.getCoordinateX() -1) {
			feedback = false;
			LOG.info("Letter position can't be higher than: " + (char) (player.getCoordinateX() + 64));
		}
		if(player.getPlayerPosition()[player.getYPOSITION()] > player.getCoordinateY()) {
			feedback = false;
			LOG.info("Number position can't be higher than: " + player.getCoordinateY());
		}
		return feedback;
	}
	
	public static Player insertActionsToBoard (Player player1, Player player2) {
		if(player1.getHuman()) {
			Player player = player2;
			if (player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] == 1) 
			{
				player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] = 3;
				player.setPlayerShips(player.getPlayerShips() - 1);
				LOG.info("You hit a ship!");
				return player;
			} else {
				player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] = 2;
				LOG.info("You missed the shot...");
				return player;
			}
		} else {
			Player player = player1;
			if (player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] == 1) 
			{
				player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] = 3;
				player.setPlayerShips(player.getPlayerShips() - 1);
				LOG.info("Computer hit a ship!");
				return player;
			} else {
				player.getPlayerBoard()[player.getPlayerPosition()[player.getXPOSITION()]]
									   [player.getPlayerPosition()[player.getYPOSITION()]] = 2;
				LOG.info("Computer missed the shot...");
				return player;
			}
		}
	}
	
	public static void computerPlayerAction(Player player1, Player computerPlayer) {
		catchComputerShot(player1);
		insertActionsToBoard(computerPlayer, player1);
	}
	
	public static void catchComputerShot(Player player) {
		int[] playerPosition = new int[2];
		playerPosition[player.getXPOSITION()] = getRandomShot(player.getCoordinateX());
		playerPosition[player.getYPOSITION()] = getRandomShot(player.getCoordinateY());
		player.setPlayerPosition(playerPosition);
	}
	
	public static int getRandomShot(int limit) {
		Random computerCoordinates = new Random();
		int numberGenerated = computerCoordinates.nextInt(limit);
		return (numberGenerated == limit) ? --numberGenerated : numberGenerated;
	}
	
	public static boolean winCondition(boolean loop, Player player1, Player computerPlayer) {
		if(computerPlayer.getPlayerShips() <= 0) {
			LOG.info(player1.getName() + " win!");
			return false;
		}
		if(player1.getPlayerShips() <= 0) {
			System.out.println(computerPlayer.getName() + " win!");
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
				if(!(loop = winCondition(loop, player1, computerPlayer))) break;
			}
		} while(loop);
		printPlayerBoard(board, player1);
		printPlayerBoard(board, computerPlayer);
	}
	
	public static void main(String args[]) {
		
		Board board = instanciateGameMode();
		Player player1 = instantiateHumanPlayer();
		Player computerPlayer = instantiateComputerPlayer();
		Ships ships = new Ships(board.getGameBoard());
		
		player1.setPlayerBoard(board.createVoidBoard());
		computerPlayer.setPlayerBoard(board.createVoidBoard());
		
		initiateNumberOfShips(ships);
		
		fillPlayerBoard(player1, board, ships);
		fillPlayerBoard(computerPlayer, board, ships);
		
		gameLoop(board, player1, computerPlayer);
		
		sc.close();
	}
}