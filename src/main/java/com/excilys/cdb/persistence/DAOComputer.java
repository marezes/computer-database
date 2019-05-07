package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import com.excilys.cdb.exception.ConnectionToDataBaseFailedException;
import com.excilys.cdb.exception.JDBCClassNotFoundException;
import com.excilys.cdb.exception.PropertiesFileLoadFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;

public class DAOComputer {
	private static DAOComputer INSTANCE = null;

	private String url;
	private String user;
	private String password;

	private String SELECT_COMPUTER_LIST_LIMIT = "SELECT id, name FROM computer LIMIT ?, ?";
	private String SELECT_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private String INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private String SELECT_LAST_ID_ELEMENT_INSERTED = "SELECT LAST_INSERT_ID()";

	private DAOComputer() throws JDBCClassNotFoundException, PropertiesFileLoadFailedException {
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new JDBCClassNotFoundException("com.mysql.cj.jdbc.Driver");
		}

		// Activation des properties
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/META-INF/properties.properties"));
		} catch (IOException e) {
			throw new PropertiesFileLoadFailedException("properties.properties");
		}

		// Récupérations des éléments dans properties
		url = properties.getProperty("URL");
		user = properties.getProperty("USER");
		password = properties.getProperty("PASSWORD");
	}

	/**
	 * Méthode qui renvoie l'objet singleton DAOComputer.
	 * 
	 * @return Un objet de type DAOComputer
	 * @throws Exception
	 */
	public static DAOComputer getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new DAOComputer();
		}
		return INSTANCE;
	}

	/**
	 * Méthode qui renvoie la liste des machines (sans détails) présentes dans la
	 * base de donné et la retourne.
	 * 
	 * @param pageNumber      Un entier représentant le numéro de la page à afficher
	 * @param numberOfElement Un entier représantant le nombre d'éléments à afficher par page
	 * @return Une ArrayList de ModelComputerShort
	 * @throws Exception
	 */
	public ArrayList<ModelComputerShort> requestListLimit(int pageNumber, int numberOfElement) throws Exception {
		ArrayList<ModelComputerShort> model = new ArrayList<ModelComputerShort>();

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COMPUTER_LIST_LIMIT)) {

				statement.setInt(1, ((pageNumber - 1) * numberOfElement));
				statement.setInt(2, numberOfElement);

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String name = resultat.getString("name");
						model.add(new ModelComputerShort(id, name));
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
			throw new ConnectionToDataBaseFailedException();
		}

		return model;
	}

	/**
	 * Méthode qui récupère le détail d'une machine.
	 * 
	 * @param id - Un entier qui représente l'id
	 * @return Un objet de type ModelComputer
	 * @throws Exception
	 */
	public ModelComputer requestById(int id) throws Exception {
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
					Integer companyId = resultat.getInt("company_id") == 0 ? null : resultat.getInt("company_id");
					String companyName = resultat.getString("company.name");
					model = new ModelComputer(id, name, introduced, discontinued,
							new ModelCompany(companyId, companyName));

				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					throw e;
				}
			} catch (SQLException e) {
				// System.err.println("Création du prepared statement raté.");
				throw e;
			}
		} catch (SQLException e) {
			throw new ConnectionToDataBaseFailedException();
		}

		return model;
	}

	/**
	 * Méthode qui créer une nouvelle machine dans la base de donnée.
	 * 
	 * @param modelComputer - Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 * @throws Exception
	 */
	public ModelComputer requestCreate(ModelComputer modelComputer) throws Exception {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(INSERT_COMPUTER)) {

				statement.setString(1, modelComputer.getName());
				if (modelComputer.getIntroduced() == null) {
					statement.setNull(2, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(2, modelComputer.getIntroduced());
				}
				if (modelComputer.getDiscontinued() == null) {
					statement.setNull(3, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(3, modelComputer.getDiscontinued());
				}
				if (modelComputer.getModelCompany().getId() == null) {
					statement.setNull(4, java.sql.Types.INTEGER);
				} else {
					statement.setInt(4, modelComputer.getModelCompany().getId());
				}

				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate();
				} catch (SQLException e) {
					// System.err.println("Exécution de la requête create raté.");
					throw e;
				}

				try (ResultSet resultat = statement.executeQuery(SELECT_LAST_ID_ELEMENT_INSERTED)) {
					resultat.next();
					modelComputer.setId(resultat.getInt(1));
				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					throw e;
				}
			} catch (SQLException e) {
				// System.err.println("Création du statement raté.");
				throw e;
			}
		} catch (SQLException e) {
			throw new ConnectionToDataBaseFailedException();
		}
		
		String companyNameMissing = (requestById(modelComputer.getId()).getModelCompany()).getName();
		(modelComputer.getModelCompany()).setName(companyNameMissing);
		return (statut == 1) ? modelComputer : null; // une exception à la place de null;
	}

	/**
	 * Méthode qui met à jour les informations d'une machine dans la base de donnée.
	 * 
	 * @param model Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 * @throws Exception
	 */
	public ModelComputer requestUpdate(ModelComputer modelComputer) throws Exception {
		int statut = -1;

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(UPDATE_COMPUTER)) {

				statement.setString(1, modelComputer.getName());
				
				if (modelComputer.getIntroduced() == null) {
					statement.setNull(2, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(2, modelComputer.getIntroduced());
				}
				
				if (modelComputer.getDiscontinued() == null) {
					statement.setNull(3, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(3, modelComputer.getDiscontinued());
				}
				
				if (modelComputer.getModelCompany().getId() == null) {
					statement.setNull(4, java.sql.Types.INTEGER);
				} else {
					statement.setInt(4, modelComputer.getModelCompany().getId());
				}

				statement.setInt(5, modelComputer.getId());

				/* Exécution d'une requête d'écriture */
				try {
					statut = statement.executeUpdate();
				} catch (SQLException e) {
					// System.err.println("Exécution de la requête update raté.");
					throw e;
				}
			} catch (SQLException e) {
				// System.err.println("Création du statement raté.");
				throw e;
			}
		} catch (SQLException e) {
			throw new ConnectionToDataBaseFailedException();
		}
		
		String companyNameMissing = (requestById(modelComputer.getId()).getModelCompany()).getName();
		(modelComputer.getModelCompany()).setName(companyNameMissing);
		return (statut == 1) ? modelComputer : null; // une exception à la place de null;
	}

	/**
	 * Méthode qui supprime une machine de la base de donnée selon un identifiant.
	 * 
	 * @param id Un entier qui représente l'id
	 * @return Un booléen true si la requête a réussi, et false sinon
	 * @throws Exception
	 */
	public ModelComputer requestDelete(int id) throws Exception {
		int statut = -1;
		
		ModelComputer modelComputerDeleted = requestById(id);

		if (modelComputerDeleted == null) {
			return null; // il faut une exception
		}

		try (Connection connexion = DriverManager.getConnection(url, user, password)) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(DELETE_COMPUTER)) {

				statement.setInt(1, id);

				/* Exécution d'une requête d'écriture */
				statut = statement.executeUpdate();
			} catch (SQLException e) {
				// System.err.println("Delete raté."+e.getStackTrace());
				throw e;
			}
		} catch (SQLException e) {
			throw new ConnectionToDataBaseFailedException();
		}

		return (statut == 1) ? modelComputerDeleted : null; // une exception à la place de null;
	}
}
