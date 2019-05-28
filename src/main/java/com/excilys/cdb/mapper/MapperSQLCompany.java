package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.ModelCompany;

@Component
public class MapperSQLCompany implements RowMapper<ModelCompany>{
	
	public MapperSQLCompany() {
		
	}

	@Override
	public ModelCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		
		return (new ModelCompany.ModelCompanyBuilder()
				.withId(id)
				.withName(name)
				.build());
	}
}
