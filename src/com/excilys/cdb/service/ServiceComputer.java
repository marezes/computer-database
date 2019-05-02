package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOComputerShort;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.persistence.DAOComputer;

public class ServiceComputer {
	private static final ServiceComputer INSTANCE = new ServiceComputer();
	private MapperComputer mapperComputer;
	private DAOComputer daoComputer;
	
	private ServiceComputer() {
		mapperComputer = MapperComputer.getInstance();
		daoComputer = DAOComputer.getInstance();
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton ServiceComputer.
	 * @return Un objet de type ServiceComputer
	 */
	public static ServiceComputer getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Méthode qui récupère la liste des machines.
	 * @return Une ArrayList de DTOComputerShort
	 */
	public ArrayList<DTOComputerShort> requestList() {
		return mapperComputer.modelComputerShortListToDTOComputerShortList(daoComputer.requestList());
	}
	
	public ArrayList<DTOComputerShort> requestListLimit(String pageNumber, String numberOfElement) {
		int pageNumberList = -1;
		int numberOfElementList = -1;
		try {
			pageNumberList = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			System.err.println("pageNumber n'est pas un entier");
		}
		try {
			numberOfElementList = Integer.parseInt(numberOfElement);
		} catch (NumberFormatException e) {
			System.err.println("pageNumber n'est pas un entier");
		}
		
		return mapperComputer.modelComputerShortListToDTOComputerShortList(daoComputer.requestListLimit(pageNumberList, numberOfElementList));
	}
	
	/**
	 * Méthode qui récupère le détail d'une machine par son id.
	 * @return Un objet de type DTOComputer
	 */
	public DTOComputer requestComputerDetails(String id) {
		int idComputer = -1;
		try {
			idComputer = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
			System.err.println("Problème au serviceComputer, pas un integer");
		}
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestById(idComputer));
	}
	
	public DTOComputer requestCreate(DTOComputer dtoComputer) {
		ModelComputer modelComputer = mapperComputer.dtoComputerToModelComputer(dtoComputer);
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestCreate(modelComputer));
	}
	
	public DTOComputer requestUpdate(DTOComputer dtoComputer) {
		ModelComputer modelComputer = mapperComputer.dtoComputerToModelComputer(dtoComputer);
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestUpdate(modelComputer));
	}
	
	public DTOComputer requestDelete(String id) {
		int idComputer = -1;
		try {
			idComputer = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
			System.err.println("Problème au serviceComputer, pas un integer");
		}
		return mapperComputer.modelComputerToDTOComputer(daoComputer.requestDelete(idComputer));
	}
}
