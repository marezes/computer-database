package com.excilys.cdb.exception;

public class JDBCClassNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JDBCClassNotFoundException(String nameOfClassNotFound) {
		System.out.println("Erreur: La classe \"" + nameOfClassNotFound + "\" n'a pas été trouvé");
	}
}
