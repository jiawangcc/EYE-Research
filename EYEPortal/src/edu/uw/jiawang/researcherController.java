package edu.uw.jiawang;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class userController
 */
public class researcherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public researcherController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI(); //return value of action
		System.out.println(requestURI); 
		String url = "";
		if(requestURI.endsWith("Update")){
			request.getSession().setAttribute("researcherId", request.getParameter("researcherId"));
			url = updateResearcher(request);
		}
		
		response.sendRedirect(url);
	}
		
	
	private String updateResearcher(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int researcherId = Integer.parseInt(request.getParameter("researcherId"));
		String affil = request.getParameter("affil");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Researcher r = new Researcher();
		r.setFirstname(firstName);
		r.setLastname(lastName);
		r.setResId(researcherId);
		r.setAffiliation(affil);
		r.setEmail(email);
		
		if (researcherDB.updateResearcher(r)) {
			return "/EYEPortal/editResearcher.jsp";
		} else {
			return "/EYEPortal/err.html";
		}
	}
}
