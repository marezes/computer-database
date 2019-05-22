package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.mapper.MapperPage;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ServiceComputer serviceComputer;
	private MapperComputer mapperComputer;
	private MapperPage mapperPage;
	private DTOPage dtoPage;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		try {
			serviceComputer = ServiceComputer.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get ServiceComputer without exception");
			e.printStackTrace();
		}
		try {
			mapperComputer = MapperComputer.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperComputer without exception");
		}
		try {
			mapperPage = MapperPage.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperPage without exception");
		}
		dtoPage = new DTOPage.DTOPageBuilder()
				.withPageNumber(1)
				.withNumberOfElementsToPrint(10)
				.build();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer pageNumberRequested = null;
		Integer numberOfElementsToPrint = null;
		ArrayList<DTOComputer> dtoComputerList = null;
		
		try {
			pageNumberRequested = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			System.err.println("Problème parsing PageNumberRequested");
		}
		pageNumberRequested = (pageNumberRequested <= 0) ? 1 : 
			(pageNumberRequested >= Integer.MAX_VALUE) ? 
					((dtoPage.getNumberTotalPage() != null) ? 1 : dtoPage.getNumberTotalPage())
					: pageNumberRequested;
		try {
			numberOfElementsToPrint = (request.getParameter("numberElementPerPages") != null) 
					? Integer.parseInt(request.getParameter("numberElementPerPages")) 
							: dtoPage.getNumberOfElementsToPrint();
		} catch(Exception e) {
			System.err.println("Problème parsing numberElementPerPages");
		}
		
		dtoPage.setPageNumber(pageNumberRequested);
		dtoPage.setNumberOfElementsToPrint(numberOfElementsToPrint);
		dtoPage.setWordSearched(request.getParameter("search"));
		

		if (request.getParameter("orderby") != null) {
			switch (request.getParameter("orderby")) {
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
		
		if (request.getParameter("search") != null) {
			try {
				dtoComputerList = mapperComputer.modelComputerListToDTOComputerList(serviceComputer.requestListPageSearched(mapperPage.dtoPageToModelPage(dtoPage)));
			} catch (Exception e) {
				System.err.println("Failed to get List of computers searched");
			}
	
			try {
				dtoPage.setNumberTotalOfComputer(serviceComputer.requestTotalNumberOfComputersFound(request.getParameter("search")));
			} catch (Exception e) {
				System.err.println("Failed to get number total of computer found with search");
				e.printStackTrace();
			}

			dtoPage.setNumberTotalPage((dtoPage.getNumberTotalOfComputer() / dtoPage.getNumberOfElementsToPrint())
					+ (((dtoPage.getNumberTotalOfComputer() % dtoPage.getNumberOfElementsToPrint()) != 0) ? 1 : 0));

			request.setAttribute("search", request.getParameter("search"));
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
		
		
		request.setAttribute("computerListObject", dtoComputerList);
		request.setAttribute("numberOfElementsToPrint", dtoPage.getNumberOfElementsToPrint());
		request.setAttribute("totalNumberOfComputer", dtoPage.getNumberTotalOfComputer());
		request.setAttribute("pageNumber", dtoPage.getPageNumber());
		request.setAttribute("totalNumberOfPages", dtoPage.getNumberTotalPage());

		try{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			System.err.println("Dispatcher failed");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Arrays.asList(request.getParameter("selection").split(",")).stream()
			.map(id -> Integer.parseInt(id))
			.forEach(id -> serviceComputer.requestDelete(id));
		} catch (NumberFormatException e) {
			System.err.println("Cast échoué");
		}

		response.sendRedirect("dashboard");
	}

}
