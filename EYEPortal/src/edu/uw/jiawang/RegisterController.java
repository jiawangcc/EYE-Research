package edu.uw.jiawang;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uw.jiawang.util.RandomString;
import edu.uw.jiawang.util.SendMailTLS;

/**
 * Servlet implementation class RegisterController
 */
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
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
		String requestURI = request.getRequestURI(); //return value of action
		System.out.println(requestURI); 
		if(requestURI.endsWith("new")){
			String email = request.getParameter("email");
			if (!RegisterDB.checkUserAvail(email)) {
				response.getWriter().append("<script>alert(\""+email + " is already registered "
						+ "in the system. Please use a different email address\");history.go(-1);</script>");
				return;
			}
			Register r = new Register(request.getParameter("role"), request.getParameter("firstname"),
					request.getParameter("lastname"), request.getParameter("affil"), email, 
					request.getParameter("contact"), request.getParameter("phone"));
			if (RegisterDB.insertRegisterEntry(r)) {
				response.getWriter().append("<script>alert(\"Registration with " + email + " is pending approval. "
						+ "You'll receive an email regarding your new account request.\");history.go(-1);</script>");
			} else {
				response.getWriter().append("Registration with " + email + " failed. Please contact eyeproject@uw.edu for assistance");
			}
		}else if(requestURI.endsWith("approve")){
			String email = request.getParameter("email");
			Register r = RegisterDB.showRegisterEntry(email);
			String role = request.getParameter("role");
			if (role.equalsIgnoreCase("nurse") || role.equalsIgnoreCase("doctor") || role.equalsIgnoreCase("researcher")) {
				User usr = new User(r.getFirstname(), r.getLastname(), email, r.getAffiliation());
				String tempPw = new RandomString(12).nextString();
				
				if (UserDB.insertUser(usr, role, tempPw) && RegisterDB.deleteRegisterRoleEntry(email, role)) {
					SendMailTLS.sendMsgToEmailRecipient("EyeDB registration approved", "Login email: " + email + "\nTemp password: " + tempPw, email);
					response.getWriter().append("Informed user about registration status - Approved");
				} else {
					response.getWriter().append("Error approving registration request");
				}
			} else {
				throw new IllegalStateException("Unexpected role type for registration");
			}
			response.getWriter().append("Approved " + email + " for role[" + role + "]");
		}else if(requestURI.endsWith("reject")){
			String email = request.getParameter("email");
			String role = request.getParameter("role");
			if (RegisterDB.deleteRegisterRoleEntry(email, role)) {
				SendMailTLS.sendMsgToEmailRecipient("EyeDB registration denied", "Login email: " + email + "\nPlease contact eyedb@uw.edu for next steps", email);
				response.getWriter().append("<meta http-equiv=\"Refresh\" content=\"5;url=registerRequest.jsp\">Informed user about registration status - Rejected");
			} else {
				response.getWriter().append("Error rejecting registration request");
			}
		}
	}

}
