package com.excilys.cdb.ui;

import java.util.Scanner;

public class UI {
	String requete;
	boolean stop;
	Scanner sc;
	
	public UI() {
		stop = false;
		sc = new Scanner(System.in);
	}
	
	public void start() {
		System.out.println("Bonjour et bienvenu sur votre espace utilisateur");
		
		while(!stop) {
			System.out.print("Que voulez-vous faire : ");
			requete = sc.next();
			switch(requete) {
			case "0": break; // list computer
			case "1": break; // list company
			case "2": break; // show computer details
			case "3": break; // create new computer
			case "4": break; // update computer
			case "5": break; // delete computer
			case "6": break; // quit program
			default:
				System.out.println("Argument " + requete + " inconnu");
				System.out.println("Recommencez");
				break;
			}
		}
		sc.close();
	}
	
	public void menu() {
		System.out.println("*********************| MENU |*********************");
		System.out.println("Voici un récapitulatif des options:");
		System.out.println("	0 : Lister les machines");
		System.out.println("	1 : Lister les entreprises");
		System.out.println("	2 : Afficher le détail d'une machine");
		System.out.println("	3 : Créer une nouvelle machine");
		System.out.println("	4 : Mettre à jour une machine");
		System.out.println("	5 : Supprimer une machine");
		System.out.println("	6 : Quitter le programme");
		System.out.println("**************************************************");
	}
}
