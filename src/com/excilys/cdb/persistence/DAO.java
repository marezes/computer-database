package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DAO {
	private String url;
	private String user;
	private String password;
	private Connection connexion;
	
	public DAO() {
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch ( ClassNotFoundException e ) {
		    System.err.println("Erreur dans le chargement du Driver JDBC");
		}
		
		// Activation des properties
		Properties properties = new Properties();
        try {
			properties.load(new FileInputStream("src/META-INFO/properties.properties"));
		} catch (IOException e) {
			System.err.println("Appel de properties n'a pas fonctionné");
			System.exit(1);
		}
        
        // Récupérations des éléments dans properties
        url = properties.getProperty("URL");
        user = properties.getProperty("USER");
        password = properties.getProperty("PASSWORD");
	}
	
	/**
	 * Méthode qui se connecte à la base de donnée.
	 */
	public void connect() {
		try {
			connexion = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}
	}
	
	/**
	 * Méthode qui se déconnecte de la base de donnée.
	 */
	public void disconnect() {
		try {
			connexion.close();
		} catch (SQLException e) {
			System.err.println("La fermeture de connexion ne s'est pas effectué");
		}
	}
	
	// Getter
	
	public Connection getConnexion() {
		return this.connexion;
	}
}
