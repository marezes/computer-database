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
		this.serviceCompany = ServiceCompany.getInstance();
		this.serviceComputer = ServiceComputer.getInstance();
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
			ArrayList<DTOComputerShort> resultDtoComputerShort = serviceComputer.requestListLimit(pageNumber, numberOfElement);
			resultDtoComputerShort.stream()
				.map(object -> object.toString())
				.forEach(str -> response.add(str));
			break;
		case LIST_COMPANY:
			ArrayList<DTOCompany> resultDtoCompany = serviceCompany.requestList();
			resultDtoCompany.stream()
				.map(object -> object.toString())
				.forEach(str -> response.add(str));
			break;
		case SHOW_COMPUTER_DETAILS:
			id = args[1];
			DTOComputer dtoComputer = serviceComputer.requestComputerDetails(id);
			response.add(dtoComputer.toString());
			break;
		case CREATE_COMPUTER:
			if (args[1].equals("")) {
				break; // à changer par une exception
			}
			name = args[1];
			introduced = (args[2].equals("")?null:args[2]);
			discontinued = (args[3].equals("")?null:args[3]);
			companyId = (args[4].equals("")?null:args[4]);
			DTOComputer dtoComputerToCreate = new DTOComputer(null, name, introduced, discontinued, new DTOCompany(companyId, null));
			response.add((serviceComputer.requestCreate(dtoComputerToCreate)).toString());
			break;
		case UPDATE_COMPUTER:
			name = args[1];
			introduced = args[2];
			discontinued = args[3];
			companyId = args[4];
			DTOComputer dtoComputerToUpdate = new DTOComputer("0", name, introduced, discontinued, new DTOCompany(companyId, null));
			serviceComputer.requestUpdate(dtoComputerToUpdate);
			// TODO: ajouter la partie de renvoie de résultat pour confirmer la mise à jours
			break;
		case DELETE_COMPUTER:
			id = args[1];
			DTOComputer dtoComputerDeleted = serviceComputer.requestDelete(id);
			response.add(dtoComputerDeleted.toString());
			break;
		case EXIT_PROGRAM: break;
		case UNKNOWN: break;
		}
		
		return response;
	}
	
//	/**
//	 * Méthode qui vérifie que l'argument n'est pas vide, ai le bon nombre, et qu'il
//	 * n'y ait pas de String vide.
//	 * @param args La liste des arguments
//	 * @return Un booléen pour dire si c'est bon ou pas
//	 */
//	private boolean verifyArgs(String[] args) {
//		if (args.length <= 0 || args.length > 5) {
//			return false;
//		}
//		if (args[0].length() == 0) {
//			return false;
//		} else {
//			for (int i = 1; i < args.length; i++) {
//				if (args[i] == null) {
//					continue;
//				} else if (args[i].length() == 0) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
}
