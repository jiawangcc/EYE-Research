package edu.uw.jiawang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminController
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(login(request)) {
			response.sendRedirect("/EYEPortal/registerRequest.jsp");
		} else {
			response.sendRedirect("/EYEPortal/loginError.jsp");
		}
	}
	
	private boolean login(HttpServletRequest request) {
        
		String email = request.getParameter("email");
		String passwd = request.getParameter("password");
		
		boolean loginSuccessful = (AdminDB.checkLogin(email, passwd) != null);
		
		if(loginSuccessful){
		   	request.getSession().setAttribute("role", "admin");
			request.getSession().setAttribute("name", email);
		}
		return loginSuccessful;
	}

}
