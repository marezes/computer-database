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
	private String INSERT_COMPUTER = "INSERT INTO computer (nom, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String UPDATE_COMPUTER = "UPDATE computer SET nom = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private String DELETE_COMPUTER =  "DELETE FROM computer WHERE id = ?";
	
	private DAOComputer() {
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
	
	/**
	 * Méthode qui renvoie l'objet singleton DAOComputer.
	 * @return Un objet de type DAOComputer
	 */
	public static DAOComputer getInstance(){
		return INSTANCE;
    }

	/**
	 * Méthode qui renvoie la liste des machines (sans détails) présentes dans 
	 * la base de donné et la retourne.
	 * @return Une ArrayList de ModelComputerShort
	 */
	public ArrayList<ModelComputerShort> requestList() {
		ArrayList<ModelComputerShort> model = new ArrayList<ModelComputerShort>();

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
	
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery(SELECT_COMPUTER_LIST)) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String name = resultat.getString("name");
						model.add(new ModelComputerShort(id, name));
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

	/**
	 * Méthode qui récupère le détail d'une machine.
	 * @param id - Un entier qui représente l'id
	 * @return Un objet de type ModelComputer
	 */
	public ModelComputer requestById(int id) {
		ModelComputer model = null;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_BY_ID)) {
				
				statement.setInt(1, id);
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					
					resultat.next();
					String name = resultat.getString("name");
					Timestamp introduced = resultat.getTimestamp("introduced");
					Timestamp discontinued = resultat.getTimestamp("discontinued");
					String manufacturer = resultat.getString("company.name");
					model = new ModelComputer(id, name, introduced, discontinued, manufacturer);
					
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

	/**
	 * Méthode qui créer une nouvelle machine dans la base de donnée.
	 * @param modelComputer - Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public ModelComputer requestCreate(ModelComputer modelComputer) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(INSERT_COMPUTER)) {
				
				statement.setString(1, modelComputer.getName());
				statement.setTimestamp(2, modelComputer.getIntroduced());
				statement.setTimestamp(3, modelComputer.getDiscontinued());
				statement.setString(4, modelComputer.getManufacturer());
				
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate();
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
			return null; // à remplacer par une exception
		} else { // statut == 1 donc réussi
			return modelComputer;
		}
	}

	/**
	 * Méthode qui met à jour les information d'une machine dans la base de donnée. 
	 * @param model - Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public ModelComputer requestUpdate(ModelComputer modelComputer) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {
	
			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(UPDATE_COMPUTER)) {
	
				statement.setString(1, modelComputer.getName());
				statement.setTimestamp(2, modelComputer.getIntroduced());
				statement.setTimestamp(3, modelComputer.getDiscontinued());
				statement.setString(4, modelComputer.getManufacturer());
				statement.setInt(5, modelComputer.getId());
				
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate();
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
			return null; // à remplacer par une exception
		} else { // statut == 1 donc réussi
			return modelComputer;
		}
	}

	/**
	 * Méthode qui supprime une machine de la base de donnée selon un identifiant.
	 * @param id Un entier qui représente l'id
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public boolean requestDelete(int id) {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(DELETE_COMPUTER)) {
	
				statement.setInt(1, id);
				
				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate();
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
