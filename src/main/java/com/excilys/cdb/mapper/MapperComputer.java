package com.excilys.cdb.mapper;

import java.sql.Timestamp;
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
			String id = String.valueOf(modelComputerShort.getId());
			String name = modelComputerShort.getName();
			dtoComputerShort = new DTOComputerShort(id, name);
			dtoComputerShortList.add(dtoComputerShort);
		}
		
		return dtoComputerShortList;
	}
	
	public DTOComputer modelComputerToDTOComputer(ModelComputer modelComputer) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		String id = String.valueOf(modelComputer.getId());
		
		String name = modelComputer.getName();
		
		String introduced =  (modelComputer.getIntroduced() == null) ? null : (modelComputer.getIntroduced()).toString();
		
		String discontinued = (modelComputer.getDiscontinued() == null) ? null : (modelComputer.getDiscontinued()).toString();
		
		String companyId = String.valueOf(modelComputer.getModelCompany().getId());
		
		String companyName = (modelComputer.getModelCompany().getName() == null) ? null : (modelComputer.getModelCompany().getName()).toString();
		
		return (new DTOComputer(id, name, introduced, discontinued, companyId, companyName));
	}

    public ModelComputer dtoComputerToModelComputer(DTOComputer dtoComputer) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
    	
    	Integer id = null;
        String name;
        Timestamp introduced = null;
        Timestamp discontinued = null;
        Integer companyId = null;
        String companyName;

        try {
        	id = Integer.parseInt(dtoComputer.getId());
        } catch (NumberFormatException nfe) {
        	throw nfe;
        }
        name = dtoComputer.getName();

        try {
            introduced = (dtoComputer.getIntroduced().equals("")) ? null : Timestamp.valueOf(dtoComputer.getIntroduced());
        } catch (IllegalArgumentException iae) {
            // System.err.println("Pas de Timestamp introduced");
            throw iae;
        }

        try {
            discontinued = (dtoComputer.getDiscontinued().equals("")) ? null : Timestamp.valueOf(dtoComputer.getDiscontinued());
        } catch (IllegalArgumentException iae) {
            // System.err.println("Pas de Timestamp discontinued");
            throw iae;
        }
        
        try {
            companyId = Integer.parseInt(dtoComputer.getCompanyId());
        } catch (NumberFormatException nfe) {
            // System.err.println("Pas un integer pour companyId");
            throw nfe;
        }

        companyName = dtoComputer.getCompanyName();

        return (new ModelComputer(id, name, introduced, discontinued, new ModelCompany(companyId, companyName)));
    }
}
