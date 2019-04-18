package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

public class MapperComputer implements MapperInterface {
	public MapperComputer() {
	}
	
	public DTOComputer modelComputerToDTO(ModelComputer modelComputer) {
		return (new DTOComputer(String.valueOf(modelComputer.getId()), 
				modelComputer.getName(), modelComputer.getDi().toString(), 
				modelComputer.getDd().toString(), modelComputer.getManufacturer()));
	}
	
	public ModelComputer dtoComputerToModelComputer(DTOComputer dtoComputer) {
		int id = -1;
		Timestamp di = null;
		try {
			id = Integer.parseInt(dtoComputer.getId());
		} catch (NumberFormatException nfe) {
			System.err.println("Pas un integer");
		}
		try {
			Timestamp.valueOf(dtoComputer.getDi());
		} catch (IllegalArgumentException iae) {
			System.err.println("Pas de Timestamp");
		}
		// TODO: autre Timestamp + company_id
		return null;
	}
}
