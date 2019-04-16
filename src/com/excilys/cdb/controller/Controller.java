package com.excilys.cdb.controller;

public class Controller {
	String[] response;
	
	public Controller() {
		this.response = null;
	}
	
	public String[] process(String... args) {
		if (correctArgs(args)) {
			
			return response;
		} else {
			return null;
		}
	}
	
	/**
	 * Méthode qui vérifie que l'argument n'est pas vide, ai le bon nombre, et qu'il
	 * n'y ait pas de String vide.
	 * @param args La liste des arguments
	 * @return Un booléen pour dire si c'est bon ou pas
	 */
	private boolean correctArgs(String[] args) {
		if (args.length <= 0 || args.length > 5) {
			return false;
		}
		if (args[0].length() == 0) {
			return false;
		} else {
			for (int i = 1; i < args.length; i++) {
				if (args[i] == null) {
					continue;
				} else if (args[i].length() == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
