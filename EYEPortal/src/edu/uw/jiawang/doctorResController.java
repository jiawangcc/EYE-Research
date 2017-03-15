package edu.uw.jiawang;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NurseResController
 */
public class doctorResController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doctorResController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().endsWith("New")) {
			request.getSession().setAttribute("studentId", request.getParameter("studentId"));
			response.sendRedirect(insertNote(request));
		} else {
			response.sendRedirect("index.jsp");
		}
	}
	
	private String insertNote(HttpServletRequest request) {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int doctorId = Integer.parseInt(request.getParameter("doctorId"));
		String note = request.getParameter("comment");
		
		doctorRes dr = new doctorRes();
		dr.setStuId(studentId);
		dr.setDoctorId(doctorId);
		dr.setNote(note);
		if (doctorResDB.insertNote(dr)) {
			return "/EYEPortal/viewScore.jsp";
		} else {
			return "/EYEPortal/err.html";
		}
	}

}
