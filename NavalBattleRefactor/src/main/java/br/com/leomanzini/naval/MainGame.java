package br.com.leomanzini.naval;

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
		player.setPlayerShips(ships.getNumberOfChips());
	}
	
	public static void main(String args[]) {
		
		Board board = instanciateGameMode();
		Player player1 = instantiateHumanPlayer();
		Player player2 = instantiateComputerPlayer();
		Ships ships = new Ships(board.getGameBoard());
		
		player1.setPlayerBoard(board.createVoidBoard());
		player2.setPlayerBoard(board.createVoidBoard());
		
		initiateNumberOfShips(ships);
		
		fillPlayerBoard(player1, board, ships);
		fillPlayerBoard(player2, board, ships);
		
		sc.close();
	}
}