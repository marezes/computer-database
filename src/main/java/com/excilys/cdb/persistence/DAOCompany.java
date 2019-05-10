package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.excilys.cdb.model.ModelCompany;

public class DAOCompany {
	
	private static DAOCompany INSTANCE = null;
	
	private String url;
	private String user;
	private String password;
	
	private String SELECT = "SELECT id, name FROM company;";

	private DAOCompany() throws Exception {
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// System.err.println("Erreur dans le chargement du Driver JDBC");
			throw e;
		}

		/* Activation des properties */
		Properties properties = new Properties();
		try {
			InputStream input = getClass().getResourceAsStream("/properties.properties");
			properties.load(input);
		} catch (IOException e) {
			//System.err.println("Appel de properties n'a pas fonctionné");
			throw e;
		}

		/* Récupérations des éléments dans properties */
		url = properties.getProperty("URL");
		user = properties.getProperty("USER");
		password = properties.getProperty("PASSWORD");
	}

	/**
	 * Méthode qui renvoie l'objet singleton DAOCompany.
	 * @return Un objet de type DAOCompany
	 * @throws Exception 
	 */
	public static DAOCompany getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new DAOCompany();
		}
		return INSTANCE;
	}

	/**
	 * Méthode qui récupère la liste des entreprises dans la base de donnée et la retourne.
	 * @return Une ArrayList de ModelCompany
	 * @throws SQLException 
	 */
	public ArrayList<ModelCompany> requestList() throws SQLException {
		ArrayList<ModelCompany> model = new ArrayList<ModelCompany>();
		
		try (Connection connexion = DriverManager.getConnection(url, user, password)) {
			
			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery(SELECT)) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String name = resultat.getString("name");
						model.add(new ModelCompany(id, name));
					}
				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					throw e;
				}
				
			} catch (SQLException e) {
				// System.err.println("Création du statement raté.");
				throw e;
			}

		} catch (SQLException e) {
			// System.err.println("Problème dans la connexion à la base SQL");
			throw e;
		}

		return model;
	}
}
