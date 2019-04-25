package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;

public class DAOComputer {
	private static final DAOComputer INSTANCE = new DAOComputer();

	private String url;
	private String user;
	private String password;
	
	private String SELECT_COMPUTER_LIST = "SELECT id, name FROM computer";
	private String SELECT_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	
	private DAOComputer() {
		super();

		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur dans le chargement du Driver JDBC");
		}

		// Activation des properties
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/META-INF/properties.properties"));
		} catch (IOException e) {
			System.err.println("Appel de properties n'a pas fonctionné");
			System.exit(1);
		}

		// Récupérations des éléments dans properties
		url = properties.getProperty("URL");
		user = properties.getProperty("USER");
		password = properties.getProperty("PASSWORD");
	}
	
	public static DAOComputer getInstance(){
		return INSTANCE;
    }

	public ArrayList<ModelComputerShort> requestList() {
		ArrayList<ModelComputerShort> model = new ArrayList<ModelComputerShort>();

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
	
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery(SELECT_COMPUTER_LIST)) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String nameComputer = resultat.getString("name");
						model.add(new ModelComputerShort(id, nameComputer));
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

	public ArrayList<ModelComputer> requestById(int id) {
		ArrayList<ModelComputer> model = new ArrayList<ModelComputer>();

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_BY_ID)) {
				
				statement.setInt(1, id);
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					
					while (resultat.next()) {
						String nameComputer = resultat.getString("name");
						Timestamp di = resultat.getTimestamp("introduced");
						Timestamp dd = resultat.getTimestamp("discontinued");
						String company = resultat.getString("company.name");
						model.add(new ModelComputer(id, nameComputer, di, dd, company));
					}
				} catch (SQLException e) {
					System.err.println("Récupération de la requête raté.");
				}
			} catch (SQLException e) {
				System.err.println("Création du prepared statement raté.");
			}
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}

		return model;
	}

	public boolean requestCreate(ModelComputer model) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
	
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate(
							"INSERT INTO computer (nom, introduced, discontinued, company_id) VALUES " + "(" + model.getName()
									+ ", " + model.getDi() + ", " + model.getDd() + ", " + model.getManufacturer() + ");");
				} catch (SQLException e) {
					System.err.println("Exécution de la requête create raté.");
				}
			} catch (SQLException e) {
				System.err.println("Création du statement raté.");
			}
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}
		
		if (statut == 0) { // échec
			return false;
		} else { // statut == 1 donc réussi
			return true;
		}
	}

	public boolean requestUpdate(ModelComputer model) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {
	
			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
	
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate("UPDATE computer SET nom = " + model.getName() + ", introduced "
							+ model.getDi() + ", discontinued = " + model.getDd() + ", company_id = " + model.getManufacturer()
							+ " WHERE id = " + model.getId() + ";");
				} catch (SQLException e) {
					System.err.println("Exécution de la requête update raté.");
				}
			} catch (SQLException e) {
				System.err.println("Création du statement raté.");
			}
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}

		if (statut == 0) { // échec
			return false;
		} else { // statut == 1 donc réussi
			return true;
		}
	}

	/**
	 * Méthode qui supprime une ligne de la base de donnée.
	 * @param id Un entier qui représente l'id
	 * @return Un booléen true si réussi et non sinon
	 */
	public boolean requestDelete(int id) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
	
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate("DELETE FROM computer WHERE id = " + id + ";");
				} catch (SQLException e) {
					System.err.println("Exécution de la requête delete raté.");
				}
			} catch (SQLException e) {
				System.err.println("Création du statement raté.");
			}
		} catch (SQLException e) {
			System.err.println("Problème dans la connexion à la base SQL");
		}

		if (statut == 0) { // échec
			return false;
		} else { // statut == 1 donc réussi
			return true;
		}
	}
}
