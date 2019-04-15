package com.excilys.cdb.ui;

import java.util.Scanner;

public class UI {
	
	public UI() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bonjour et bienvenu sur votre espace utilisateur");
		
		while(true) {
			System.out.print("Que voulez-vous faire : ");
			switch(sc.next()) {
			case "0": break;
			case "1": break;
			case "2": break;
			case "3": break;
			case "4": break;
			case "5": break; //quitter
			default: break;
			}
		}
	}
}
