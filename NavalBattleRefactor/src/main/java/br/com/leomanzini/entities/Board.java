package br.com.leomanzini.entities;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Board {
	
	private static final Logger LOG = LogManager.getLogger(Board.class);
	private int boardSize;
	private int coordinateX;
	private int coordinateY;
	private int playerOneBoard[][];
	private int playerTwoBoard[][];
	private Scanner sc = new Scanner(System.in);
	
	public Board(int coordinateX, int coordinateY) {
		validateCoordinates(coordinateX, coordinateY);
	}
	
	public void validateCoordinates(int coordinateX, int coordinateY) {
		while(true) {
			boolean ex = false;
			try {
				if (coordinateX > 26 || 0 > coordinateX) {
					while (coordinateX > 26 || 0 > coordinateX) {
						LOG.info("Max number of lines is 26, enter with a valid input!");
						coordinateX = sc.nextInt();
					}
				}
				LOG.info("Enter with the board columns: ");
				coordinateY = sc.nextInt();
				ex = true;
			} catch(InputMismatchException e) {
				LOG.error("Enter with a integer number!\n", e);
			}
			if(ex) {
				this.coordinateX = coordinateX;
				this.coordinateY = coordinateY;
				break;
			}
		}  
	}
	
	public void setPlayerBoard () {
		this.playerOneBoard = createVoidBoard();
		this.playerTwoBoard = createVoidBoard();
	}
	
	public int[][] createVoidBoard () {
		return new int [this.coordinateX][this.coordinateY];
	}
}
