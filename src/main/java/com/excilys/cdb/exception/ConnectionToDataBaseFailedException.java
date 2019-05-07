package com.excilys.cdb.exception;

public class ConnectionToDataBaseFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionToDataBaseFailedException() {
		super("Erreur: La connexion à la base de donnée a échoué. Vérifiez l'url, l'utilisateur ou bien le mot de passe.");
	}
}
