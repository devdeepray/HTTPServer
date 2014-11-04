/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is a class to send data on a socket. Basically, it makes string
 * of the header and writes that to socket, followed by writing of the data to 
 * the socket
 */

import java.io.OutputStream;

public class HTTPSenderUtils 
{
	public static void send(HTTPObject response, OutputStream bos) throws Exception 
	{
		bos.write((response.header.version + " " + response.header.action + " " + response.header.param + "\r\n").getBytes());
		for(String x : response.header.attributes.keySet())
		{
			bos.write((x + ": " + response.header.attributes.get(x) + "\r\n").getBytes());
			
		}
		bos.write("\r\n".getBytes());
		bos.write(IOUtils.B2b(response.body));
		bos.flush();
	}	
}
