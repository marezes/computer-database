package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.persistence.DAOCompany;

public class ServiceCompany {
	private static ServiceCompany INSTANCE = null;
	
	private MapperCompany mapperCompany;
	private DAOCompany daoCompany;
	
	private ServiceCompany() throws Exception {
		mapperCompany = MapperCompany.getInstance();
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
	 * @return Une ArrayList de DTOCompany
	 * @throws Exception 
	 */
	public ArrayList<DTOCompany> requestList() throws Exception {
		return mapperCompany.modelCompanyListToDTOCompanyList(daoCompany.requestList());
	}
}
