package com.excilys.cdb.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dbConfig.Hikari;
import com.excilys.cdb.mapper.MapperSQLCompany;
import com.excilys.cdb.model.ModelCompany;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DAOCompany {
	
	private final HikariDataSource dataSource;
	private MapperSQLCompany mapperSQLCompany;
	
	private String SELECT = "SELECT id, name FROM company;";
	private String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = :companyId;";
	private String DELETE_COMPANY = "DELETE FROM company WHERE id = :companyId;";

	public DAOCompany(Hikari hikari, MapperSQLCompany mapperSQLCompany) throws Exception {
		dataSource = hikari.getDataSource();
		this.mapperSQLCompany = mapperSQLCompany;
	}

	/**
	 * Méthode qui récupère la liste des entreprises dans la base de donnée et la retourne.
	 * @return Une ArrayList de ModelCompany
	 * @throws SQLException 
	 */
	public ArrayList<ModelCompany> requestList() throws SQLException {
		ArrayList<ModelCompany> modelCompanyList;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		modelCompanyList = (ArrayList<ModelCompany>) jdbcTemplate.query(SELECT, mapperSQLCompany);

		return modelCompanyList;
	}
	
	public boolean requestDelete(int companyId) throws RuntimeException, Exception {
		int statut = -1;

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("companyId", companyId);
        
        statut = jdbcTemplate.update(DELETE_COMPANY, paramSetter);

		return (statut == 1) ? true : false;
	}
	
	public ModelCompany requestById(int companyId) throws Exception {
		ModelCompany modelCompany = null;

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("companyId", companyId);

        modelCompany = jdbcTemplate.queryForObject(SELECT_BY_ID, paramSetter, mapperSQLCompany);

		return modelCompany;
	}
}
