package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.excilys.cdb.model.ModelCompany;

public class DAOCompany {
	
	private static final DAOCompany INSTANCE = new DAOCompany();
	
	private String url;
	private String user;
	private String password;
	
	private String SELECT = "SELECT * FROM company;";

	private DAOCompany() {
		super();
		
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur dans le chargement du Driver JDBC");
		}

		/* Activation des properties */
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/META-INF/properties.properties"));
		} catch (IOException e) {
			System.err.println("Appel de properties n'a pas fonctionné");
			System.exit(1);
		}

		/* Récupérations des éléments dans properties */
		url = properties.getProperty("URL");
		user = properties.getProperty("USER");
		password = properties.getProperty("PASSWORD");
	}

	public static DAOCompany getInstance() {
		return INSTANCE;
	}

	public ArrayList<ModelCompany> requestList() {
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
					System.err.println("Récupération de la requête raté.");
				}
				
			} catch (SQLException e) {
				System.err.println("Création du statement raté.");
			}
			
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}

		return model;
	}
}
