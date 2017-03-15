package edu.uw.jiawang;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class userController
 */

public class scoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static List<Integer> prepareDatasetForNumerics(int testId) {
		return scoreDB.showAllScoreEntriesByTest(testId).stream()
				.map(s -> s.getScore())
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}
	
	
	public static Map<String, Long> prepareDataset(int testId) {
		return scoreDB.showAllScoreEntriesByTest(testId).stream()
				.map(s -> s.getScore())
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scoreController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI); 
	    if (requestURI.contains("insert")){
			String msg = "";
			if(syncScore(request)){
				msg = "Success";
			}
			else{
				msg = "Failed";
			}
			response.getWriter().println(msg);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI(); //return value of action
		System.out.println(requestURI); 
		
		if (requestURI.contains("insert")){
			String msg = "";
			if(syncScore(request)){
				msg = "Success";
			}
			else{
				msg = "Failed";
			}
			response.getWriter().println(msg);
			
		}
		
	}

	//this method right now is only for testing with Huaming
	private boolean syncScore(HttpServletRequest request){
			
		int sid;
		String test = "";
		String date = "";
		String score = "";
		boolean result = false;
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(request.getParameter("payload"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray values = (JSONArray) jsonObject.get("values");		
			Iterator<JSONObject> iterator = values.iterator();
			while (iterator.hasNext()) {
				JSONObject row = (JSONObject)iterator.next();
				sid = Integer.valueOf((String)row.get("sid"));
				test = (String) row.get("test");
				int tid = 0;
				switch(test){
				case "ciss": tid = 1;
				               break;
				case "acuity": tid = 2;
	               break;
				case "stereopsis": tid = 3;
	               break;
				case "convergence": tid = 4;
	               break;
				}
				date = (String) row.get("date");
				score = (String) row.get("score");
				
				Score s = new Score();
				s.setStuId(sid);
				s.setTestId(tid);
				s.setDate(date);
				s.setScore(score);
				result = scoreDB.insertScore(s);
			}
					
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
		

}
