package br.com.leomanzini.utils;

public enum ShipPositions {

	NOTHING(0),
	SHIP(1),
	MISSED(2),
	HIT(3);

	private int value;
	
	ShipPositions(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
