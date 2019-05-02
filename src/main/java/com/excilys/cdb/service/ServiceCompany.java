package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.persistence.DAOCompany;

public class ServiceCompany {
	private static final ServiceCompany INSTANCE = new ServiceCompany();
	private MapperCompany mapperCompany;
	private DAOCompany daoCompany;
	
	private ServiceCompany() {
		mapperCompany = MapperCompany.getInstance();
		daoCompany = DAOCompany.getInstance();
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton ServiceCompany.
	 * @return Un objet de type ServiceCompany
	 */
	public static ServiceCompany getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Méthode qui récupère la liste des entreprises.
	 * @return Une ArrayList de DTOCompany
	 */
	public ArrayList<DTOCompany> requestList() {
		return mapperCompany.modelCompanyListToDTOCompanyList(daoCompany.requestList());
	}
}
