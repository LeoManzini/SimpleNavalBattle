package br.com.leomanzini.naval;

import br.com.leomanzini.naval.entities.Board;
import br.com.leomanzini.naval.entities.Player;
import br.com.leomanzini.naval.entities.Ships;
import br.com.leomanzini.naval.executor.GameExecutor;

public class MainGame {
		
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
			System.out.println(e);
			System.exit(-1);
		}
	}
}