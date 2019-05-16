package com.excilys.cdb.mapper;

import java.util.ArrayList;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.model.ModelPage;

public class MapperPage {
	private static MapperPage INSTANCE = null;
	
    private MapperPage() {
    }
	
	/**
	 * Méthode qui renvoie l'objet singleton MapperPage.
	 * @return Un objet de type MapperPage
	 */
	public static MapperPage getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MapperPage();
		}
		return INSTANCE;
	}
	
	public DTOPage modelPageToDTOPage(ModelPage modelPage) {
		Integer pageNumber = modelPage.getPageNumber();
		
		Integer numberTotalPage = modelPage.getNumberTotalPage();
		
		Integer numberOfElemetsToPrint = modelPage.getNumberOfElementsToPrint();
		
		Integer numberTotalOfComputer = modelPage.getNumberTotalOfComputer();
		
		ArrayList<DTOComputer> dtoComputerList = null;
		
		try {
			dtoComputerList = MapperComputer.getInstance().modelComputerListToDTOComputerList(modelPage.getModelComputerList());
		} catch (Exception e) {
			throw e;
		}
		
		return (new DTOPage.DTOPageBuilder()
				.withPageNumber(pageNumber)
				.withNumberTotalPage(numberTotalPage)
				.withNumberOfElementsToPrint(numberOfElemetsToPrint)
				.withNumberTotalOfComputer(numberTotalOfComputer)
				.withDtoComputerList(dtoComputerList)
				.build());
	}
	
    public ModelPage dtoPageToModelPage(DTOPage dtoPage) {
    	
		Integer pageNumber = dtoPage.getPageNumber();
		
		Integer numberOfElemetsToPrint = dtoPage.getNumberOfElementsToPrint();
		
		return (new ModelPage.ModelPageBuilder()
				.withPageNumber(pageNumber)
				.withNumberOfElementsToPrint(numberOfElemetsToPrint)
				.build());
    }
}
