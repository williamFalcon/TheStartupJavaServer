package server;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import dbConnectors.MYSQLConnector;
import dummyObjects.User;

/**
 * Http request handler
 * @author William Falcon
 *
 */
public class HttpRequestHandler extends AbstractHandler{

	//Private objects
	private MYSQLConnector mysql;

	/**
	 * Main constructor
	 */
	public HttpRequestHandler(){

		//Init sql
		mysql = new MYSQLConnector();
	}

	/**
	 * Abstract handler method. Captures HTTP request
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		System.out.println("HTTP Request received\n" + baseRequest.getRemoteHost());

		try {

			//Route GET requests
			if (baseRequest.getMethod().equals("GET")) {
				baseRequest.setHandled(true);
				processGETRequests(target, baseRequest, request, response);
				
				//Process POST requests
			}else if (baseRequest.getMethod().equals("POST")) {
				baseRequest.setHandled(true);
				processPOSTRequests(target, baseRequest, request, response);
			}

		} catch (Exception e) {

			//TODO: Handle exception
			e.printStackTrace();
		}

	}

	
	/********************************************************************************
	 *********************************REQUEST ROUTES*********************************
	 ********************************************************************************/
	
	/**
	 * Processes GET requests
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processGETRequests(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		//Format path
		String path = baseRequest.getPathInfo().toLowerCase();
		
		//Route paths
		if (path.contains("welcome")) {
			sampleMethodShowingJSONAndMySQL(target, baseRequest, request, response);
		}
	}
	
	/**
	 * Process POST requests
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processPOSTRequests(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
	}
	
	/********************************************************************************
	 *********************************GET HANDLERS***********************************
	 ********************************************************************************/
	
	/**
	 * Sample method to show how to handle a request, query a database, transform a request into
	 * JSON and send back to client
	 * 
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void sampleMethodShowingJSONAndMySQL(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		try {
			
			//Sample insert into MYSQL
			Random random = new Random();
			
			
			//Create user
			User user = new User();
			user.setFirst("Sample user first " + random.nextInt(2000));
			user.setLast("Sample user last" + random.nextInt(2000));
			user.setSocial(random.nextInt(2000));
			mysql.insertObject("user", user);
			
			//SAMPLE MYSQL get result from query
			ArrayList<Object> responseArray = mysql.getResultsFromQuery("SELECT * FROM user", new User());
			
			//Send JSON response
			ResponseManager.sendJSONResponse(responseArray, response);
			
		} catch (Exception e) {
			
			// Probably NO DB created
			ResponseManager.sendJSONResponse("Welcome to the basic HTTP server. To test MYSQL follow the read me", response);
		}
	}
	
	/**
	 * Inserts an image
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author waf04
	 */
	private BufferedImage extractImageFromMultipartRequest(HttpServletRequest request) throws Exception{
		
		//Init multipart parser
		MultipartParser parser = new MultipartParser(request, 1024 * 1024 * 1024);
		
		//Init image
		BufferedImage image = null;
		
		// If the content type is not multipart/form-data, this will be null.
		if (parser != null) {

		    Part imagepart;
		    
		    //While there is an image part
		    while ((imagepart = parser.readNextPart()) != null) {
		        if (imagepart instanceof FilePart) {
		        	
		        	//Get image input stream
		        	InputStream imageInput = ((FilePart) imagepart).getInputStream();
		        	
//		        	//Write to byte array
//		        	byte[] file = IOUtils.toByteArray(imageInput);
//		        	InputStream in = new ByteArrayInputStream(file);
		        	
		        	//Write input stream to image
		        	image = ImageIO.read(imageInput);
		        	
//		        	//Save image to disk
//		        	String fileName = ((FilePart) imagepart).getFilePath() + ".png";
//		        	ImageIO.write(bImageFromConvert, "png", new File(fileName));	
		        }
		        
		        else if (imagepart instanceof ParamPart) {
		            // This is request parameter from the query string
		        }
		    }
		}
		
		//return image
		return image;
	}
}















