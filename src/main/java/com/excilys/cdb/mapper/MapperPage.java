package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.model.ModelPage;

@Component
public class MapperPage {
    public MapperPage() {
    }
	
	public DTOPage modelPageToDTOPage(ModelPage modelPage) {
		Integer pageNumber = modelPage.getPageNumber();
		
		Integer numberTotalPage = modelPage.getNumberTotalPage();
		
		Integer numberOfElemetsToPrint = modelPage.getNumberOfElementsToPrint();
		
		Integer numberTotalOfComputer = modelPage.getNumberTotalOfComputer();
		
		String wordSearched = modelPage.getWordSearched();
		
		String orderBy = modelPage.getOrderBy();
		
		boolean isAsc = modelPage.isAsc();
		
		return (new DTOPage.DTOPageBuilder()
				.withPageNumber(pageNumber)
				.withNumberTotalPage(numberTotalPage)
				.withNumberOfElementsToPrint(numberOfElemetsToPrint)
				.withNumberTotalOfComputer(numberTotalOfComputer)
				.withWordSearched(wordSearched)
				.withOrderBy(orderBy)
				.withisAsc(isAsc)
				.build());
	}
	
    public ModelPage dtoPageToModelPage(DTOPage dtoPage) {
		Integer pageNumber = dtoPage.getPageNumber();
		
		Integer numberTotalPage = dtoPage.getNumberTotalPage();
		
		Integer numberOfElemetsToPrint = dtoPage.getNumberOfElementsToPrint();
		
		Integer numberTotalOfComputer = dtoPage.getNumberTotalOfComputer();
		
		String wordSearched = dtoPage.getWordSearched();
		
		String orderBy = dtoPage.getOrderBy();
		
		boolean isAsc = dtoPage.isAsc();
		
		return (new ModelPage.ModelPageBuilder()
				.withPageNumber(pageNumber)
				.withNumberTotalPage(numberTotalPage)
				.withNumberOfElementsToPrint(numberOfElemetsToPrint)
				.withNumberTotalOfComputer(numberTotalOfComputer)
				.withWordSearched(wordSearched)
				.withOrderBy(orderBy)
				.withisAsc(isAsc)
				.build());
    }
}
