package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;
import com.excilys.cdb.model.ModelPage;
import com.excilys.cdb.persistence.DAOComputer;

@Service
public class ServiceComputer {
	private DAOComputer daoComputer;
	
	public ServiceComputer(DAOComputer daoComputer) {
		this.daoComputer = daoComputer;
	}
	
	/**
	 * Méthode qui récupère la liste des machines par pages et par un nombre d'éléments données.
	 * 
	 * @param pageNumber Un entier représentant le numéro de la page à afficher
	 * @param numberOfElement Un entier représantant le nombre d'éléments à afficher par page
	 * @return Une ArrayList de ModelComputerShort
	 * @throws Exception 
	 */
	public ArrayList<ModelComputerShort> requestListLimit(int pageNumber, int numberOfElement) throws Exception {
		return daoComputer.requestListLimit(pageNumber, numberOfElement);
	}
	
	public ArrayList<ModelComputer> requestCompleteListLimit(int pageNumber, int numberOfElement) throws Exception {
		return daoComputer.requestCompleteListLimit(pageNumber, numberOfElement);
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
	
	public ModelComputer requestCreate(ModelComputer modelComputer) throws Exception {
		return daoComputer.requestCreate(modelComputer);
	}
	
	public ModelComputer requestUpdate(ModelComputer modelComputer) throws Exception {
		return daoComputer.requestUpdate(modelComputer);
	}
	
	public ModelComputer requestDelete(int id) {
		return daoComputer.requestDelete(id);
	}
}
