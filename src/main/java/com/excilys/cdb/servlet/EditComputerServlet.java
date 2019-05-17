package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.mapper.MapperDTO;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	private MapperCompany mapperCompany;
	private MapperComputer mapperComputer;
	private MapperDTO mapperDTO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        try {
			serviceCompany = ServiceCompany.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get ServiceCompany without exception");
		}
        try {
			serviceComputer = ServiceComputer.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get ServiceComputer without exception");
		}
        try {
			mapperCompany = MapperCompany.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperCompany without exception");
		}
        try {
			mapperComputer = MapperComputer.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperComputer without exception");
		}
        try {
			mapperDTO = MapperDTO.getInstance();
		} catch (Exception e) {
			System.err.println("Erreur get MapperDTO without exception");
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = -1;
		
		try {
			id = Integer.parseInt(request.getParameter("id"));
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
			System.err.println("Failed to get List of computers");
		}
		
		request.setAttribute("id", id);
		request.setAttribute("computerDetails", dtoComputerDetails);
		request.setAttribute("companyListObject", dtoCompanyList);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/editComputer.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DTOComputer dtoComputerToEdit = mapperDTO.dataToDTOComputer(request);
		/* Partie validateur à ajouter */
		try {
			serviceComputer.requestUpdate(mapperComputer.dtoComputerToModelComputer(dtoComputerToEdit));
		} catch (Exception e) {
			System.err.println("Update or mapping to model failed");
		}
		response.sendRedirect("dashboard");
	}

}
