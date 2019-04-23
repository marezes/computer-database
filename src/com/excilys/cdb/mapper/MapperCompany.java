package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.model.ModelCompany;

public class MapperCompany {
	public MapperCompany() {
	}
	
	public DTOCompany modelCompanyToDTO(ModelCompany modelCompany) {
		return (new DTOCompany(String.valueOf(modelCompany.getId()), modelCompany.getName()));
	}
	
	public ModelCompany dtoCompanyToModelCompany(DTOCompany dtoCompany) {
		int id = -1;
		try {
			id = Integer.parseInt(dtoCompany.getId());
		} catch (NumberFormatException nfe) {
			System.err.println("Pas un integer");
		}
		return (new ModelCompany(id, dtoCompany.getName()));
	}
}
