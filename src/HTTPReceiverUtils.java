/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the class called by the receiver runnable to receive
 * data from the socket. The receive function returns an object of type HTTPObject 
 * after reading and parsing the request. This is called directly from the socket thread 
 * if pipelining is not enabled.
 */

import java.io.IOException;
import java.io.InputStream;

public class HTTPReceiverUtils {

	static int debugCode = 0x10;
	
	public static HTTPObject receive(InputStream is) throws IOException 
	{
		String header = new String(); // Read header into this
		String tmpline = new String();
		Debug.print("Starting to read header", debugCode);
		while(true) // Keep reading till end of header
		{
			tmpline = IOUtils.readLineFromStreamReader(is);
			if (tmpline == null ||  tmpline.equals("")) // End of stream OR \r\n\r\n
			{
				break;
			}
			header += tmpline + "\n"; // Concat read line to header
		}
		Debug.print("End reading header", debugCode);
		
		if (header == null || header == "") return null; // Null header
		
		Debug.print("Header is " + header, debugCode);
		Debug.print("Starting to parse header", debugCode);
		HTTPHeader hdrobj = new HTTPHeader(header); // Parse header string to header object
		Debug.print("End header parse", debugCode);
		if (hdrobj.attributes.get("content-length") == null)
		{
			Debug.print("No body detected.", debugCode);
			return new HTTPObject(hdrobj, null);
		}
		Debug.print("Starting to read body", debugCode);
		// Get the body length
		byte [] bodytmp = new byte[Integer.parseInt(hdrobj.attributes.get(StringConstants.contentLength.toLowerCase()))];
		is.read(bodytmp); // Read the body from isr
		Debug.print("End reading body", debugCode); 
		return new HTTPObject(hdrobj, IOUtils.b2B(bodytmp)); // return http object with body and header
	}
}
