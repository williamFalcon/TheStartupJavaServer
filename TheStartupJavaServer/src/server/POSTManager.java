package server;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

import settings.RoutingSettings;

import dbConnectors.MYSQLConnector;
import dummyObjects.User;

public class POSTManager {

	/**
	 * Process POST requests
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	static final void processPOSTRequests(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response, MYSQLConnector mysql) throws Exception{

		//Format path
		String path = baseRequest.getPathInfo().toLowerCase();

		//Route paths
		if (path.contains(RoutingSettings.insertObject)) {
			postObject(target, baseRequest, request, response, mysql);
		}
	}
	
	/**
	 * Inserts an object into a sql database and sends back a bool success flag
	 * 
	 * @param target
	 * @param baseRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private static void postObject(String target, Request baseRequest, HttpServletRequest request,
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
			ArrayList<Object> responseArray = mysql.executeGenericQuery("SELECT * FROM Sample.user",null);
			
			//Send JSON response
			ResponseManager.sendJSONResponse(responseArray, response, baseRequest);
			
		} catch (Exception e) {
			
			//NO DB created
			ResponseManager.sendJSONResponse("Welcome to the basic HTTP server. To test MYSQL follow the read me", response, baseRequest);
		}
	}


}
