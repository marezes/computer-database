package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.cdb.model.Model;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;

public class DAOComputer extends DAO {
	public DAOComputer() {
		super();
	}

	public ArrayList<Model> requestList() {
		Statement statement = null;
		ResultSet resultat = null;
		ArrayList<Model> model = new ArrayList<Model>();

		connect();

		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête de lecture */
		try {
			resultat = statement.executeQuery("SELECT id, name FROM computer");
		} catch (SQLException e) {
			System.err.println("Récupération de la requête raté.");
		}

		try {
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String nameComputer = resultat.getString("name");
				model.add(new ModelComputerShort(id, nameComputer));
			}
		} catch (SQLException e) {
			System.err.println("while raté.");
		}

		disconnect();

		return model;
	}

	public ArrayList<Model> requestById(int id) {
		Statement statement = null;
		ResultSet resultat = null;
		ArrayList<Model> model = new ArrayList<Model>();

		connect();

		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête de lecture */
		try {
			resultat = statement.executeQuery("SELECT * FROM computer "
					+ "LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = " + id + ";");
		} catch (SQLException e) {
			System.err.println("Récupération de la requête raté.");
		}

		try {
			while (resultat.next()) {
				String nameComputer = resultat.getString("name");
				Timestamp di = resultat.getTimestamp("introduced");
				Timestamp dd = resultat.getTimestamp("discontinued");
				String company = resultat.getString("company.name");
				model.add(new ModelComputer(id, nameComputer, di, dd, company));
			}
		} catch (SQLException e) {
			System.err.println("while raté.");
		}

		disconnect();
		return model;
	}

	public boolean requestCreate(ModelComputer model) {
		Statement statement = null;
		int statut = -1;

		connect();

		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête d'écriture */
		try {
			statut = statement.executeUpdate(
					"INSERT INTO computer (nom, introduced, discontinued, company_id) VALUES " + "(" + model.getName()
							+ ", " + model.getDi() + ", " + model.getDd() + ", " + model.getManufacturer() + ");");
		} catch (SQLException e) {
			System.err.println("Exécution de la requête create raté.");
		}
		disconnect();
		if (statut == 0) { // échec
			return false;
		} else { // statut == 1 donc réussi
			return true;
		}
	}

	public boolean requestUpdate(ModelComputer model) {
		Statement statement = null;
		int statut = -1;

		connect();

		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête d'écriture */
		try {
			statut = statement.executeUpdate("UPDATE computer SET nom = " + model.getName() + ", introduced "
					+ model.getDi() + ", discontinued = " + model.getDd() + ", company_id = " + model.getManufacturer()
					+ " WHERE id = " + model.getId() + ";");
		} catch (SQLException e) {
			System.err.println("Exécution de la requête update raté.");
		}
		disconnect();
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
		Statement statement = null;
		int statut = -1;

		connect();

		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête d'écriture */
		try {
			statut = statement.executeUpdate("DELETE FROM computer WHERE id = " + id + ";");
		} catch (SQLException e) {
			System.err.println("Exécution de la requête delete raté.");
		}
		disconnect();
		if (statut == 0) { // échec
			return false;
		} else { // statut == 1 donc réussi
			return true;
		}
	}
}
