package com.excilys.cdb.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.DTOPage;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceComputer serviceComputer;
	private MapperComputer mapperComputer;
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
		}
        try {
			mapperComputer = MapperComputer.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperComputer without exception");
		}
        dtoPage = new DTOPage.DTOPageBuilder()
        		.withPageNumber(1)
        		.withNumberTotalPage(null)
        		.withNumberOfElemetsToPrint(10)
        		.withNumberTotalOfComputer(null)
        		.withModelComputerList(null)
        		.build();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter("numberElementPerPages") != null) {
				dtoPage.setNumberOfElemetsToPrint(Integer.parseInt(request.getParameter("numberElementPerPages")));
			}
		} catch(Exception e) {
			System.err.println("Problème parsing numberElementPerPages");
		}
		
		try {
			dtoPage.setModelComputerList(
					mapperComputer.modelComputerListToDTOComputerList(
							serviceComputer.requestCompleteListLimit(
									1, dtoPage.getNumberOfElemetsToPrint())));
		} catch (Exception e) {
			System.err.println("Failed to get List of computers");
		}
		
		request.setAttribute("ComputerListObject", dtoPage.getModelComputerList());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
