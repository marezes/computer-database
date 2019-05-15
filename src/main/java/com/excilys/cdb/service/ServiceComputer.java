package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;
import com.excilys.cdb.model.ModelPage;
import com.excilys.cdb.persistence.DAOComputer;

public class ServiceComputer {
	private static ServiceComputer INSTANCE = null;
	private DAOComputer daoComputer;
	
	private ServiceComputer() throws Exception {
		MapperComputer.getInstance();
		daoComputer = DAOComputer.getInstance();
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton ServiceComputer.
	 * @return Un objet de type ServiceComputer
	 * @throws Exception 
	 */
	public static ServiceComputer getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new ServiceComputer();
		}
		return INSTANCE;
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
	
	public ModelPage requestListPage(ModelPage modelPage) throws Exception {
		return daoComputer.requestListPage(modelPage);
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
	
	public ModelComputer requestDelete(int id) throws Exception {
		return daoComputer.requestDelete(id);
	}
}
