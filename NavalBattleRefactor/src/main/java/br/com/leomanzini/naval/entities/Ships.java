package br.com.leomanzini.naval.entities;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ships {
	
	private static final Logger LOG = LogManager.getLogger(Ships.class);
	private Scanner sc = new Scanner(System.in);
	private int maxNumberOfShips;
	private int numberOfShips;
	
	public Ships() {
	}
	
	public Ships(int gameBoard) {
		calculateMaxNumberOfShips(gameBoard);
	}
	
	public void calculateMaxNumberOfShips(int gameBoard) {
		try {
			switch(gameBoard) {
			case 1:
				this.maxNumberOfShips = (8 * 8) / 3;
				break;
			case 2:
				this.maxNumberOfShips = (13 * 13) / 3;
				break;
			case 3:
				this.maxNumberOfShips = (20 * 20) / 3;
				break;
			case 4:
				this.maxNumberOfShips = (26 * 26) / 3;
				break;
			}
		} catch(Exception e) {
			LOG.error("Invalid board size.", e);
			System.exit(-1);
		}
	}
	
	private void validateNumberOfChips(int numberOfShips) {
		try {
			while(true) {
				if(numberOfShips <= 0 || numberOfShips > this.maxNumberOfShips) {
					LOG.info("Enter with a valid number of ships.");
					numberOfShips = sc.nextInt();
				} else {
					this.numberOfShips = numberOfShips;
					break;
				}
			}
		} catch(InputMismatchException e) {
			LOG.error("Enter with a integer number.", e);
			System.exit(-1);
		}
	}
	
	public int getMaxNumberOfShips() {
		return maxNumberOfShips;
	}

	public void setMaxNumberOfShips(int maxNumberOfShips) {
		this.maxNumberOfShips = maxNumberOfShips;
	}

	public int getNumberOfChips() {
		return numberOfShips;
	}

	public void setNumberOfChips(int numberOfShips) {
		validateNumberOfChips(numberOfShips);
	}
}
