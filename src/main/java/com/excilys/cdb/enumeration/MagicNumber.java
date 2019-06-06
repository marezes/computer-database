package com.excilys.cdb.enumeration;

public enum MagicNumber {
	LIST_COMPUTER("0"),
	LIST_COMPANY("1"),
	SHOW_COMPUTER_DETAILS("2"),
	CREATE_COMPUTER("3"),
	UPDATE_COMPUTER("4"),
	DELETE_COMPUTER("5"),
	EXIT_PROGRAM("6"),
	UNKNOWN("#");
	
	private String value = "";
	
	private MagicNumber(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	public static MagicNumber getEnum(String valeur) {
		for (MagicNumber e : MagicNumber.values()) {
			if (e.value.equals(valeur)) {
				return e;
			}
		}
		return UNKNOWN;
	}
}
