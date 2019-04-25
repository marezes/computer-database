package com.excilys.cdb.main;

import com.excilys.cdb.persistence.DAOComputer;
import com.excilys.cdb.ui.*;

public class Main {
	public static void main(String[] args) {
		UI ui = new UI();
		ui.init();
		//ui.start();
		DAOComputer test = DAOComputer.getInstance();
		test.requestList();
	}
}
