package com.excilys.cdb.mapper;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.model.ModelCompany;

public class MapperCompany {
	private static final MapperCompany INSTANCE = new MapperCompany();
	
	private MapperCompany() {
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton MapperCompany.
	 * @return Un objet de type MapperCompany
	 */
	public static MapperCompany getInstance() {
		return INSTANCE;
	}
	
	public ArrayList<DTOCompany> modelCompanyListToDTOCompanyList(ArrayList<ModelCompany> modelCompanyList) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		ArrayList<DTOCompany> dtoCompanyList = new ArrayList<DTOCompany>();
		DTOCompany dtoCompany = null;
		
		for (ModelCompany modelCompany : modelCompanyList) {
			String id = String.valueOf(modelCompany.getId());
			String name = modelCompany.getName();
			dtoCompany = new DTOCompany(id, name);
			dtoCompanyList.add(dtoCompany);
		}
		
		return dtoCompanyList;
	}
}
