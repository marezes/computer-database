package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.excilys.cdb.dto.DTOComputer;

public class Validator {
	
	private static Validator INSTANCE = null;

	private Validator() {
	}
	
	/**
	 * Méthode qui renvoie l'objet singleton Validator.
	 * @return Un objet de type Validator
	 * @throws Exception 
	 */
	public static Validator getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new Validator();
		}
		return INSTANCE;
	}
	
	public boolean dtoComputerValidation(DTOComputer dtoComputerToValidate) {
		isValidId(dtoComputerToValidate.getId());
		isValidName(dtoComputerToValidate.getName());
		isValidDate(dtoComputerToValidate.getIntroduced());
		isValidDate(dtoComputerToValidate.getDiscontinued());
		isDiscontinuedSuperior(dtoComputerToValidate.getIntroduced(), dtoComputerToValidate.getDiscontinued());
		isValidId(dtoComputerToValidate.getCompanyId());
		return true;
	}
	
	private void isValidId(Integer id) {
		if ((id != null) && ((id <= 0) || (id >= Integer.MAX_VALUE))) {
			throw new RuntimeException("Invalid Id");
		}
	}
	
	private void isValidDate(LocalDate date) {
		if (date != null) {
			Pattern datePattern = Pattern.compile(
				      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
				    	      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
				    	      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
				    	      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	
			if (!datePattern.matcher(date.toString()).matches()) {
				throw new RuntimeException("Invalid date");
			}
		}
	}
	
	private void isDiscontinuedSuperior(LocalDate dateIntroduced, LocalDate dateDiscontinued) {
		if ((dateIntroduced != null) && (dateDiscontinued != null)) {
			if (dateIntroduced.isAfter(dateDiscontinued)) {
				throw new RuntimeException("date introduced superior or equals to discontinued");
			}
		}
	}
	
	private void isValidName(String name) {
		if ((name == null) || name.contentEquals("") ) {
			throw new RuntimeException("Invalid Id");
		}
	}
}
