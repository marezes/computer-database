package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.mapper.MapperPage;
import com.excilys.cdb.model.ModelPage;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ServiceComputer serviceComputer;
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
		ModelPage modelPage = null;
		
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
		
		
		if (request.getParameter("search") != null) {
			try {
				modelPage = serviceComputer.requestListPageSearched(pageNumberRequested, numberOfElementsToPrint, request.getParameter("search"));
			} catch (Exception e) {
				System.err.println("Failed to get List of computers searched");
			}
	
			try {
				modelPage.setNumberTotalOfComputer(serviceComputer.requestTotalNumberOfComputersFound(request.getParameter("search")));
			} catch (Exception e) {
				System.err.println("Failed to get number total of computer found with search");
				e.printStackTrace();
			}

			modelPage.setNumberTotalPage((modelPage.getNumberTotalOfComputer() / modelPage.getNumberOfElementsToPrint())
					+ (((modelPage.getNumberTotalOfComputer() % modelPage.getNumberOfElementsToPrint()) != 0) ? 1 : 0));

			request.setAttribute("search", request.getParameter("search"));
		} else {
			try {
				modelPage = serviceComputer.requestListPage(pageNumberRequested, numberOfElementsToPrint);
			} catch (Exception e) {
				System.err.println("Failed to get List of computers");
			}
	
			try {
				modelPage.setNumberTotalOfComputer(serviceComputer.requestTotalNumberOfComputers());
			} catch (Exception e) {
				System.err.println("Failed to get number total of computer");
			}

			modelPage.setNumberTotalPage((modelPage.getNumberTotalOfComputer() / modelPage.getNumberOfElementsToPrint())
					+ (((modelPage.getNumberTotalOfComputer() % modelPage.getNumberOfElementsToPrint()) != 0) ? 1 : 0));
		}

		dtoPage = mapperPage.modelPageToDTOPage(modelPage);
		
		
		request.setAttribute("computerListObject", dtoPage.getDtoComputerList());
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
