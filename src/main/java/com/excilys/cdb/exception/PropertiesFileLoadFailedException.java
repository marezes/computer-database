package com.excilys.cdb.exception;

public class PropertiesFileLoadFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PropertiesFileLoadFailedException(String fileName) {
		super("Erreur: Le fichier \"" + fileName + "\" n'a pas pu être chargé.");
	}
}
