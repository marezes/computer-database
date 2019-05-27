package com.excilys.cdb.mapper;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DTOComputer;

@Component
public class MapperDTO {
    public MapperDTO() {
    }
	
	public DTOComputer dataToDTOComputer(HttpServletRequest request) {
		DTOComputer dtoComputerCreated = null;
		String computerName = request.getParameter("computerName");
		Integer id = null;
		LocalDate introduced = null;
		LocalDate discontinued = null;
		Integer companyId = null;
		
		try {
			id = (request.getParameter("id") == null) ? null : Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			throw e;
		}

		try {
			introduced = (request.getParameter("introduced")).equals("") ? null : LocalDate.parse(request.getParameter("introduced"));
		} catch (Exception e) {
			throw e;
		}
		
		try {
			discontinued = (request.getParameter("discontinued")).equals("") ? null : LocalDate.parse(request.getParameter("discontinued"));
		} catch (Exception e) {
			throw e;
		}
		
		try {
			companyId = (request.getParameter("companyId")).equals("") ? null : Integer.parseInt(request.getParameter("companyId"));
		} catch (Exception e) {
			throw e;
		}
		
		try {
			dtoComputerCreated = (new DTOComputer.DTOComputerBuilder(computerName)
					.withId(id)
					.withIntroduced(introduced)
					.withDiscontinued(discontinued)
					.withCompanyId(companyId)
					.build());
		} catch (Exception e) {
			throw e;
		}
		
		return dtoComputerCreated;
	}
}
