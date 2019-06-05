package com.excilys.cdb.controller;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.enumeration.MagicNumber;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Controller {
	public static final Controller INSTANCE = new Controller();
	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	private MapperCompany mapperCompany;
	private MapperComputer mapperComputer;
	
	private Controller() {
		try {
			this.serviceCompany = ServiceCompany.getInstance();
			this.serviceComputer = ServiceComputer.getInstance();
			this.mapperCompany = MapperCompany.getInstance();
			this.mapperComputer = MapperComputer.getInstance();
		} catch (Exception e) {
			printErrors(e);
		}
	}

	public static Controller getInstance() {
		return INSTANCE;
	}
	
	public ArrayList<String> process(String... args) {
		/* Partie avec une classe de validator qui devrait renvoyer une exception
		 * si c'est pas bon mais qui va envoyer en attendant null */
		
		ArrayList<String> response = new ArrayList<String>();
		int id;
		String name;
		Timestamp introduced;
		Timestamp discontinued;
		int companyId;
		
		switch(MagicNumber.getEnum(args[0])) {
		case LIST_COMPUTER:
			/* Partie validateur à ajouter */
			int pageNumber = -1;
			int numberOfElement = -1;
			try {
				pageNumber = Integer.parseInt(args[1]);
				numberOfElement = Integer.parseInt(args[2]);
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			
			ArrayList<DTOComputerShort> resultDtoComputerShort = null;
			try {
				resultDtoComputerShort = mapperComputer.modelComputerShortListToDTOComputerShortList(
						serviceComputer.requestListLimit(pageNumber, numberOfElement));
			} catch (Exception e) {
				printErrors(e);
			}
			
			if (resultDtoComputerShort != null) {
				resultDtoComputerShort.stream()
					.map(object -> object.toString())
					.forEach(str -> response.add(str));
			}
			break;
		case LIST_COMPANY:
			ArrayList<DTOCompany> resultDtoCompany = null;
			try {
				resultDtoCompany = mapperCompany.modelCompanyListToDTOCompanyList(serviceCompany.requestList());
			} catch (Exception e) {
				printErrors(e);
			}
			if (resultDtoCompany != null) {
				resultDtoCompany.stream()
					.map(object -> object.toString())
					.forEach(str -> response.add(str));
			}
			break;
		case SHOW_COMPUTER_DETAILS:
			/* Partie validateur à ajouter */
			id = -1;
			try {
				id = Integer.parseInt(args[1]);
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			DTOComputer dtoComputer = null;
			try {
				dtoComputer = mapperComputer.modelComputerToDTOComputer(serviceComputer.requestComputerDetails(id));
			} catch (Exception e) {
				printErrors(e);
			}
			if (dtoComputer != null) {
				response.add(dtoComputer.toString());
			}
			break;
		case CREATE_COMPUTER:
			/* Partie validateur à ajouter */
			if (args[1].equals("")) {
				break; // à changer par une exception
			}
			name = args[1];
			introduced = null;
			discontinued = null;
			companyId = -1;
			try {
				introduced = (args[2].equals("") ? null : Timestamp.valueOf(args[2]));
				discontinued = (args[3].equals("") ? null : Timestamp.valueOf(args[3]));
				companyId = (args[4].equals("") ? null : Integer.parseInt(args[4]));
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			ModelComputer modelComputerToCreate = new ModelComputer(null, name, introduced, discontinued, new ModelCompany(companyId, null));
			try {
				response.add((mapperComputer.modelComputerToDTOComputer(serviceComputer.requestCreate(modelComputerToCreate)).toString()));
			} catch (Exception e) {
				printErrors(e);
			}
			break;
		case UPDATE_COMPUTER:
			/* Partie validateur à ajouter */
			id = -1;
			try {
				id = Integer.parseInt(args[1]);
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			
			ModelComputer modelComputerAlreadyInDataBase = null;
			try {
				modelComputerAlreadyInDataBase = serviceComputer.requestComputerDetails(id);
			} catch (Exception e) {
				// TODO: Si pas dans la base
			}
			
			name = "";
			if (args[2].equals("")) {
				// Exception du validateur
			} else {
				name = args[2];
			}
			
			introduced = null;
			discontinued = null;
			companyId = -1;
			try {
				introduced = args[3].equals("vider") ? null :
					(args[3].equals("")
							? modelComputerAlreadyInDataBase.getIntroduced()
									: Timestamp.valueOf(args[3]));
				discontinued = args[4].equals("vider") ? null :
					(args[4].equals("")
							? modelComputerAlreadyInDataBase.getDiscontinued()
									: Timestamp.valueOf(args[4]));
				companyId = args[5].equals("vider") ? null :
					(args[5].equals("")
							? modelComputerAlreadyInDataBase.getModelCompany().getId()
									: Integer.parseInt(args[5]));
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			ModelComputer modelComputerToUpdate = new ModelComputer(id, name, introduced, discontinued, new ModelCompany(companyId, null));
			try {
				response.add(mapperComputer.modelComputerToDTOComputer(serviceComputer.requestUpdate(modelComputerToUpdate)).toString());
			} catch (Exception e) {
				printErrors(e);
			}
			break;
		case DELETE_COMPUTER:
			/* Partie validateur à ajouter */
			id = -1;
			try {
				id = Integer.parseInt(args[1]);
			} catch (Exception e) {
				// TODO: Normalement catché dans le validateur
			}
			
			ModelComputer modelComputerDeleted = null;
			try {
				modelComputerDeleted = serviceComputer.requestDelete(id);
			} catch (Exception e) {
				printErrors(e);
			}
			response.add(mapperComputer.modelComputerToDTOComputer(modelComputerDeleted).toString());
			break;
		case EXIT_PROGRAM: break;
		case UNKNOWN: break;
		}
		
		return response;
	}

	public void printErrors(Exception e) {
		System.err.println(e.getMessage());
	}
}
