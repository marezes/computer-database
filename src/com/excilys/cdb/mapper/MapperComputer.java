package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOComputerShort;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelComputerShort;

public class MapperComputer {
	private static final MapperComputer INSTANCE = new MapperComputer();
	
    private MapperComputer() {
    }
	
	/**
	 * Méthode qui renvoie l'objet singleton MapperComputer.
	 * @return Un objet de type MapperComputer
	 */
	public static MapperComputer getInstance() {
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
		String introduced = (modelComputer.getIntroduced()).toString();
		String discontinued = (modelComputer.getDiscontinued()).toString();
		String manufacturer = (modelComputer.getManufacturer()).toString();
		
		return (new DTOComputer(id, name, introduced, discontinued, manufacturer));
	}

    public DTOComputer modelToDTO(ModelComputer modelComputer) {
        return null; /*(new DTOComputer(String.valueOf(modelComputer.getId()),
                modelComputer.getName(), modelComputer.getDi().toString(),
                modelComputer.getDd().toString(), modelComputer.getManufacturer()));*/
    }

    public ModelComputer dtoToModel(DTOComputer dtoComputer) {
        int id = -1;
        String name;
        Timestamp di = null;
        Timestamp dd = null;
        String company;

        try {
            id = Integer.parseInt(dtoComputer.getId());
        } catch (NumberFormatException nfe) {
            System.err.println("Pas un integer pour id");
        }

        name = dtoComputer.getName();

        try {
            //di = Timestamp.valueOf(dtoComputer.getDi());
        } catch (IllegalArgumentException iae) {
            System.err.println("Pas de Timestamp di");
        }

        try {
            //Timestamp.valueOf(dtoComputer.getDd());
        } catch (IllegalArgumentException iae) {
            System.err.println("Pas de Timestamp dd");
        }

        company = dtoComputer.getManufacturer();

        return (new ModelComputer(id, name, di, dd, company));
    }
}
