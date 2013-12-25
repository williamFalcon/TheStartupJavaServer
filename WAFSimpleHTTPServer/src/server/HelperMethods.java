package server;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

/**
 * Helpful methods
 * @author William Falcon
 *
 */
public abstract class HelperMethods {

	//Private attributes
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Get current now time
	 * @return time now
	 */
	public static String now() {

		//Calendar instance reflecting the date now
		Calendar cal = Calendar.getInstance();

		//Format
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		//Return
		return sdf.format(cal.getTime());
	}

	/**
	 * Extracts image from multi part request
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

					//Write input stream to image
					image = ImageIO.read(imageInput);		        	
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
