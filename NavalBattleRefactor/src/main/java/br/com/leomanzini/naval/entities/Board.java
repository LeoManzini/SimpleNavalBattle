package br.com.leomanzini.naval.entities;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Board {

	private static final Logger LOG = LogManager.getLogger(Board.class);
	private int coordinateX;
	private int coordinateY;
	private int gameBoard;
	private Scanner sc = new Scanner(System.in);

	public Board() {
	}

	public Board(int option) {
		validateOption(option);
	}
	
	public void validateOption(int option) {
		try {
			loop : while (true) {
				switch(option) {
					case 1:
						this.gameBoard = 1;
						this.coordinateX = 8;
						this.coordinateY = 8;
						break loop;
					case 2:
						this.gameBoard = 2;
						this.coordinateX = 13;
						this.coordinateY = 13;
						break loop;
					case 3:
						this.gameBoard = 3;
						this.coordinateX = 20;
						this.coordinateY = 20;
						break loop;
					case 4:
						this.gameBoard = 4;
						this.coordinateX = 26;
						this.coordinateY = 26;
						break loop;
					default:
						LOG.info("Choose a valid game option: ");
						option = sc.nextInt();
				}
			}
		} catch(InputMismatchException e) {
			LOG.error("Enter with a integer number.", e);
			System.exit(-1);
		}
	}
	
	public int[][] createVoidBoard () {
		return new int [this.coordinateX][this.coordinateY];
	}
	
	public void close() {
		sc.close();
	}
	
	public int getGameBoard() {
		return gameBoard;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}
}
