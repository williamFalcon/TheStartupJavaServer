package server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

import dbConnectors.MYSQLConnector;

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
		
	}

}
