package server;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import com.google.gson.Gson;

/**
 * Responsible for the different response types
 * @author waf04
 *
 */
public abstract class ResponseManager {

	/**
	 * Sends a JSON response for an object (200)
	 * @param objectToEncode
	 * @throws Exception
	 */
	public static void sendJSONResponse(Object objectToEncode, HttpServletResponse response, Request baseRequest) throws Exception{

		//Encode object into JSON
		String jsonString = new Gson().toJson(objectToEncode);

		//Set http headers
		response.setContentType("application/json"); 
		response.setStatus(HttpServletResponse.SC_OK);
		
		try {
			
			//Let's jetty know the request was finished so we can move on
			baseRequest.setHandled(true);

			//Write response and close resource
			PrintWriter outPrintWriter = response.getWriter();
			outPrintWriter.print(jsonString);
			outPrintWriter.flush();

		} catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
