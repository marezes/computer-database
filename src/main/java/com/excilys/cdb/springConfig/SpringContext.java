package com.excilys.cdb.springConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.mapper.MapperDTO;
import com.excilys.cdb.mapper.MapperPage;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;

public class SpringContext {
	
	private static SpringContext INSTANCE = new SpringContext();
	
	AnnotationConfigApplicationContext ctx;
	
	public SpringContext() {
		ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringConfig.class);
        try {
        	ctx.refresh();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode qui renvoie l'objet singleton SpringContext.
	 * @return Un objet de type SpringContext
	 */
	public static SpringContext getInstance() {
		return INSTANCE;
	}

	public ServiceCompany getServiceCompany() {
		return ctx.getBean(ServiceCompany.class);
	}

	public ServiceComputer getServiceComputer() {
		return ctx.getBean(ServiceComputer.class);
	}

	public MapperCompany getMapperCompany() {
		return ctx.getBean(MapperCompany.class);
	}

	public MapperComputer getMapperComputer() {
		return ctx.getBean(MapperComputer.class);
	}

	public MapperDTO getMapperDTO() {
		return ctx.getBean(MapperDTO.class);
	}

	public MapperPage getMapperPage() {
		return ctx.getBean(MapperPage.class);
	}

	public Validator getValidator() {
		return ctx.getBean(Validator.class);
	}
}
