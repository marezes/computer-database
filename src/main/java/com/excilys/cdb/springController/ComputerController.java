package com.excilys.cdb.springController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.mapper.MapperDTO;
import com.excilys.cdb.mapper.MapperPage;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;

@Controller
@SessionAttributes("dtoPage")
public class ComputerController {

	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	private MapperCompany mapperCompany;
	private MapperComputer mapperComputer;
	private MapperDTO mapperDTO;
	private MapperPage mapperPage;
	private Validator validator;

	public ComputerController(ServiceCompany serviceCompany, ServiceComputer serviceComputer, 
			MapperCompany mapperCompany, MapperComputer mapperComputer, 
			MapperDTO mapperDTO, MapperPage mapperPage, Validator validator) {
		
		this.serviceCompany = serviceCompany;
		this.serviceComputer = serviceComputer;
		this.mapperCompany = mapperCompany;
		this.mapperComputer = mapperComputer;
		this.mapperDTO = mapperDTO;
		this.mapperPage = mapperPage;
		this.validator = validator;
	}
	
	@ModelAttribute("dtoPage")
	public DTOPage createDtoPage() {
		return (new DTOPage.DTOPageBuilder()
				.withPageNumber(1)
				.withNumberOfElementsToPrint(10)
				.build());
	}
	
	@GetMapping(value = {"/", "/dashboard"})
	protected String dashboardGet(
			Model model, 
			@RequestParam(value = "page", required=false) String pageNumber, 
			@RequestParam(value = "numberElementPerPages", required=false) String numberElementPerPages, 
			@RequestParam(value = "search", required=false) String search, 
			@RequestParam(value = "orderby", required=false) String orderby, 
			@ModelAttribute("dtoPage") DTOPage dtoPage) {
		
		Integer pageNumberRequested = null;
		Integer numberOfElementsToPrint = null;
		ArrayList<DTOComputer> dtoComputerList = null;
		
		try {
			pageNumberRequested = (pageNumber == null) ? 1 : Integer.parseInt(pageNumber);
		} catch (Exception e) {
			System.err.println("Problème parsing PageNumberRequested");
		}
		pageNumberRequested = (pageNumberRequested <= 0) ? 1 : 
			(pageNumberRequested >= Integer.MAX_VALUE) ? 
					((dtoPage.getNumberTotalPage() != null) ? 1 : dtoPage.getNumberTotalPage())
					: pageNumberRequested;
		try {
			numberOfElementsToPrint = (numberElementPerPages != null) 
					? Integer.parseInt(numberElementPerPages) 
							: dtoPage.getNumberOfElementsToPrint();
		} catch(Exception e) {
			System.err.println("Problème parsing numberElementPerPages");
		}
		
		dtoPage.setPageNumber(pageNumberRequested);
		dtoPage.setNumberOfElementsToPrint(numberOfElementsToPrint);
		dtoPage.setWordSearched(search);
		

		if (orderby != null) {
			switch (orderby) {
			case "computerNameASC":
				dtoPage.setOrderBy("computerName");
				dtoPage.setisAsc(true);
				break;
			case "computerNameDESC":
				dtoPage.setOrderBy("computerName");
				dtoPage.setisAsc(false);
				break;
			case "introducedASC":
				dtoPage.setOrderBy("introduced");
				dtoPage.setisAsc(true);
				break;
			case "introducedDESC":
				dtoPage.setOrderBy("introduced");
				dtoPage.setisAsc(false);
				break;
			case "discontinuedASC":
				dtoPage.setOrderBy("discontinued");
				dtoPage.setisAsc(true);
				break;
			case "discontinuedDESC":
				dtoPage.setOrderBy("discontinued");
				dtoPage.setisAsc(false);
				break;
			case "companyNameASC":
				dtoPage.setOrderBy("companyName");
				dtoPage.setisAsc(true);
				break;
			case "companyNameDESC":
				dtoPage.setOrderBy("companyName");
				dtoPage.setisAsc(false);
				break;

			default:
				dtoPage.setOrderBy(null);
				dtoPage.setisAsc(true);
				break;
			}
		} else {
			dtoPage.setOrderBy(null);
			dtoPage.setisAsc(true);
		}
		
		if (search != null) {
			try {
				dtoComputerList = mapperComputer.modelComputerListToDTOComputerList(serviceComputer.requestListPageSearched(mapperPage.dtoPageToModelPage(dtoPage)));
			} catch (Exception e) {
				System.err.println("Failed to get List of computers searched");
			}
	
			try {
				dtoPage.setNumberTotalOfComputer(serviceComputer.requestTotalNumberOfComputersFound(search));
			} catch (Exception e) {
				System.err.println("Failed to get number total of computer found with search");
				e.printStackTrace();
			}

			dtoPage.setNumberTotalPage((dtoPage.getNumberTotalOfComputer() / dtoPage.getNumberOfElementsToPrint())
					+ (((dtoPage.getNumberTotalOfComputer() % dtoPage.getNumberOfElementsToPrint()) != 0) ? 1 : 0));

			model.addAttribute("search", search);
		} else {
			try {
				dtoComputerList = mapperComputer.modelComputerListToDTOComputerList(serviceComputer.requestListPage(mapperPage.dtoPageToModelPage(dtoPage)));
			} catch (Exception e) {
				System.err.println("Failed to get List of computers");
			}
	
			try {
				dtoPage.setNumberTotalOfComputer(serviceComputer.requestTotalNumberOfComputers());
			} catch (Exception e) {
				System.err.println("Failed to get number total of computer");
			}

			dtoPage.setNumberTotalPage((dtoPage.getNumberTotalOfComputer() / dtoPage.getNumberOfElementsToPrint())
					+ (((dtoPage.getNumberTotalOfComputer() % dtoPage.getNumberOfElementsToPrint()) != 0) ? 1 : 0));
			
		}
		
		
		model.addAttribute("computerListObject", dtoComputerList);
		model.addAttribute("numberOfElementsToPrint", dtoPage.getNumberOfElementsToPrint());
		model.addAttribute("totalNumberOfComputer", dtoPage.getNumberTotalOfComputer());
		model.addAttribute("pageNumber", dtoPage.getPageNumber());
		model.addAttribute("totalNumberOfPages", dtoPage.getNumberTotalPage());

		return "dashboard";
	}
	
