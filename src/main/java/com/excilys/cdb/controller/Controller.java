package com.excilys.cdb.controller;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.enumeration.MagicNumber;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

import java.util.ArrayList;

public class Controller {
	public static final Controller INSTANCE = new Controller();
	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	
	private Controller() {
		try {
			this.serviceCompany = ServiceCompany.getInstance();
			this.serviceComputer = ServiceComputer.getInstance();
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
		String id;
		String name;
		String introduced;
		String discontinued;
		String companyId;
		
		switch(MagicNumber.getEnum(args[0])) {
		case LIST_COMPUTER:
			String pageNumber = args[1];
			String numberOfElement = args[2];
			ArrayList<DTOComputerShort> resultDtoComputerShort = null;
			try {
				resultDtoComputerShort = serviceComputer.requestListLimit(pageNumber, numberOfElement);
			} catch (Exception e) {
				printErrors(e);
			}
			resultDtoComputerShort.stream()
				.map(object -> object.toString())
				.forEach(str -> response.add(str));
			break;
		case LIST_COMPANY:
			ArrayList<DTOCompany> resultDtoCompany = null;
			try {
				resultDtoCompany = serviceCompany.requestList();
			} catch (Exception e) {
				printErrors(e);
			}
			resultDtoCompany.stream()
				.map(object -> object.toString())
				.forEach(str -> response.add(str));
			break;
		case SHOW_COMPUTER_DETAILS:
			id = args[1];
			DTOComputer dtoComputer = null;
			try {
				dtoComputer = serviceComputer.requestComputerDetails(id);
			} catch (Exception e) {
				printErrors(e);
			}
			response.add(dtoComputer.toString());
			break;
		case CREATE_COMPUTER:
			if (args[1].equals("")) {
				break; // à changer par une exception
			}
			name = args[1];
			introduced = (args[2].equals("") ? null : args[2]);
			discontinued = (args[3].equals("") ? null : args[3]);
			companyId = (args[4].equals("") ? null : args[4]);
			DTOComputer dtoComputerToCreate = new DTOComputer(null, name, introduced, discontinued, companyId, null);
			try {
				response.add((serviceComputer.requestCreate(dtoComputerToCreate)).toString());
			} catch (Exception e) {
				printErrors(e);
			}
			break;
		case UPDATE_COMPUTER:
			id = args[1];
			name = args[2];
			introduced = args[3];
			discontinued = args[4];
			companyId = args[5];
			DTOComputer dtoComputerToUpdate = new DTOComputer(id, name, introduced, discontinued, companyId, null);
			try {
				response.add(serviceComputer.requestUpdate(dtoComputerToUpdate).toString());
			} catch (Exception e) {
				printErrors(e);
			}
			break;
		case DELETE_COMPUTER:
			id = args[1];
			DTOComputer dtoComputerDeleted = null;
			try {
				dtoComputerDeleted = serviceComputer.requestDelete(id);
			} catch (Exception e) {
				printErrors(e);
			}
			response.add(dtoComputerDeleted.toString());
			break;
		case EXIT_PROGRAM: break;
		case UNKNOWN: break;
		}
		
		return response;
	}

	public void printErrors(Exception e) {
		System.err.println(e.getMessage());
		//e.printStackTrace();
	}
}
