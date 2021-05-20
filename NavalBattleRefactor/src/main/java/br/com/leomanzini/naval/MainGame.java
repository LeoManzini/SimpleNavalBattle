package br.com.leomanzini.naval;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;

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
	
	public static void main(String args[]) {
		
		Board board = instanciateGameMode();
		Player player1 = instantiateHumanPlayer();
		Player player2 = instantiateComputerPlayer();
		
		player1.setPlayerBoard(board.createVoidBoard());
		player2.setPlayerBoard(board.createVoidBoard());
		
		sc.close();
	}
}