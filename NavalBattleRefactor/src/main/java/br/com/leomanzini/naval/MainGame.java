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
			Board board = GameExecutor.instanciateGameMode();
			Player player1 = GameExecutor.instantiateHumanPlayer();
			Player computerPlayer = GameExecutor.instantiateComputerPlayer();
			Ships ships = new Ships(board.getGameBoard());
			
			player1.setPlayerBoard(board.createVoidBoard());
			computerPlayer.setPlayerBoard(board.createVoidBoard());
			
			GameExecutor.initiateNumberOfShips(ships);
			
			GameExecutor.fillPlayerBoard(player1, board, ships);
			GameExecutor.fillPlayerBoard(computerPlayer, board, ships);
			
			GameExecutor.gameLoop(board, player1, computerPlayer);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		}
	}
}