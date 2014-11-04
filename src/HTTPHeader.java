/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is a structure to represent a HTTP header in a
 * nice manner, which is easy to program with. It basically parses a string
 * header into the header structure, ie attributes and parameters. 
 */

import java.io.IOException;
import java.util.HashMap;

public class HTTPHeader 
{	
	public HashMap<String, String> attributes; // Attributes in header
	public String version; // Eg. HTTP/1.1
	public String param; // Eg. URI in GET request
	public String action; // Eg. GET, POST, etc
	
	HTTPHeader()
	{
		attributes = new HashMap<String, String>();
		version = new String();
		param = new String();
		action = new String();
	}
	
	HTTPHeader(String header) throws IOException
	{		
		attributes = new HashMap<String, String>(); // init new attributes
		String [] headerLines = header.split("\r?\n"); // split it into all lines
		String [] firstLineFields = headerLines[0].split(" "); // split first line
		if(firstLineFields.length != 3) throw new IOException(); // Bad request
		version = firstLineFields[2].trim(); // Version eg HTTP/1.0
		action = firstLineFields[0].trim(); // Action = GET, HEAD, POST, etc
		param = firstLineFields[1].trim(); // Parameter for action. eg URI, etc
		for(int i = 1; i < headerLines.length; ++i)
		{	
			String []tokns = headerLines[i].split(": "); // Split at :
			if(tokns.length != 2) continue; // Bad attribute case
			attributes.put(tokns[0].trim().toLowerCase(), tokns[1].trim()); // param name->small, param value->original
		}
	}
}
