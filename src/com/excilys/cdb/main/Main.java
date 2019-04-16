package com.excilys.cdb.main;

import com.excilys.cdb.ui.*;
import com.excilys.cdb.controller.*;

public class Main {
	public static void main(String[] args) {
		UI ui = new UI(new Controller());
		ui.start();
	}
}
