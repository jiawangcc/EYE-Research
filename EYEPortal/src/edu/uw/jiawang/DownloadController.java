package edu.uw.jiawang;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadController
 */
@WebServlet("/DownloadController")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("documentId") != null) {
			downloadProjectDocument(request, response);
		} else if (request.getParameter("paperId") != null) {
			downloadResearchPaper(request, response);
		} else if (request.getRequestURI().contains("Dataset")) {
			downloadDataset(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void downloadProjectDocument(HttpServletRequest request, HttpServletResponse response) {
		int documentId = Integer.parseInt(request.getParameter("documentId"));
		ProjectDocument document = DocumentDB.getDocument(documentId);
		
		byte[] bytes = document.getDocument().get();
		
		if (bytes != null) {
			InputStream inputStream;
			int fileLength;
			try {
				inputStream = new ByteArrayInputStream(bytes);
				fileLength = inputStream.available();
				
		        System.out.println("fileLength = " + fileLength);
		
		        ServletContext context = getServletContext();
		
		        // sets MIME type for the file download
		        String fileName = document.getFileName();
		        String mimeType = context.getMimeType(fileName);
		        if (mimeType == null) {        
		            mimeType = "application/octet-stream";
		        }              
		         
		        // set content properties and header attributes for the response
		        response.setContentType(mimeType);
		        response.setContentLength(fileLength);
		        String headerKey = "Content-Disposition";
		        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		        response.setHeader(headerKey, headerValue);
		
		        // writes the file to the client
		        OutputStream outStream = response.getOutputStream();
		         
		        byte[] buffer = new byte[BUFFER_SIZE];
		        int bytesRead = -1;
		         
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		         
		        inputStream.close();
		        outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		} else {
			try {
				response.getWriter().print("Document not found for the id: " + documentId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	private void downloadResearchPaper(HttpServletRequest request, HttpServletResponse response) {
		int paperId = Integer.parseInt(request.getParameter("paperId"));
		researchWork paper = researchWorkDB.getPaper(paperId);
		
		byte[] bytes = paper.getPaper().get();
		
		if (bytes != null) {
			InputStream inputStream;
			int fileLength;
			try {
				inputStream = new ByteArrayInputStream(bytes);
				fileLength = inputStream.available();
				
		        System.out.println("fileLength = " + fileLength);
		
		        ServletContext context = getServletContext();
		
		        // sets MIME type for the file download
		        String fileName = paper.getFilename();
		        String mimeType = context.getMimeType(fileName);
		        if (mimeType == null) {        
		            mimeType = "application/octet-stream";
		        }              
		         
		        // set content properties and header attributes for the response
		        response.setContentType(mimeType);
		        response.setContentLength(fileLength);
		        String headerKey = "Content-Disposition";
		        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		        response.setHeader(headerKey, headerValue);
		
		        // writes the file to the client
		        OutputStream outStream = response.getOutputStream();
		         
		        byte[] buffer = new byte[BUFFER_SIZE];
		        int bytesRead = -1;
		         
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		         
		        inputStream.close();
		        outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		} else {
			try {
				response.getWriter().print("Paper not found for the id: " + paperId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	private void downloadDataset(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] schools = request.getParameterValues("school");
		String[] games = request.getParameterValues("game");
		String[] grades = request.getParameterValues("grade");
		
		Optional<List<String>> schoolList;
		Optional<List<Integer>> gameList;
		Optional<List<Integer>> gradeList;
		
		if (schools != null) {
			List<String> tmp = new ArrayList<>();
			for (int i = 0; i < schools.length; i++) 
		    {
		    	tmp.add(schools[i]);
		    }
			schoolList = Optional.of(tmp);
		} else {
			schoolList = Optional.empty();
		}
		
		if (games != null) {
			List<Integer> tmp = new ArrayList<>();
			for (int i = 0; i < games.length; i++) 
		    {
		    	tmp.add(Integer.valueOf(games[i]));
		    }
			gameList = Optional.of(tmp);
		} else {
			gameList = Optional.empty();
		}
		
		if (grades != null) {
			List<Integer> tmp = new ArrayList<>();
			for (int i = 0; i < grades.length; i++) 
		    {
		    	tmp.add(Integer.valueOf(grades[i]));
		    }
			gradeList = Optional.of(tmp);
		} else {
			gradeList = Optional.empty();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("School");
		sb.append(',');
		sb.append("Grade");
		sb.append(',');
		sb.append("Game");
		sb.append(',');
		sb.append("Test");
		sb.append(',');
		sb.append("Score");
		sb.append(',');
		sb.append("Date");
		sb.append(System.getProperty("line.separator"));
		
		for(DatasetEntry entry : scoreDB.prepareScoreDataset(schoolList, gameList, gradeList)) {
			sb.append(entry.getSchool());
			sb.append(',');
			sb.append(entry.getGrade());
			sb.append(',');
			sb.append(entry.getGameName());
			sb.append(',');
			sb.append(entry.getTestName());
			sb.append(',');
			sb.append(entry.getScore());
			sb.append(',');
			sb.append(entry.getDate());
			sb.append(System.getProperty("line.separator"));
		}
		
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", "attachment; filename=\"eye_dataset.csv\"");
	    try
	    {
	        OutputStream outputStream = response.getOutputStream();
	        outputStream.write(sb.toString().getBytes());
	        outputStream.flush();
	        outputStream.close();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		
	}

}
