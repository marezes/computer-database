package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelPage;
import com.excilys.cdb.persistence.DAOComputer;

@Service
public class ServiceComputer {
	private DAOComputer daoComputer;
	
	public ServiceComputer(DAOComputer daoComputer) {
		this.daoComputer = daoComputer;
	}
	
	public ArrayList<ModelComputer> requestListPage(ModelPage modelPage) throws Exception {
		return daoComputer.requestListPage(modelPage);
	}
	
	public ArrayList<ModelComputer> requestListPageSearched(ModelPage modelPage) throws Exception {
		return daoComputer.requestListPageSearched(modelPage);
	}
	
	public int requestTotalNumberOfComputersFound(String nameSearched) throws Exception {
		return daoComputer.requestTotalNumberOfComputersFound(nameSearched);
	}
	
	public int requestTotalNumberOfComputers() throws Exception {
		return daoComputer.requestTotalNumberOfComputers();
	}
	
	/**
	 * Méthode qui récupère le détail d'une machine par son id.
	 * @return Un objet de type DTOComputer
	 * @throws Exception 
	 */
	public ModelComputer requestComputerDetails(int id) throws Exception {
		return daoComputer.requestById(id);
	}
	
	public boolean requestCreate(ModelComputer modelComputer) throws Exception {
		return daoComputer.requestCreate(modelComputer);
	}
	
	public boolean requestUpdate(ModelComputer modelComputer) throws Exception {
		return daoComputer.requestUpdate(modelComputer);
	}
	
	public boolean requestDelete(int id) {
		return daoComputer.requestDelete(id);
	}
}
