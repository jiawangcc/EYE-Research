package edu.uw.jiawang;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Servlet implementation class UploadController
 */
public class UploadController extends HttpServlet {
	public enum Headers {
		ChildName,Student_ID,HorizontalParameter,NeedBeanBag,VerticalParameter,
		EyeOpenLeft,EyeCloseLeft,EyeOpenRight,EyeCloseRight,Date,
		Skipping,Round1,Round2,VisualDiscrimination,TummyCrawl;
	}
	
	private static final long serialVersionUID = 1L;
	private static final String CSV_MIME_TYPE = "text/csv";
	private static final Map<Headers, Integer> REDFLAG_TEST_MAPPING;
	
	static {
        Map<Headers, Integer> aMap = new HashMap<>();
        aMap.put(Headers.HorizontalParameter, 5);
        aMap.put(Headers.VerticalParameter, 6);
        aMap.put(Headers.NeedBeanBag, 7);
        aMap.put(Headers.EyeOpenLeft, 8);
        aMap.put(Headers.EyeCloseLeft, 9);
        aMap.put(Headers.EyeOpenRight, 10);
        aMap.put(Headers.EyeCloseRight, 11);
        aMap.put(Headers.Round1, 12);
        aMap.put(Headers.Round2, 13);
        aMap.put(Headers.Skipping, 14);
        aMap.put(Headers.VisualDiscrimination, 15);
        aMap.put(Headers.TummyCrawl, 16);
        REDFLAG_TEST_MAPPING = Collections.unmodifiableMap(aMap);
    }

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart || (request.getSession().getAttribute("role") == null)) {
			response.sendRedirect("err.html");
		}
		
		String role = (String) request.getSession().getAttribute("role");
		String toPage = "/EYEPortal/unauthorized.html";
		
		if (role.equalsIgnoreCase("researcher")) {
			toPage = uploadResearchPaper(request, response);
		} else if (role.equalsIgnoreCase("admin")) {
			String url = request.getRequestURI();
			if (url.contains("document")) {
				toPage = uploadProjectDocument(request, response);
			} else if (url.contains("dataset")) {
				response.getWriter().print("<body><b>");
				response.getWriter().print(uploadDataset(request, response));
				response.getWriter().print("</b><br/><a href='/EYEPortal/uploadTestResult.jsp'>Go Back</a>");
				response.getWriter().println("</body>");
				response.getWriter().flush();
				return;
			} 
		}
		response.sendRedirect(toPage);
	}

	private String uploadResearchPaper(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean uploadSuccessful = false;
		
		Map<String, String> params = new HashMap<>();
		String fileName = null;

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		InputStream stream = null;
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				stream = item.openStream();
				if (item.isFormField()) {
					params.put(name, Streams.asString(stream));
				} else {
					params.entrySet().stream().forEach(e -> System.err.println(e.getKey() + ":" + e.getValue()));;
					fileName = item.getName();
					// Process the input stream
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					IOUtils.copy(stream, output);
					
					researchWork rw = new researchWork();
					rw.setResearcherId(Integer.valueOf((String)request.getSession().getAttribute("id")));
		    		rw.setFilename(fileName);
		    		rw.setTitle(params.get("title"));
		    		rw.setKeyword(params.get("keyword"));
		    		rw.setAbstract(params.get("abstract"));
		    		rw.setPaper(Optional.of(output.toByteArray()));
					uploadSuccessful = researchWorkDB.insertWork(rw);
					if (uploadSuccessful) {
						System.out.println("File " + fileName + " uploaded.");
					} else {
						System.err.println("File " + fileName + " failed during upload.");
					}
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		return uploadSuccessful ? "/EYEPortal/researcher.jsp" : "/EYEPortal/err.html";
	}

	private String uploadProjectDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean uploadSuccessful = false;
		String fileName = null;

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		InputStream stream = null;
		try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				stream = item.openStream();
				if (item.isFormField()) {
					System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
				} else {
					fileName = item.getName();
					// Process the input stream
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					IOUtils.copy(stream, output);
					uploadSuccessful = DocumentDB.insertDocument(new ProjectDocument(Optional.empty(), 
							(String)request.getSession().getAttribute("name"),
							fileName, Optional.of(output.toByteArray()), Optional.empty()));
					if (uploadSuccessful) {
						System.out.println("File " + fileName + " uploaded.");
					} else {
						System.err.println("File " + fileName + " failed during upload.");
					}
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		return uploadSuccessful ? "/EYEPortal/uploadProjectPaper.jsp" : "/EYEPortal/err.html";
	}
	

	private String uploadDataset(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean uploadSuccessful = true;
        int cntScoreEntries = 0;
        
        StringBuilder returnMsgBuilder = new StringBuilder();
        
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	String name = item.getFieldName();
			        String value = item.getString();
			    	System.out.println("Form field " + name + " with value " + value + " detected.");
			    } else {
			        String fileName = item.getName();
			        String contentType = item.getContentType();
			        if (!CSV_MIME_TYPE.equals(contentType)) {
			        	return "Not a CSV file.";
			        }
			        long sizeInBytes = item.getSize();
			        System.out.println(fileName + "(" + sizeInBytes + ") " + contentType);
			        
			        File uploadedFile = new File(fileName);
			        item.write(uploadedFile);
			        
			        Reader in = new FileReader(uploadedFile.getAbsolutePath());
			        Iterable<CSVRecord> records = CSVFormat.DEFAULT
			        		.withHeader(Headers.class)
			        		.withSkipHeaderRecord(true)
			        		.parse(in);

			        for (CSVRecord record : records) {
			            for (Map.Entry<Headers, Integer> entry : REDFLAG_TEST_MAPPING.entrySet()) {
		            		Score s = new Score();
		            		s.setStuId(Integer.valueOf(record.get(Headers.Student_ID)));
		            		s.setTestId(entry.getValue());
		            		s.setDate(record.get(Headers.Date));
			            	if ( Headers.EyeOpenLeft.equals(entry.getKey()) 
			            	  || Headers.EyeCloseLeft.equals(entry.getKey()) 
			            	  || Headers.EyeOpenRight.equals(entry.getKey()) 
			            	  || Headers.EyeCloseRight.equals(entry.getKey()) ) {
			            		s.setScore(String.valueOf(convertDurationToSeconds(record.get(entry.getKey()))));
			            	} else {
			            		s.setScore(WordUtils.capitalize(record.get(entry.getKey())));
			            	}
			            	uploadSuccessful &= scoreDB.insertScore(s);
			            	cntScoreEntries++;
			            }
			        }
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMsgBuilder.append('\n');
		returnMsgBuilder.append("Uploaded of " + cntScoreEntries + " score entries " + (uploadSuccessful ? "succeeded" : "failed"));
		return returnMsgBuilder.toString();
	}
	
	private static int convertDurationToSeconds(String dur) {
		String[] parts = dur.split(":");
		return Integer.parseInt(parts[0]) * 3600 + Integer.parseInt(parts[1]) * 60 + Integer.parseInt(parts[2]);
	}
}
