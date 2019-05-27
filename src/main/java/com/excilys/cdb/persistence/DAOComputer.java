package com.excilys.cdb.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dbConfig.Hikari;
import com.excilys.cdb.exception.JDBCClassNotFoundException;
import com.excilys.cdb.exception.PropertiesFileLoadFailedException;
import com.excilys.cdb.mapper.MapperSQLComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.model.ModelPage;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DAOComputer {
	private final HikariDataSource dataSource;
	private MapperSQLComputer mapperSQLComputer;

	private HashMap<String, String> antiInjector;

	private String SELECT_COMPUTER_LIST_LIMIT = 
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id "
			+ "ORDER BY ";
	private String SELECT_COMPUTER_SEARCH_LIST_LIMIT = 
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE INSTR(computer.name, :searchedWord) > 0 OR INSTR(company.name, :searchedWord) > 0 "
			+ "ORDER BY ";
	private String SELECT_COUNT_COMPUTER = 
			"SELECT COUNT(id) "
			+ "FROM computer";
	private String SELECT_COUNT_COMPUTER_SEARCHED = 
			"SELECT COUNT(computer.id) "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE INSTR(computer.name, :searchedWord) > 0 OR INSTR(company.name, :searchedWord) > 0;";
	private String SELECT_BY_ID = 
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id "
			+ "WHERE computer.id = :computerId;";
	private String INSERT_COMPUTER = 
			"INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (:name, :introduced, :discontinued, :companyId);";
	private String UPDATE_COMPUTER = 
			"UPDATE computer "
			+ "SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :companyId "
			+ "WHERE id = :id;";
	private String DELETE_COMPUTER = 
			"DELETE FROM computer "
			+ "WHERE id = :id;";
	private String DELETE_COMPUTERS_WITH_TRANSACTION = 
			"DELETE FROM computer "
			+ "WHERE company_id = :companyId;";
	
	public DAOComputer(Hikari hikari, MapperSQLComputer mapperSQLComputer) throws JDBCClassNotFoundException, PropertiesFileLoadFailedException, ClassNotFoundException {
		dataSource = hikari.getDataSource();
		this.mapperSQLComputer = mapperSQLComputer;
		
		this.antiInjector = new HashMap<String, String>();
		this.antiInjector.put("computerName", "computer.name");
		this.antiInjector.put("introduced", "introduced");
		this.antiInjector.put("discontinued", "discontinued");
		this.antiInjector.put("companyName", "company.name");
	}
	
	public ArrayList<ModelComputer> requestListPage(ModelPage modelPage) {
		ArrayList<ModelComputer> modelComputerList;
		
		String SELECT_COMPUTER_LIST_LIMIT_ORDER_BY = SELECT_COMPUTER_LIST_LIMIT 
				+ (getOrderBy(modelPage.getOrderBy()) 
						+ " IS NULL, " 
						+ getOrderBy(modelPage.getOrderBy()) + " " 
						+ (modelPage.isAsc() ? "ASC" : "DESC") 
						+ " LIMIT :pageNumber, :numberOfElementsToPrint;");
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("pageNumber", ((modelPage.getPageNumber() - 1) * modelPage.getNumberOfElementsToPrint()));
        paramSetter.addValue("numberOfElementsToPrint", modelPage.getNumberOfElementsToPrint());
        
        modelComputerList = (ArrayList<ModelComputer>) jdbcTemplate.query(SELECT_COMPUTER_LIST_LIMIT_ORDER_BY, paramSetter, mapperSQLComputer);
        
		return modelComputerList;
	}
	
	private String getOrderBy(String columnName) {
		return ((antiInjector.get(columnName) == null) ? "computer.id" : antiInjector.get(columnName));
	}
	
	public int requestTotalNumberOfComputers() {
		int totalNumberOfComputers = 0;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        totalNumberOfComputers = jdbcTemplate.queryForObject(SELECT_COUNT_COMPUTER, Integer.class);
		
		return totalNumberOfComputers;
	}
	
	public ArrayList<ModelComputer> requestListPageSearched(ModelPage modelPage) {
		ArrayList<ModelComputer> modelComputerList = new ArrayList<ModelComputer>();
		
		String SELECT_COMPUTER_SEARCH_LIST_LIMIT_ORDER_BY = SELECT_COMPUTER_SEARCH_LIST_LIMIT 
				+ (getOrderBy(modelPage.getOrderBy()) 
						+ " IS NULL, " 
						+ getOrderBy(modelPage.getOrderBy()) + " " 
						+ (modelPage.isAsc() ? "ASC" : "DESC") 
						+ " LIMIT :pageNumber, :numberOfElementsToPrint;");
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("pageNumber", ((modelPage.getPageNumber() - 1) * modelPage.getNumberOfElementsToPrint()));
        paramSetter.addValue("numberOfElementsToPrint", modelPage.getNumberOfElementsToPrint());
        paramSetter.addValue("searchedWord", modelPage.getWordSearched());
        
        modelComputerList = (ArrayList<ModelComputer>) jdbcTemplate.query(SELECT_COMPUTER_SEARCH_LIST_LIMIT_ORDER_BY, paramSetter, mapperSQLComputer);

		return modelComputerList;
	}
	
	public int requestTotalNumberOfComputersFound(String nameSearched) {
		int totalNumberOfComputersFound = 0;

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("searchedWord", nameSearched);
        
        totalNumberOfComputersFound = jdbcTemplate.queryForObject(SELECT_COUNT_COMPUTER_SEARCHED, paramSetter, Integer.class);
        
		return totalNumberOfComputersFound;
	}

	/**
	 * Méthode qui récupère le détail d'une machine.
	 * 
	 * @param id - Un entier qui représente l'id
	 * @return Un objet de type ModelComputer
	 */
	public ModelComputer requestById(int id) {
		ModelComputer modelComputer = null;

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("computerId", id);

        modelComputer = jdbcTemplate.queryForObject(SELECT_BY_ID, paramSetter, mapperSQLComputer);

		return modelComputer;
	}

	/**
	 * Méthode qui créer une nouvelle machine dans la base de donnée.
	 * 
	 * @param modelComputer - Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public boolean requestCreate(ModelComputer modelComputer) {
		int statut = -1;
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("name", modelComputer.getName());
        paramSetter.addValue("introduced", modelComputer.getIntroduced());
        paramSetter.addValue("discontinued", modelComputer.getDiscontinued());
        paramSetter.addValue("companyId", modelComputer.getModelCompany().getId());
        
        statut = jdbcTemplate.update(INSERT_COMPUTER, paramSetter);
		
		return (statut == 1);
	}

	/**
	 * Méthode qui met à jour les informations d'une machine dans la base de donnée.
	 * 
	 * @param model Un objet de type ModelComputer
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public boolean requestUpdate(ModelComputer modelComputer) {
		int statut = -1;
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("name", modelComputer.getName());
        paramSetter.addValue("introduced", modelComputer.getIntroduced());
        paramSetter.addValue("discontinued", modelComputer.getDiscontinued());
        paramSetter.addValue("companyId", modelComputer.getModelCompany().getId());
        paramSetter.addValue("id", modelComputer.getId());
        
        statut = jdbcTemplate.update(UPDATE_COMPUTER, paramSetter);
		
		return (statut == 1);
	}

	/**
	 * Méthode qui supprime une machine de la base de donnée selon un identifiant.
	 * 
	 * @param id Un entier qui représente l'id
	 * @return Un booléen true si la requête a réussi, et false sinon
	 */
	public boolean requestDelete(int id) {
		int statut = -1;
		
		ModelComputer modelComputerDeleted = requestById(id);

		if (modelComputerDeleted == null) {
			return false;
		}
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("id", id);
        
        statut = jdbcTemplate.update(DELETE_COMPUTER, paramSetter);

		return (statut == 1);
	}
	
	@Transactional
	public boolean requestDeleteWithTransaction(int companyId) {
		int statut = -1;
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		MapSqlParameterSource paramSetter = new MapSqlParameterSource();
        paramSetter.addValue("companyId", companyId);
        
        statut = jdbcTemplate.update(DELETE_COMPUTERS_WITH_TRANSACTION, paramSetter);

		return (statut == 1);
	}
}
