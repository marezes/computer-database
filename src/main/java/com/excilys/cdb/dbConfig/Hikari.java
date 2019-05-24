package com.excilys.cdb.dbConfig;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikari {
	
	private static Hikari INSTANCE;
	
	private HikariDataSource dataSource;
	
	private Hikari() throws ClassNotFoundException {
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("hikariConfig");
		} catch (MissingResourceException ex) {
			bundle = ResourceBundle.getBundle("dbconfig_travis");
		}
		
		String driver = bundle.getString("driverClassName");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException cause) {
			throw cause;
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(driver);
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("user"));
		config.setPassword(bundle.getString("password"));
		
		this.dataSource = new HikariDataSource(config);
	}
	
	public static Hikari getInstance() throws ClassNotFoundException {
		if (INSTANCE == null) {
			INSTANCE = new Hikari();
		}
		return INSTANCE;
	}
	
	public HikariDataSource getDataSource() {
		return this.dataSource;
	}
}
