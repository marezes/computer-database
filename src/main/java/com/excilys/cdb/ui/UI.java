package com.excilys.cdb.ui;

import com.excilys.cdb.controller.*;
import com.excilys.cdb.enumeration.MagicNumber;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
	private Controller controller;
	private String request;			// La demande de l'utilisateur
	private String id;				// un id
	private String name;			// Nom de l'ordinateur
	private String introduced;				// date introduced
	private String discontinued;				// date discontinued
	private String manufacturer;	// le fabricant
	private ArrayList<String> response;		// La réponse reçu
	private boolean stop;			// Booléen pour l'arrêt de la boucle principale
	private Scanner sc;				// Le scanner pour récupérer ce que l'utilisateur écrit (à changer)
	private String pageNumber;		// Le numéro de la page à afficher
	private String numberOfElement;	// Le nombre d'éléments à afficher par page
	
	/**
	 * Constructeur sans argument.
	 */
	public UI() {
		stop = false;
		sc = new Scanner(System.in); // (à changer)
		this.controller = Controller.getInstance();
	}
	
	/**
	 * Méthode qui permet l'intéraction humain-machine.
	 */
	public void start() {
		System.out.println("Bonjour et bienvenue sur votre espace utilisateur");
		
		while(!stop) {
			menu();
			System.out.print("Que voulez-vous faire : ");
			request = sc.nextLine();
			switch(MagicNumber.getEnum(request)) {
			case LIST_COMPUTER:
				System.out.print("Quelle page voulez-vous afficher : ");
				pageNumber = sc.nextLine();
				System.out.print("Combien d'éléments voulez-vous afficher : ");
				numberOfElement = sc.nextLine();
				
				response = controller.process(request, pageNumber, numberOfElement);
				
				if (response.size() > 0) {
					System.out.println("*************** Liste des machines ***************");
					for (String computer : response) {
						System.out.println(computer);
					}
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas de machine.");
				}
				response = null;
				break;
			case LIST_COMPANY:
				response = controller.process(request);
				
				if (response.size() > 0) {
					System.out.println("************** Liste des entreprises *************");
					for (String company : response) {
						System.out.println(company);
					}
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas d'entreprise.");
				}
				response = null;
				break;
			case SHOW_COMPUTER_DETAILS:
				System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
				id = sc.nextLine();
				
				response = controller.process(request, id);
				
				if (response.size() > 0) {
					System.out.println("\n********************* Détail *********************");
					String computer = response.get(0);
					System.out.println(computer);
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas de machine avec l'identifiant " + id + ".");
				}
				response = null;
				break;
			case CREATE_COMPUTER:
				createNewComputer();
				response = controller.process(request, name, introduced, discontinued, manufacturer);
				if (response.size() > 0) {
					System.out.println("\n************ Requête créée avec succès ***********");
					String rowCreated = response.get(0);
					System.out.println(rowCreated);
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas de machine avec l'identifiant " + id + ".");
				}
				// TODO: afficher la machine créée
				break;
			case UPDATE_COMPUTER:
//				System.out.print("Donnez l'id de la machine que vous voulez afficher : ");
//				id = sc.nextLine();
//				//response = controller.process(request, id);
//				if (response.get(0).equals("-1")) {
//					//responseToPrint();
//				} else {
//					//updateComputer();
//					//response = controller.process(request, name, di, dd, manufacturer);
//					//responseToPrint();
//				}
				break;
			case DELETE_COMPUTER:
				System.out.print("Donnez l'id de la machine que vous voulez supprimer : ");
				id = sc.nextLine();
				response = controller.process(request, id);
				if (response.size() > 0) {
					System.out.println("\n********** Requête supprimé avec succès **********");
					String rowDeleted = response.get(0);
					System.out.println(rowDeleted);
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas de machine avec l'identifiant " + id + ".");
				}
				// TODO: afficher la machine supprimée
				break;
			case EXIT_PROGRAM:
				stop = true;
				break;
			case UNKNOWN:
				System.out.println("Argument " + request + " inconnu");
				System.out.println("Recommencez");
				break;
			}
		}
		sc.close();
		System.out.println("Session terminée.");
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
	
	/**
	 * Méthode qui demande les informations pour créer une nouvelle machine.
	 */
	private void createNewComputer() {
		System.out.print("Donnez le nom de la machine que vous voulez créer : ");
		name = sc.nextLine();
		while (name.equals("")) {
			System.out.print("Veuillez donner un nom à la machine que vous voulez créer : ");
			name = sc.nextLine();
		}
		System.out.print("Donnez la date de création de la machine : ");
		introduced = sc.nextLine();
		System.out.print("Donnez la date de fin de production de la machine : ");
		discontinued = sc.nextLine();
		System.out.print("Donnez l'id de l'entreprise qui produit cette machine : ");
		manufacturer = sc.nextLine();
	}
}
