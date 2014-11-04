/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This class represents an HTTP object in a structured manner. 
 * It has a header and a binary payload.
 */

public class HTTPObject 
{
	public HTTPHeader header;
	public Byte [] body;
	
	public HTTPObject(HTTPHeader header, Byte[] body)
	{
		this.header = header;
		this.body = body;
	}

	public HTTPObject() 
	{
		header = new HTTPHeader();
		body = null;
	}
	
	public static HTTPObject nullHTTPObject(){
		HTTPObject tmp = new HTTPObject();
		tmp.header = null;
		return tmp;
	}
}
