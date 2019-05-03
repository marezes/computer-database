package com.excilys.cdb.ui;

import com.excilys.cdb.controller.*;
import com.excilys.cdb.enumeration.MagicNumber;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
	private Controller controller;
	private ArrayList<String> response;	// La réponse reçu
	
	private String request;				// La demande de l'utilisateur
	private String id;					// un id
	private String name;				// Nom de l'ordinateur
	private String introduced;			// date introduced
	private String discontinued;		// date discontinued
	private String company;				// le fabricant
	
	private String pageNumber;			// Le numéro de la page à afficher
	private String numberOfElement;		// Le nombre d'éléments à afficher par page
	
	private boolean stop;				// Booléen pour l'arrêt de la boucle principale
	private Scanner sc;					// Le scanner pour récupérer ce que l'utilisateur écrit (à changer)
	
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
				
				if (response.size() == 1) {
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
				response = controller.process(request, name, introduced, discontinued, company);
				if (response.size() > 0) {
					System.out.println("\n************ Requête créée avec succès ***********");
					String rowCreated = response.get(0);
					System.out.println(rowCreated);
					System.out.println("**************************************************\n");
				} else {
					System.out.println("La machine " + name + " n'a pas pu être créée.");
				}
				break;
			case UPDATE_COMPUTER:
				System.out.print("Donnez l'id de la machine que vous voulez modifier : ");
				id = sc.nextLine();
				response = controller.process((MagicNumber.SHOW_COMPUTER_DETAILS).toString(), id);
				if (response.size() != 1) {
					System.out.println("L'identifiant " + id + " n'existe pas.");
					break;
				}

				elementToUpdate(response.remove(0));
				response = controller.process(request, id, name, introduced, discontinued, company);
				if (response.size() > 0) {
					System.out.println("\n********* Requête mise à jour avec succès ********");
					String rowUpdated = response.get(0);
					System.out.println(rowUpdated);
					System.out.println("**************************************************\n");
				} else {
					System.out.println("Pas de machine avec l'identifiant " + id + ".");
				}
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
		String time = "";
		System.out.print("Donnez le nom de la machine que vous voulez créer : ");
		name = sc.nextLine();
		while (name.equals("")) {
			System.out.print("Veuillez donner un nom à la machine que vous voulez créer : ");
			name = sc.nextLine();
		}
		System.out.print("Donnez la date de création de la machine : ");
		introduced = sc.nextLine();
		System.out.print("Donnez l'heure de création de la machine : ");
		time = sc.nextLine();
		if (!time.equals("")) {
			introduced += (" " + time);
		}
		System.out.print("Donnez la date de fin de production de la machine : ");
		discontinued = sc.nextLine();
		System.out.print("Donnez l'heure de fin de production de la machine : ");
		time = sc.nextLine();
		if (!time.equals("")) {
			discontinued += (" " + time);
		}
		// TODO: tester si les jours et heures sont correct
		// TODO: tester si discontinued > introduced
		System.out.print("Donnez l'id de l'entreprise qui produit cette machine : ");
		company = sc.nextLine();
	}
	
	/**
	 * Méthode qui demande les informations pour mettre à jour une machine.
	 */
	private void elementToUpdate(String element) {
		String time = "";
		System.out.println("\n*************** Requête a changer ****************");
		System.out.println(element);
		System.out.println("**************************************************");
		System.out.println("\nLorsque vous désirez pas changer l'argument, taper sur [entrer] sans rien mettre");
		System.out.print("Quel nom voulez-vous donner : ");
		name = sc.nextLine();
		System.out.print("Quel est la date de création de la machine (YYYY-MM-DD) : ");
		introduced = sc.nextLine();
		System.out.print("Quel est l'heure de création de la machine (HH:MM:SS) : ");
		time = sc.nextLine();
		if (!time.equals("")) {
			introduced += (" " + time);
		}
		System.out.print("Quel est la date de fin de production de la machine (YYYY-MM-DD) : ");
		discontinued = sc.nextLine();
		System.out.print("Quel est l'heure de fin de production de la machine (HH:MM:SS) : ");
		time = sc.nextLine();
		if (!time.equals("")) {
			discontinued += (" " + time);
		}
		// TODO: tester si les jours et heures sont correct
		// TODO: tester si discontinued > introduced
		System.out.print("Quel est l'id de l'entreprise qui produit cette machine : ");
		company = sc.nextLine();
	}
}
