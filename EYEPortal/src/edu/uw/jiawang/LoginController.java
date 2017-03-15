package edu.uw.jiawang;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI); 
		String url = "";
		
		if(requestURI.endsWith("Login")){
			url = login(request);
			response.sendRedirect(url);
		}
		else if(requestURI.contains("check_sid")){
			response.getWriter().println(getSalt(request));
		}
		else if(requestURI.contains("check_pwd")){
			response.getWriter().println(checkStudent(request) ? "Valid" : "Invalid");
		}
		else if(requestURI.contains("change")){
			url = changePwd(request);
			response.sendRedirect(url);
		}	
	}
	
	private String login (HttpServletRequest request){
		
		String url = "";
        
		String role = request.getParameter("role");
		String passwd = request.getParameter("password");
		String loginId = request.getParameter("id");
		String id = "";
		
		if(role.equals("parent")){
			int iId = 0;
			try {
				iId = Integer.valueOf(loginId);
			} catch (NumberFormatException ex) {
			}
			id = studentDB.checkLogin(iId,passwd);
		}
		else if(role.equals("nurse")){
			id = nurseDB.checkLoginByEmail("nurse", loginId,passwd);
		}
		else if(role.equals("doctor")){
			id = doctorDB.checkLoginByEmail("doctor", loginId,passwd);
		}
		else if(role.equals("researcher")){
			id = researcherDB.checkLoginByEmail("researcher", loginId,passwd);
		}
		
		if(id.equals("")){
			url = "/EYEPortal/loginError.jsp";
		}else{
			    request.getSession().setAttribute("role", role);
				request.getSession().setAttribute("id", id);
				request.getSession().setAttribute("name", loginId);
				url = "/EYEPortal/" + role + ".jsp";
		}
		
		return url;
	}
	
	
	private String changePwd (HttpServletRequest request){
		
		String url = "";
        
		String role = (String) request.getSession().getAttribute("role");
		String oldpasswd = request.getParameter("oldPassword");
		String newpasswd = request.getParameter("newPassword");
		String loginId = request.getParameter("id");
		String id = "";
		
		if(role.equals("parent")){
			int iId = 0;
			try {
				iId = Integer.valueOf(loginId);
			} catch (NumberFormatException ex) {
			}
			id = studentDB.checkLogin(iId,oldpasswd);
		}
		else if(role.equals("nurse")){
			id = nurseDB.checkLoginByEmail("nurse", loginId,oldpasswd);
		}
		else if(role.equals("doctor")){
			id = doctorDB.checkLoginByEmail("doctor", loginId,oldpasswd);
		}
		else if(role.equals("researcher")){
			id = researcherDB.checkLoginByEmail("researcher", loginId,oldpasswd);
		}
		
		if(id.equals("")){
			url = "/EYEPortal/loginError.jsp";
		}else{
			
			UserDB.updatePwd(Integer.valueOf(id), role, newpasswd);
			url = "/EYEPortal/index.jsp";
		}
		
		return url;
	}
	private String getSalt(HttpServletRequest request){
		
		try {
			int sid = Integer.valueOf((String) request.getParameter("sid"));
			String salt = studentDB.selectSalt(sid);

			return salt;
		} catch (NumberFormatException e) {
			return "";
		}
		
	}
	
	private boolean checkStudent(HttpServletRequest request){
		
		try {
			int sid = Integer.valueOf((String) request.getParameter("sid"));
			String pwd = (String) request.getParameter("pwd");

			return studentDB.checkSyncLogin(sid, pwd);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
