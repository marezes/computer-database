package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DAOComputer extends DAO {
	public DAOComputer() {
		super();
	}

	public void test() {
		connect();
		/* Création de l'objet gérant les requêtes */
		Statement statement = null;
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête de lecture */
		ResultSet resultat = null;
		try {
			resultat = statement.executeQuery("SELECT *  FROM computer;");
		} catch (SQLException e) {
			System.err.println("Récupération du statement raté.");
		}

		try {
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String intro = resultat.getString("name");
				Timestamp t = resultat.getTimestamp("introduced");
				System.out.println(id + " " + intro + " " + t);
			}
		} catch (SQLException e) {
			System.err.println("while raté.");
		}
	}
}
