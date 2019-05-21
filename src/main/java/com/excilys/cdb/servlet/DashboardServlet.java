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
		try {
			if (request.getParameter("page") != null) {
				pageNumberRequested = Integer.parseInt(request.getParameter("page"));
			} else {
				pageNumberRequested = 1;
			}
		} catch (Exception e) {
			System.err.println("Problème parsing PageNumberRequested");
		}

		Integer numberOfElementsToPrint = null;
		try {
			if (request.getParameter("numberElementPerPages") != null) {
				numberOfElementsToPrint = Integer.parseInt(request.getParameter("numberElementPerPages"));
			} else {
				numberOfElementsToPrint = dtoPage.getNumberOfElementsToPrint();
			}
		} catch(Exception e) {
			System.err.println("Problème parsing numberElementPerPages");
		}

		ModelPage modelPage = null;
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

		dtoPage = mapperPage.modelPageToDTOPage(modelPage);

		request.setAttribute("computerListObject", dtoPage.getDtoComputerList());
		request.setAttribute("numberOfElementsToPrint", dtoPage.getNumberOfElementsToPrint());
		request.setAttribute("totalNumberOfComputer", dtoPage.getNumberTotalOfComputer());
		request.setAttribute("pageNumber", dtoPage.getPageNumber());
		request.setAttribute("totalNumberOfPages", dtoPage.getNumberTotalPage());

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		requestDispatcher.forward(request, response);
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
