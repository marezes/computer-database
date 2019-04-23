package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.cdb.model.ModelCompany;

public class DAOCompany extends DAO {
	public DAOCompany() {
		super();
	}

	public ArrayList<ModelCompany> requestList() {
		Statement statement = null;
		ResultSet resultat = null;
		ArrayList<ModelCompany> model = new ArrayList<ModelCompany>();
		
		connect();
		
		/* Création de l'objet gérant les requêtes */
		try {
			statement = getConnexion().createStatement();
		} catch (SQLException e) {
			System.err.println("Création du statement raté.");
		}

		/* Exécution d'une requête de lecture */
		try {
			resultat = statement.executeQuery("SELECT *  FROM company;");
		} catch (SQLException e) {
			System.err.println("Récupération de la requête raté.");
		}
		
		try {
			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				model.add(new ModelCompany(id, name));
			}
		} catch (SQLException e) {
			System.err.println("while raté.");
		}
		
		disconnect();
		
		return model;
	}
}
