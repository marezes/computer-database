package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOComputerShort;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;

public class MapperComputer {
	private static MapperComputer INSTANCE = null;
	
    private MapperComputer() {
    }
	
	/**
	 * Méthode qui renvoie l'objet singleton MapperComputer.
	 * @return Un objet de type MapperComputer
	 */
	public static MapperComputer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MapperComputer();
		}
		return INSTANCE;
	}
	
	public ArrayList<DTOComputerShort> modelComputerShortListToDTOComputerShortList(ArrayList<ModelComputerShort> modelComputerShortList) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		ArrayList<DTOComputerShort> dtoComputerShortList = new ArrayList<DTOComputerShort>();
		DTOComputerShort dtoComputerShort = null;
		
		for (ModelComputerShort modelComputerShort : modelComputerShortList) {
			Integer id = modelComputerShort.getId();
			String name = modelComputerShort.getName();
			dtoComputerShort = new DTOComputerShort.DTOComputerShortBuilder(name)
					.withId(id)
					.build();
			dtoComputerShortList.add(dtoComputerShort);
		}
		
		return dtoComputerShortList;
	}
	
	public ArrayList<DTOComputer> modelComputerListToDTOComputerList(ArrayList<ModelComputer> modelComputerList) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		ArrayList<DTOComputer> dtoComputerList = new ArrayList<DTOComputer>();
		
		for (ModelComputer modelComputer : modelComputerList) {
			Integer id = modelComputer.getId();
			String name = modelComputer.getName();
			
			LocalDate introduced =  
					(modelComputer.getIntroduced() == null) 
					? null : (modelComputer.getIntroduced());
			
			LocalDate discontinued = 
					(modelComputer.getDiscontinued() == null) 
					? null : (modelComputer.getDiscontinued());
			
			Integer companyId = 
					(modelComputer.getModelCompany().getId()) == null 
					? null : (modelComputer.getModelCompany().getId());
			
			String companyName = 
					(modelComputer.getModelCompany().getName() == null) 
					? null : (modelComputer.getModelCompany().getName()).toString();
			
			dtoComputerList.add(new DTOComputer.DTOComputerBuilder(name)
					.withId(id)
					.withIntroduced(introduced)
					.withDiscontinued(discontinued)
					.withCompanyId(companyId)
					.withCompanyName(companyName)
					.build());
		}
		
		return dtoComputerList;
	}
	
	public DTOComputer modelComputerToDTOComputer(ModelComputer modelComputer) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		Integer id = modelComputer.getId();
		
		String name = modelComputer.getName();
		
		LocalDate introduced =  
				(modelComputer.getIntroduced() == null) 
				? null : (modelComputer.getIntroduced());
		
		LocalDate discontinued = 
				(modelComputer.getDiscontinued() == null) 
				? null : (modelComputer.getDiscontinued());
		
		Integer companyId = 
				(modelComputer.getModelCompany().getId()) == null 
				? null : (modelComputer.getModelCompany().getId());
		
		String companyName = 
				(modelComputer.getModelCompany().getName() == null) 
				? null : (modelComputer.getModelCompany().getName());
		
		return (new DTOComputer.DTOComputerBuilder(name)
				.withId(id)
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withCompanyId(companyId)
				.withCompanyName(companyName)
				.build());
	}

    public ModelComputer dtoComputerToModelComputer(DTOComputer dtoComputer) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
    	
    	Integer id = dtoComputer.getId();
        
    	String name = dtoComputer.getName();
        
    	LocalDate introduced = null;
        try {
            introduced = 
            		(dtoComputer.getIntroduced() == null) 
            		? null : dtoComputer.getIntroduced();
        } catch (DateTimeParseException dtpe) {
            // System.err.println("Pas de LocalDate introduced");
            throw dtpe;
        }

        LocalDate discontinued = null;
        try {
            discontinued = 
            		(dtoComputer.getDiscontinued() == null) 
            		? null : dtoComputer.getDiscontinued();
        } catch (DateTimeParseException dtpe) {
            // System.err.println("Pas de LocalDate discontinued");
            throw dtpe;
        }

        Integer companyId = dtoComputer.getCompanyId();

        String companyName = dtoComputer.getCompanyName();

        return (new ModelComputer.ModelComputerBuilder(name)
				.withId(id)
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withModelCompany(
						new ModelCompany.ModelCompanyBuilder()
							.withId(companyId)
							.withName(companyName)
							.build())
				.build());
    }
}
