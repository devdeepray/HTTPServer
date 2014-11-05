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
	public static void send(HTTPObject response, OutputStream bos, ConnStats cs) throws Exception 
	{
		String tmp = (response.header.version + " " + response.header.action + " " + response.header.param + "\r\n");
		bos.write(tmp.getBytes());
		cs.bytesSent += tmp.length();
		for(String x : response.header.attributes.keySet())
		{
			tmp = (x + ": " + response.header.attributes.get(x) + "\r\n");
			bos.write(tmp.getBytes());	
			cs.bytesSent += tmp.length();
		}
		tmp = "\r\n";
		bos.write(tmp.getBytes());
		cs.bytesSent += tmp.length();
		bos.write(IOUtils.B2b(response.body));
		cs.bytesSent += response.body.length;
		bos.flush();
	}	
}
