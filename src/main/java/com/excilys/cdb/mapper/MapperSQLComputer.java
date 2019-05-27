package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

@Component
public class MapperSQLComputer implements RowMapper<ModelComputer> {
	
	public MapperSQLComputer() {
	}

	@Override
	public ModelComputer mapRow(ResultSet rs, int rowNum) throws SQLException {
		ModelComputer modelComputer = null;
		
		int id = rs.getInt("id");
		String name = rs.getString("name");
		LocalDate introduced = (rs.getTimestamp("introduced") == null 
				? null : ((rs.getTimestamp("introduced")).toLocalDateTime()).toLocalDate());
		LocalDate discontinued = (rs.getTimestamp("discontinued") == null 
				? null : ((rs.getTimestamp("discontinued")).toLocalDateTime()).toLocalDate());
		Integer companyId = rs.getInt("company_id") == 0 ? null : rs.getInt("company_id");
		String companyName = rs.getString("company.name");
		
		modelComputer = (new ModelComputer.ModelComputerBuilder(name)
				.withId(id)
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withModelCompany(
						new ModelCompany.ModelCompanyBuilder()
							.withId(companyId)
							.withName(companyName)
							.build())
				.build());
		
		return modelComputer;
	}

}
