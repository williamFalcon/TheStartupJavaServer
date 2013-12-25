package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import dbConnectors.MYSQLConnector;

/**
 * Invokes appropriate class to deal with each HTTP request type
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
				GETManager.processGETRequests(target, baseRequest, request, response, mysql);

				//Process POST requests
			}else if (baseRequest.getMethod().equals("POST")) {
				baseRequest.setHandled(true);
				POSTManager.processPOSTRequests(target, baseRequest, request, response, mysql);
			}

		} catch (Exception e) {

			//TODO: Handle exception
			e.printStackTrace();
		}
	}
}















