package br.com.leomanzini.naval;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.naval.entities.Board;

public class MainGame {
	public static void main(String args[]) {
		
		final Logger LOG = LogManager.getLogger(MainGame.class);
		Scanner sc = new Scanner(System.in);
		
		LOG.info("Choose a game board size: ");
		LOG.info("1 - Small game board;");
		LOG.info("2 - Medium game board;");
		LOG.info("3 - Big game board;");
		LOG.info("4 - Bigger game board;");
		
		int option = sc.nextInt();

		Board board = new Board(option);
		
		sc.close();
	}
}