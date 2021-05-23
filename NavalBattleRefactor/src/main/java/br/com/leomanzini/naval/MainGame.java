package br.com.leomanzini.naval;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;
import br.com.leomanzini.naval.entities.Ships;
import br.com.leomanzini.naval.executor.GameExecutor;

public class MainGame {
	
	private static final Logger LOG = LogManager.getLogger(MainGame.class);
	
	public static void main(String args[]) {
		
		try {
			Board board = null;
			Player player1 = null;
			Player computerPlayer = null;
			Ships ships = null;
			
			GameExecutor.printStartMenu();
			GameExecutor.startOption();
			GameExecutor.printGameRules();
			GameExecutor.startGame(board, player1, computerPlayer, ships);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		}
	}
}