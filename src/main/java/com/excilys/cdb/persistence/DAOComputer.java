package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dbConfig.Hikari;
import com.excilys.cdb.exception.ConnectionToDataBaseFailedException;
import com.excilys.cdb.exception.JDBCClassNotFoundException;
import com.excilys.cdb.exception.PropertiesFileLoadFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;
import com.excilys.cdb.model.ModelPage;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DAOComputer {
	
	private Logger logger = LoggerFactory.getLogger(DAOComputer.class);
	
	private final HikariDataSource dataSource;

	private String SELECT_COMPUTER_LIST_LIMIT = 
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id "
			+ "ORDER BY ";
	private String SELECT_COMPUTER_SEARCH_LIST_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE INSTR(computer.name, ?) > 0 OR INSTR(company.name, ?) > 0 ORDER BY ";
	private String SELECT_COUNT_COMPUTER = "SELECT COUNT(id) FROM computer";
	private String SELECT_COMPLETE_COMPUTER_LIST_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ?, ?;";
	private String SELECT_COUNT_COMPUTER_SEARCHED = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE INSTR(computer.name, ?) > 0 OR INSTR(company.name, ?) > 0;";
	private String SELECT_BY_ID = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;";
	private String INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?;";
	private String DELETE_COMPUTERS_WITH_TRANSACTION = "DELETE FROM computer WHERE campany_id = ?;";
	private String SELECT_LAST_ID_ELEMENT_INSERTED = "SELECT LAST_INSERT_ID();";
	private HashMap<String, String> antiInjector;

	public DAOComputer(Hikari hikari) throws JDBCClassNotFoundException, PropertiesFileLoadFailedException, ClassNotFoundException {
		dataSource = hikari.getDataSource();
		
		this.antiInjector = new HashMap<String, String>();
		this.antiInjector.put("computerName", "computer.name");
		this.antiInjector.put("introduced", "introduced");
		this.antiInjector.put("discontinued", "discontinued");
		this.antiInjector.put("companyName", "company.name");
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

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COMPUTER_LIST_LIMIT)) {

				statement.setInt(1, ((pageNumber - 1) * numberOfElement));
				statement.setInt(2, numberOfElement);

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						int id = resultat.getInt("id");
						String name = resultat.getString("name");
						model.add(new ModelComputerShort.ModelComputerShortBuilder(name)
								.withId(id)
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return model;
	}
	
	public ArrayList<ModelComputer> requestCompleteListLimit(int pageNumber, int numberOfElement) throws Exception {
		ArrayList<ModelComputer> model = new ArrayList<ModelComputer>();

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COMPLETE_COMPUTER_LIST_LIMIT)) {

				statement.setInt(1, ((pageNumber - 1) * numberOfElement));
				statement.setInt(2, numberOfElement);

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						int id = resultat.getInt("computer.id");
						String name = resultat.getString("computer.name");
						LocalDate introduced = (resultat.getTimestamp("introduced") == null 
								? null : ((resultat.getTimestamp("introduced")).toLocalDateTime()).toLocalDate());
						LocalDate discontinued = (resultat.getTimestamp("discontinued") == null 
								? null : ((resultat.getTimestamp("discontinued")).toLocalDateTime()).toLocalDate());
						Integer companyId = resultat.getInt("company_id") == 0 ? null : resultat.getInt("company_id");
						String companyName = resultat.getString("company.name");
						model.add(new ModelComputer.ModelComputerBuilder(name)
								.withId(id)
								.withIntroduced(introduced)
								.withDiscontinued(discontinued)
								.withModelCompany(
										new ModelCompany.ModelCompanyBuilder()
											.withId(companyId)
											.withName(companyName)
											.build())
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return model;
	}
	
	public ArrayList<ModelComputer> requestListPage(ModelPage modelPage) throws Exception {
		ArrayList<ModelComputer> model = new ArrayList<ModelComputer>();
		
		String SELECT_COMPUTER_LIST_LIMIT_ORDER_BY = SELECT_COMPUTER_LIST_LIMIT + (getOrderBy(modelPage.getOrderBy()) + " IS NULL, " + getOrderBy(modelPage.getOrderBy()) + " " + (modelPage.isAsc() ? "ASC" : "DESC") + " LIMIT ?, ?;");
		
		try (Connection connexion = dataSource.getConnection()) {
			
			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COMPUTER_LIST_LIMIT_ORDER_BY)) {

				statement.setInt(1, ((modelPage.getPageNumber() - 1) * modelPage.getNumberOfElementsToPrint()));
				statement.setInt(2, modelPage.getNumberOfElementsToPrint());

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						int id = resultat.getInt("computer.id");
						String name = resultat.getString("computer.name");
						LocalDate introduced = (resultat.getTimestamp("introduced") == null 
								? null : ((resultat.getTimestamp("introduced")).toLocalDateTime()).toLocalDate());
						LocalDate discontinued = (resultat.getTimestamp("discontinued") == null 
								? null : ((resultat.getTimestamp("discontinued")).toLocalDateTime()).toLocalDate());
						Integer companyId = resultat.getInt("company_id") == 0 ? null : resultat.getInt("company_id");
						String companyName = resultat.getString("company.name");
						model.add(new ModelComputer.ModelComputerBuilder(name)
								.withId(id)
								.withIntroduced(introduced)
								.withDiscontinued(discontinued)
								.withModelCompany(
										new ModelCompany.ModelCompanyBuilder()
											.withId(companyId)
											.withName(companyName)
											.build())
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return model;
	}
	
	private String getOrderBy(String columnName) {
		return ((antiInjector.get(columnName) == null) ? "computer.id" : antiInjector.get(columnName));
	}
	
	public int requestTotalNumberOfComputers() throws Exception {
		int totalNumberOfComputers = -1;

		try (Connection connexion = dataSource.getConnection()) {
			/* Création de l'objet gérant les requêtes */
			try (Statement statement = connexion.createStatement()) {
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery(SELECT_COUNT_COMPUTER)) {
					resultat.next();
					totalNumberOfComputers = resultat.getInt(1);
				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					throw e;
				}
				
			} catch (SQLException e) {
				// System.err.println("Création du statement raté.");
				throw e;
			}
		} catch (SQLException e) {
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}
		
		return totalNumberOfComputers;
	}
	
	public ArrayList<ModelComputer> requestListPageSearched(ModelPage modelPage) throws Exception {
		ArrayList<ModelComputer> model = new ArrayList<ModelComputer>();
		
		String SELECT_COMPUTER_SEARCH_LIST_LIMIT_ORDER_BY = SELECT_COMPUTER_SEARCH_LIST_LIMIT + (getOrderBy(modelPage.getOrderBy()) + " IS NULL, " + getOrderBy(modelPage.getOrderBy()) + " " + (modelPage.isAsc() ? "ASC" : "DESC") + " LIMIT ?, ?;");
		System.out.println(SELECT_COMPUTER_SEARCH_LIST_LIMIT_ORDER_BY);
		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COMPUTER_SEARCH_LIST_LIMIT_ORDER_BY)) {

				statement.setString(1, modelPage.getWordSearched());
				statement.setString(2, modelPage.getWordSearched());
				statement.setInt(3, ((modelPage.getPageNumber() - 1) * modelPage.getNumberOfElementsToPrint()));
				statement.setInt(4, modelPage.getNumberOfElementsToPrint());
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						int id = resultat.getInt("computer.id");
						String name = resultat.getString("computer.name");
						LocalDate introduced = (resultat.getTimestamp("introduced") == null 
								? null : ((resultat.getTimestamp("introduced")).toLocalDateTime()).toLocalDate());
						LocalDate discontinued = (resultat.getTimestamp("discontinued") == null 
								? null : ((resultat.getTimestamp("discontinued")).toLocalDateTime()).toLocalDate());
						Integer companyId = resultat.getInt("company_id") == 0 ? null : resultat.getInt("company_id");
						String companyName = resultat.getString("company.name");
						model.add(new ModelComputer.ModelComputerBuilder(name)
								.withId(id)
								.withIntroduced(introduced)
								.withDiscontinued(discontinued)
								.withModelCompany(
										new ModelCompany.ModelCompanyBuilder()
											.withId(companyId)
											.withName(companyName)
											.build())
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return model;
	}
	
	public int requestTotalNumberOfComputersFound(String nameSearched) {
		int totalNumberOfComputersFound = 0;

		try (Connection connexion = dataSource.getConnection()) {
			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_COUNT_COMPUTER_SEARCHED)) {

				statement.setString(1, nameSearched);
				statement.setString(2, nameSearched);
				
				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {
					while (resultat.next()) {
						totalNumberOfComputersFound++;
					}
				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					e.printStackTrace();
					throw e;
				}
				
			} catch (SQLException e) {
				// System.err.println("Création du statement raté.");
				e.printStackTrace();
				throw e;
			}
		} catch (SQLException e) {
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}
		
		return totalNumberOfComputersFound;
	}

	/**
	 * Méthode qui récupère le détail d'une machine.
	 * 
	 * @param id - Un entier qui représente l'id
	 * @return Un objet de type ModelComputer
	 * @throws Exception
	 */
	public ModelComputer requestById(int id) {
		ModelComputer model = null;

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_BY_ID)) {

				statement.setInt(1, id);

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {

					resultat.next();
					String name = resultat.getString("name");
					LocalDate introduced = (resultat.getTimestamp("introduced") == null 
							? null : ((resultat.getTimestamp("introduced")).toLocalDateTime()).toLocalDate());
					LocalDate discontinued = (resultat.getTimestamp("discontinued") == null 
							? null : ((resultat.getTimestamp("discontinued")).toLocalDateTime()).toLocalDate());
					Integer companyId = resultat.getInt("company_id") == 0 ? null : resultat.getInt("company_id");
					String companyName = resultat.getString("company.name");
					model = (new ModelComputer.ModelComputerBuilder(name)
							.withId(id)
							.withIntroduced(introduced)
							.withDiscontinued(discontinued)
							.withModelCompany(
									new ModelCompany.ModelCompanyBuilder()
										.withId(companyId)
										.withName(companyName)
										.build())
							.build());

				} catch (SQLException e) {
					// System.err.println("Récupération de la requête raté.");
					throw e;
				}
			} catch (SQLException e) {
				// System.err.println("Création du prepared statement raté.");
				throw e;
			}
		} catch (SQLException e) {
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
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

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(INSERT_COMPUTER)) {

				statement.setString(1, modelComputer.getName());
				if (modelComputer.getIntroduced() == null) {
					statement.setNull(2, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(2, Timestamp.valueOf((modelComputer.getIntroduced()).atStartOfDay()));
				}
				if (modelComputer.getDiscontinued() == null) {
					statement.setNull(3, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(3, Timestamp.valueOf((modelComputer.getDiscontinued()).atStartOfDay()));
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
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

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(UPDATE_COMPUTER)) {

				statement.setString(1, modelComputer.getName());
				
				if (modelComputer.getIntroduced() == null) {
					statement.setNull(2, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(2, Timestamp.valueOf((modelComputer.getIntroduced()).atStartOfDay()));
				}
				
				if (modelComputer.getDiscontinued() == null) {
					statement.setNull(3, java.sql.Types.TIMESTAMP);
				} else {
					statement.setTimestamp(3, Timestamp.valueOf((modelComputer.getDiscontinued()).atStartOfDay()));
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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
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
	public ModelComputer requestDelete(int id) throws RuntimeException {
		int statut = -1;
		
		ModelComputer modelComputerDeleted = requestById(id);

		if (modelComputerDeleted == null) {
			return null; // il faut une exception
		}

		try (Connection connexion = dataSource.getConnection()) {

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
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return (statut == 1) ? modelComputerDeleted : null; // une exception à la place de null;
	}
	
	public boolean requestDeleteWithTransaction(int companyId) throws RuntimeException {
		int statut = -1;

		try (Connection connexion = dataSource.getConnection()) {
			
			/* set auto commit to false */
            connexion.setAutoCommit(false);
			
			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(DELETE_COMPUTERS_WITH_TRANSACTION)) {

				statement.setInt(1, companyId);

				/* Exécution d'une requête d'écriture */
				statut = statement.executeUpdate();
				
				/* Exécution du commit */
				connexion.commit();
				
			} catch (SQLException e) {
				// System.err.println("Delete raté."+e.getStackTrace());
				connexion.rollback();
				throw e;
			}
			
			/* set auto commit to true */
            connexion.setAutoCommit(false);
            
		} catch (SQLException e) {
			ConnectionToDataBaseFailedException connectionException = new ConnectionToDataBaseFailedException();
			logger.error(connectionException.getMessage());
			logger.error(e.getStackTrace().toString());
			throw connectionException;
		}

		return (statut == 1) ? true : false; // une exception à la place de null;
	}
}
