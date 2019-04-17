package com.excilys.cdb.ui;

import com.excilys.cdb.controller.*;
import com.excilys.cdb.dto.*;

import java.util.ArrayList;
import java.util.Scanner;

public class UI implements UIInterface {
	private Controller controller;
	private String request;			// La demande de l'utilisateur
	private String id;				// un id
	private String name;			// Nom de l'ordinateur
	private String di;				// date introduced
	private String dd;				// date discontinued
	private String manufacturer;	// le fabricant
	private ArrayList<DTO> response;		// La réponse reçu
	private boolean stop;			// Booléen pour l'arrêt de la boucle principale
	private Scanner sc;				// Le scanner pour récupérer ce que l'utilisateur écrit (à changer)
	
	/**
	 * Constructeur sans argument.
	 */
	public UI() {
		stop = false;
		sc = new Scanner(System.in); // (à changer)
	}
	
	/**
	 * Méthode qui sert d'initalisation à tout l'arbre.
	 * Donc, ici précisément, on créer le controleur.
	 */
	public void init() {
		this.controller = new Controller();
	}
	
	/**
	 * Méthode qui permet l'intéraction humain-machine.
	 */
	public void start() {
		System.out.println("Bonjour et bienvenue sur votre espace utilisateur");
		DTOComputer dtocomputer = null;
		DTOCompany dtocompany = null;
		
		while(!stop) {
			menu();
			System.out.print("Que voulez-vous faire : ");
			request = sc.nextLine();
			switch(request) {
			case "0": // list computer
				response = controller.process(request);
				System.out.println("  id  |  name");
				for (int i = 0; i < response.size(); i++) {
					dtocomputer = (DTOComputer) response.get(i);
					System.out.println(dtocomputer.getId() + " " + dtocomputer.getName());
				}
				if (response.size() == 0) {
					System.out.println("Pas de machine.");
				}
				response = null;
				break;
			case "1": // list company
				response = controller.process(request);
				System.out.println("  id  |  name");
				for (int i = 0; i < response.size(); i++) {
					dtocompany = (DTOCompany) response.get(i);
					System.out.println(dtocompany.getId() + " " + dtocompany.getName());
				}
				if (response.size() == 0) {
					System.out.println("Pas de machine.");
				}
				response = null;
				break;
			case "2": // show computer details
				System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
				id = sc.nextLine();
				response = controller.process(request, id);
				System.out.println("  id  |  name  |  di  |  dd  |  manufacturer");
				dtocomputer = (DTOComputer) response.get(0);
				System.out.println(dtocomputer.getId() + " " + dtocomputer.getName() + " "
						+ dtocomputer.getDi() + " " + dtocomputer.getDd() + " "
						+ dtocomputer.getManufacturer());
				if (response.size() == 0) {
					System.out.println("Pas de machine.");
				}
				response = null;
				break;
			case "3": // create new computer
				createNewComputer();
				response = controller.process(request, name, di, dd, manufacturer);
				//responseToPrint();
				break;
			case "4": // update computer
				System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
				id = sc.nextLine();
				response = controller.process(request, id);
				if (response.get(0).equals("-1")) {
					//responseToPrint();
				} else {
					//updateComputer();
					response = controller.process(request, name, di, dd, manufacturer);
					//responseToPrint();
				}
				break;
			case "5": // delete computer
				System.out.print("Donnez l'id de la machine que vous voulez supprimer : ");
				id = sc.nextLine();
				response = controller.process(request, id);
				//responseToPrint();
				break;
			case "6": // quit program
				stop = true;
				break;
			default:
				System.out.println("Argument " + request + " inconnu");
				System.out.println("Recommencez");
				break;
			}
		}
		sc.close();
	}
	
	/**
	 * Méthode qui ne fait qu'afficher les choix possibles.
	 */
	private void menu() {
		System.out.println("*********************| MENU |*********************");
		System.out.println(" Voici un récapitulatif des options:");
		System.out.println("	0 : Lister les machines");
		System.out.println("	1 : Lister les entreprises");
		System.out.println("	2 : Afficher le détail d'une machine");
		System.out.println("	3 : Créer une nouvelle machine");
		System.out.println("	4 : Mettre à jour une machine");
		System.out.println("	5 : Supprimer une machine");
		System.out.println("	6 : Quitter le programme");
		System.out.println("**************************************************");
	}
	
//	/**
//	 * Méthode qui affiche le message final ou d'erreur.
//	 * (A changer par la création d'exceptions personnalisées.)
//	 */
//	private void responseToPrint() {
//		switch(response[0]) {
//		case "-1": // message d'erreur
//			System.out.println(response[1]);
//			break;
//		case "0":
//			System.out.println("  id  |  name");
//			for (int i = 1; i < response.length; i++) {
//				System.out.println(response[i]);
//			}
//			break;
//		case "1":
//			System.out.println("  id  |  name");
//			for (int i = 1; i < response.length; i++) {
//				System.out.println(response[i]);
//			}
//			break;
//		case "2":
//			System.out.println("  id  |  name  |  di  |  dd  |  manufacturer");
//			for (int i = 1; i < response.length; i++) {
//				System.out.println(response[i]);
//			}
//			break;
//		case "3":
//			System.out.println("Machine " + name + " créée.");
//			break;
//		case "4":
//			System.out.println("Machine " + name + " mise à jour.");
//			break;
//		case "5":
//			System.out.println("Machine " + name + " supprimé.");
//			break;
//		default:
//			System.out.println("On ne devrait pas arriver dans le default");
//			break;
//		}
//	}
	
	/**
	 * Méthode qui demande les informations pour créer une nouvalle machine.
	 */
	private void createNewComputer() {
		System.out.print("Donnez le nom de la machine que vous voulez créer : ");
		name = sc.nextLine();
		System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
		di = sc.nextLine();
		System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
		dd = sc.nextLine();
		System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
		manufacturer = sc.nextLine();
	}
	
//	private void updateComputer() {
//		System.out.println("Si vous ne voulez pas changer une entré, tapez sur la touche entrée");
//		System.out.print("Changer le nom de la machine [" + response[1] + "] ? ");
//		name = sc.nextLine();
//		if (name.equals("")) {
//			name = response[1];
//		}
//		System.out.print("Changer le di de la machine [" + response[2] + "] ? ");
//		di = sc.nextLine();
//		if (di.equals("")) {
//			di = response[2];
//		}
//		System.out.print("Changer le dd de la machine [" + response[3] + "] ? ");
//		dd = sc.nextLine();
//		if (dd.equals("")) {
//			dd = response[3];
//		}
//		System.out.print("Changer le manufacturer de la machine [" + response[4] + "] ? ");
//		manufacturer = sc.nextLine();
//		if (manufacturer.equals("")) {
//			manufacturer = response[4];
//		}
//	}
}
