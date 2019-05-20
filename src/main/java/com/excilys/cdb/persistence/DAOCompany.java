package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.excilys.cdb.model.ModelCompany;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOCompany {
	
	private static DAOCompany INSTANCE = null;
	
	private final HikariDataSource dataSource;
	
	private String SELECT = "SELECT id, name FROM company;";

	private DAOCompany() throws Exception {
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("hikariConfig");
		} catch (MissingResourceException ex) {
			bundle = ResourceBundle.getBundle("dbconfig_travis");
		}
		
		String driver = bundle.getString("driverClassName");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException cause) {
			throw cause;
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(driver);
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("user"));
		config.setPassword(bundle.getString("password"));
		
		dataSource = new HikariDataSource(config);
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
		
		try (Connection connexion = dataSource.getConnection()) {
			
			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery(SELECT)) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String name = resultat.getString("name");
						model.add(new ModelCompany.ModelCompanyBuilder()
								.withId(id)
								.withName(name)
								.build());
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
