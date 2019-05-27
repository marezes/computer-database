package com.excilys.cdb.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.model.ModelCompany;

@Component
public class MapperCompany {
	public MapperCompany() {
	}
	
	public ArrayList<DTOCompany> modelCompanyListToDTOCompanyList(ArrayList<ModelCompany> modelCompanyList) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		ArrayList<DTOCompany> dtoCompanyList = new ArrayList<DTOCompany>();
		DTOCompany dtoCompany = null;
		
		for (ModelCompany modelCompany : modelCompanyList) {
			int id = modelCompany.getId();
			String name = modelCompany.getName();
			dtoCompany = new DTOCompany.DTOCompanyBuilder()
					.withId(id)
					.withName(name)
					.build();
			dtoCompanyList.add(dtoCompany);
		}
		
		return dtoCompanyList;
	}
}
