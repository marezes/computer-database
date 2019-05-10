package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.persistence.DAOCompany;

public class ServiceCompany {
	private static ServiceCompany INSTANCE = null;
	
	private DAOCompany daoCompany;
	
	private ServiceCompany() throws Exception {
		MapperCompany.getInstance();
		daoCompany = DAOCompany.getInstance();
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton ServiceCompany.
	 * @return Un objet de type ServiceCompany
	 * @throws Exception 
	 */
	public static ServiceCompany getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new ServiceCompany();
		}
		return INSTANCE;
	}
	
	/**
	 * Méthode qui récupère la liste des entreprises.
	 * @return Une ArrayList de ModelCompany
	 * @throws Exception 
	 */
	public ArrayList<ModelCompany> requestList() throws Exception {
		return daoCompany.requestList();
	}
}
