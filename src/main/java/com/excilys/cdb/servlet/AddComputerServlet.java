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
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.springConfig.SpringContext;
import com.excilys.cdb.validator.Validator;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	private MapperCompany mapperCompany;
	private MapperComputer mapperComputer;
	private MapperDTO mapperDTO;
	private Validator validator;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
		
		SpringContext springContext = SpringContext.getInstance();
		
		serviceCompany = springContext.getServiceCompany();
		serviceComputer = springContext.getServiceComputer();
		mapperCompany = springContext.getMapperCompany();
		mapperComputer = springContext.getMapperComputer();
		mapperDTO = springContext.getMapperDTO();
		validator = springContext.getValidator();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<DTOCompany> dtoCompanyList = null;
		try {
			dtoCompanyList = mapperCompany.modelCompanyListToDTOCompanyList(serviceCompany.requestList());
		} catch (Exception e) {
			System.err.println("Failed to get List of company");
		}
		
		request.setAttribute("companyListObject", dtoCompanyList);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DTOComputer dtoComputerToCreate = mapperDTO.dataToDTOComputer(request);
		
		if (validator.dtoComputerValidation(dtoComputerToCreate)) {
			try {
				serviceComputer.requestCreate(mapperComputer.dtoComputerToModelComputer(dtoComputerToCreate));
			} catch (Exception e) {
				System.err.println("Update or mapping to model failed");
			}
		}
		
		response.sendRedirect("dashboard");
	}

}
