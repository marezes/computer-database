package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.persistence.DAOCompany;

@Service
public class ServiceCompany {
	private DAOCompany daoCompany;
	
	public ServiceCompany(DAOCompany daoCompany) {
		this.daoCompany = daoCompany;
	}
	
	/**
	 * Méthode qui récupère la liste des entreprises.
	 * @return Une ArrayList de ModelCompany
	 * @throws Exception 
	 */
	public ArrayList<ModelCompany> requestList() throws Exception {
		return daoCompany.requestList();
	}
	
	public boolean requestDelete(int companyId) throws Exception {
		return daoCompany.requestDelete(companyId);
	}
	
	public ModelCompany requestById(int companyId) throws Exception {
		return daoCompany.requestById(companyId);
	}
}
