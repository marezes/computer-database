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
		String introduced;
		String discontinued;
		
		if (modelComputer.getIntroduced() != null) {
			introduced = (modelComputer.getIntroduced()).toString();
		} else {
			introduced = null;
		}
		if (modelComputer.getDiscontinued() != null) {
			discontinued = (modelComputer.getDiscontinued()).toString();
		} else {
			discontinued = null;
		}
		String companyId = String.valueOf(modelComputer.getModelCompany().getId());
		String companyName;
		if (modelComputer.getModelCompany().getName() != null) {
			companyName = (modelComputer.getModelCompany().getName()).toString();
		} else {
			companyName = null;
		}
		
		return (new DTOComputer(id, name, introduced, discontinued, companyId, companyName));
	}

    public ModelComputer dtoComputerToModelComputer(DTOComputer dtoComputer) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
    	
        String name;
        Timestamp introduced = null;
        Timestamp discontinued = null;
        Integer companyId = null;
        String companyName;

        name = dtoComputer.getName();

        try {
            introduced = (dtoComputer.getIntroduced().equals("null")) ? null : Timestamp.valueOf(dtoComputer.getIntroduced());
        } catch (IllegalArgumentException iae) {
            // System.err.println("Pas de Timestamp introduced");
            throw iae;
        }

        try {
            discontinued = (dtoComputer.getDiscontinued().equals("null")) ? null : Timestamp.valueOf(dtoComputer.getDiscontinued());
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

        return (new ModelComputer(null, name, introduced, discontinued, new ModelCompany(companyId, companyName)));
    }
}
