package br.com.leomanzini.naval.utils;

public enum ShipPositions {

	NOTHING(0),
	SHIP(1),
	MISSED(2),
	HIT(3);

	private int value;
	
	ShipPositions(int value) {
		this.value = value;
	}
	
	public int getIntValue() {
		switch (value) {
			case 0:
				return 0;
			case 1:
				return 1;
			case 2: 
				return 2;
			case 3:
				return 3;
			default:
				return 0;
		}
	}
	
	public int getValue() {
		return this.value;
	}
}
