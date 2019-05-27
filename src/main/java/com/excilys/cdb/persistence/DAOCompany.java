package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.excilys.cdb.dbConfig.Hikari;
import com.excilys.cdb.model.ModelCompany;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DAOCompany {
	
	private final HikariDataSource dataSource;
	
	private String SELECT = "SELECT id, name FROM company;";
	private String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?;";
	private String DELETE_COMPANY = "DELETE FROM company WHERE id = ?;";

	public DAOCompany(Hikari hikari) throws Exception {
		dataSource = hikari.getDataSource();
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
	
	public boolean requestDelete(int id) throws RuntimeException, Exception {
		int statut = -1;

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(DELETE_COMPANY)) {

				statement.setInt(1, id);

				/* Exécution d'une requête d'écriture */
				statut = statement.executeUpdate();
			} catch (SQLException e) {
				// System.err.println("Delete raté."+e.getStackTrace());
				throw e;
			}
		} catch (SQLException e) {
			throw e;
		}

		return (statut == 1) ? true : false; // une exception à la place de null;
	}
	
	public ModelCompany requestById(int companyId) throws Exception {
		ModelCompany model = null;

		try (Connection connexion = dataSource.getConnection()) {

			/* Création de l'objet gérant les requêtes */
			try (PreparedStatement statement = connexion.prepareStatement(SELECT_BY_ID)) {

				statement.setInt(1, companyId);

				/* Exécution d'une requête de lecture */
				try (ResultSet resultat = statement.executeQuery()) {

					resultat.next();
					String name = resultat.getString("name");
					model = (new ModelCompany.ModelCompanyBuilder()
							.withId(companyId)
							.withName(name)
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
			throw e;
		}

		return model;
	}
}
