package server;

import java.util.ArrayList;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import dbConnectors.MYSQLConnector;
import dummyObjects.User;

/**
 * Handles GET requests
 * @author WIlliam Falcon
 *
 */
public abstract class GETManager {
	
	/**
	 * Processes GET requests
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void processGETRequests(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response, MYSQLConnector mysql) throws Exception{
		
		//Format path
		String path = baseRequest.getPathInfo().toLowerCase();
		
		//Route paths
		if (path.contains("welcome")) {
			sampleMethodShowingJSONAndMySQL(target, baseRequest, request, response, mysql);
		}
	}
	
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
	private static void sampleMethodShowingJSONAndMySQL(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response, MYSQLConnector mysql) throws Exception{
		
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
			ArrayList<Object> responseArray = mysql.executeGenericQuery("SELECT * FROM user");
			
			//Send JSON response
			ResponseManager.sendJSONResponse(responseArray, response, baseRequest);
			
		} catch (Exception e) {
			
			//NO DB created
			ResponseManager.sendJSONResponse("Welcome to the basic HTTP server. To test MYSQL follow the read me", response, baseRequest);
		}
	}

}
