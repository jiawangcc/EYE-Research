package edu.uw.jiawang;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class userController
 */
public class studentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public studentController() {
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
		if(requestURI.endsWith("Update")){
			request.getSession().setAttribute("studentId", request.getParameter("studentId"));
			response.getOutputStream().println(updateStudent(request));
		}
	}
		
	
	private String updateStudent(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int grade = Integer.parseInt(request.getParameter("grade"));
		String school = request.getParameter("school");
		
		Student s = new Student();
		s.setFirstname(firstName);
		s.setLastname(lastName);
		s.setDOB(dob);
		s.setStuId(studentId);
		s.setGrade(grade);
		s.setSchool(school);
		
		if (studentDB.updateStudent(s)) {
			return "Update succeeded";
		} else {
			return "Update failed";
		}
	}
}
