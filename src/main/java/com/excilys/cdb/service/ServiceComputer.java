package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOComputerShort;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.persistence.DAOComputer;

public class ServiceComputer {
	private static ServiceComputer INSTANCE = null;
	private MapperComputer mapperComputer;
	private DAOComputer daoComputer;
	
	private ServiceComputer() throws Exception {
		mapperComputer = MapperComputer.getInstance();
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
	 * @return Une ArrayList de DTOComputerShort
	 * @throws Exception 
	 */
	public ArrayList<DTOComputerShort> requestListLimit(String pageNumber, String numberOfElement) throws Exception {
		int pageNumberList = -1;
		int numberOfElementList = -1;
		try {
			pageNumberList = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			// System.err.println("pageNumber n'est pas un entier");
			throw e;
		}
		try {
			numberOfElementList = Integer.parseInt(numberOfElement);
		} catch (NumberFormatException e) {
			// System.err.println("pageNumber n'est pas un entier");
			throw e;
		}
		
		return mapperComputer.modelComputerShortListToDTOComputerShortList(daoComputer.requestListLimit(pageNumberList, numberOfElementList));
	}
	
	public ArrayList<DTOComputer> requestCompleteListLimit(String pageNumber, String numberOfElement) throws Exception {
		int pageNumberList = -1;
		int numberOfElementList = -1;
		try {
			pageNumberList = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			// System.err.println("pageNumber n'est pas un entier");
			throw e;
		}
		try {
			numberOfElementList = Integer.parseInt(numberOfElement);
		} catch (NumberFormatException e) {
			// System.err.println("pageNumber n'est pas un entier");
			throw e;
		}
		
		return mapperComputer.modelComputerListToDTOComputerList(daoComputer.requestCompleteListLimit(pageNumberList, numberOfElementList));
	}
	
	/**
	 * Méthode qui récupère le détail d'une machine par son id.
	 * @return Un objet de type DTOComputer
	 * @throws Exception 
	 */
	public DTOComputer requestComputerDetails(String id) throws Exception {
		int idComputer = -1;
		try {
			idComputer = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
			//System.err.println("Problème au serviceComputer, pas un integer");
			throw nfe;
		}
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestById(idComputer));
	}
	
	public DTOComputer requestCreate(DTOComputer dtoComputer) throws Exception {
		ModelComputer modelComputer = mapperComputer.dtoComputerToModelComputer(dtoComputer);
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestCreate(modelComputer));
	}
	
	public DTOComputer requestUpdate(DTOComputer dtoComputer) throws Exception {
		DTOComputer dtoComputerAlreadyInDatabase = requestComputerDetails(dtoComputer.getId()); // exception si pas d'élément existant
		
		if (dtoComputer.getIntroduced().equals("")) {
			dtoComputer.setName(dtoComputerAlreadyInDatabase.getName());
		}
		
		if (dtoComputer.getIntroduced().equals("vider")) {
			dtoComputer.setIntroduced("");
		} else if (dtoComputer.getIntroduced().equals("")) {
			dtoComputer.setIntroduced(dtoComputerAlreadyInDatabase.getIntroduced());
		}
		
		if (dtoComputer.getDiscontinued().equals("vider")) {
			dtoComputer.setDiscontinued("");
		} else if (dtoComputer.getDiscontinued().equals("")) {
			dtoComputer.setDiscontinued(dtoComputerAlreadyInDatabase.getDiscontinued());
		}
		
		if (dtoComputer.getCompanyId().equals("vider")) {
			dtoComputer.setCompanyId("");
			dtoComputer.setCompanyName("");
		} else if (dtoComputer.getCompanyId().equals("")) {
			dtoComputer.setCompanyId(dtoComputerAlreadyInDatabase.getCompanyId());
			dtoComputer.setCompanyName(dtoComputerAlreadyInDatabase.getCompanyName());
		}
		
		ModelComputer modelComputer = mapperComputer.dtoComputerToModelComputer(dtoComputer);
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestUpdate(modelComputer));
	}
	
	public DTOComputer requestDelete(String id) throws Exception {
		int idComputer = -1;
		try {
			idComputer = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
			// System.err.println("Problème au serviceComputer, pas un integer");
			throw nfe;
		}
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestDelete(idComputer));
	}
}