	@PostMapping(value = {"/", "/dashboard"})
	protected RedirectView dashboardPost(@RequestParam(value = "selection", required=true) String selection) {
		try {
			Arrays.asList(selection.split(",")).stream()
			.map(id -> Integer.parseInt(id))
			.forEach(id -> serviceComputer.requestDelete(id));
		} catch (NumberFormatException e) {
			System.err.println("Cast échoué");
		}

		return new RedirectView("dashboard");
		
	}
	
	@GetMapping(value = "/addComputer")
	protected String addComputerGet(Model model) {
		
		ArrayList<DTOCompany> dtoCompanyList = null;
		try {
			dtoCompanyList = mapperCompany.modelCompanyListToDTOCompanyList(serviceCompany.requestList());
		} catch (Exception e) {
			System.err.println("Failed to get List of company");
		}
		
		model.addAttribute("companyListObject", dtoCompanyList);
		
		return "addComputer";
	}

	@PostMapping(value = "/addComputer")
	protected RedirectView addComputerPost(@RequestParam Map<String,String> allParams) {
		DTOComputer dtoComputerToCreate = mapperDTO.dataToDTOComputer(allParams);
		
		if (validator.dtoComputerValidation(dtoComputerToCreate)) {
			try {
				serviceComputer.requestCreate(mapperComputer.dtoComputerToModelComputer(dtoComputerToCreate));
			} catch (Exception e) {
				System.err.println("Update or mapping to model failed");
			}
		}
		
		return new RedirectView("dashboard");
	}
	
	@GetMapping(value = "/editComputer")
	protected String editComputerGet(Model model, @RequestParam(value = "id", required=true) String idComputer) {
		int id = -1;
		
		try {
			id = Integer.parseInt(idComputer);
		} catch (Exception e) {
			System.err.println("Error cast id");
		}

		ModelComputer modelComputerDetails = null;
		DTOComputer dtoComputerDetails = null;
		try {
			modelComputerDetails = serviceComputer.requestComputerDetails(id);
			dtoComputerDetails = mapperComputer.modelComputerToDTOComputer(modelComputerDetails);
		} catch (Exception e) {
			System.err.println("Failed to get detail of computers");
		}
		
		ArrayList<DTOCompany> dtoCompanyList = null;
		try {
			dtoCompanyList = mapperCompany.modelCompanyListToDTOCompanyList(serviceCompany.requestList());
		} catch (Exception e) {
			System.err.println("Failed to get List of company");
		}
		
		model.addAttribute("id", id);
		model.addAttribute("computerDetails", dtoComputerDetails);
		model.addAttribute("companyListObject", dtoCompanyList);
		
		return "editComputer";
	}

	@PostMapping(value = "/editComputer")
	protected RedirectView editComputerPost(@RequestParam Map<String,String> allParams) {
		DTOComputer dtoComputerToEdit = mapperDTO.dataToDTOComputer(allParams);
		if (validator.dtoComputerValidation(dtoComputerToEdit)) {
			try {
				serviceComputer.requestUpdate(mapperComputer.dtoComputerToModelComputer(dtoComputerToEdit));
			} catch (Exception e) {
				System.err.println("Update or mapping to model failed");
			}
		}
		return new RedirectView("dashboard");
	}
	
	@GetMapping(value = "/closeSession")
	public RedirectView closeSession(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return new RedirectView("dashboard");
	}
}
