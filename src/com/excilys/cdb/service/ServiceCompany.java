package com.excilys.cdb.service;

import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.ModelCompany;

public class ServiceCompany {
	MapperCompany mapperCompany;
	ModelCompany modelCompany;
	
	public ServiceCompany() {
		this.mapperCompany = new MapperCompany();
		modelCompany = null;
	}
}
