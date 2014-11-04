/**
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the session runnable that gets executed on a thread. 
 * It starts up the connection pipes and executes them in the thread pool, 
 * or runs the read process write loop. The streams are extracted here. 
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPSession implements Runnable
{
	Socket socket;
	public static int debugCode = 0x4;
	
	public HTTPSession(Socket socket)
	{
		this.socket = socket;
	}
	
	public void closeSocket()
	{
		// One attempt to close socket. 
		try 
		{
			socket.close();
		}
		catch (IOException e1) 
		{
			Debug.print("Couldnt close socket. Still exiting", debugCode);
		}
	}
	
	public void run() 
	{
		// Start processing a connection. Cant throw exceptions from here, all must be handled
		InputStream is = null;
		OutputStream os = null;
		// Create a buffered reader and buffered writer and a buffered output stream for binary
		try
		{
			is = socket.getInputStream();
			os = socket.getOutputStream();
		}
		catch (IOException e)
		{
			Debug.print("IOException while creating streams from socket", debugCode);
			closeSocket();
			return;
		}
		// Pipelining the requests and data sending if piping enabled
		if(ServerSettings.isPiped())
		{	
			HTTPReceiverRunnable hrt = new HTTPReceiverRunnable(is);
			HTTPProcessorRunnable hpt = new HTTPProcessorRunnable(hrt);
			HTTPSenderRunnable hst = new HTTPSenderRunnable(os, hpt);
			Debug.print("Starting all pipe threads", debugCode);
			ThreadPool.executeSessionThread(hrt); // Execute receiver in a thread
			ThreadPool.executeSessionThread(hpt); // Execute processor in a thread
			hst.run(); // Use same thread for the sender thread
			Debug.print("third pipe exited. Closing connections, blah", debugCode);
		}
		else
		{
			Debug.print("Non threaded rec, proc, send loop", debugCode);
			try 
			{				
				boolean keepaliveEnabled = ServerSettings.keepalive; // Keep alive flag for loop
				while(keepaliveEnabled)
				{
					HTTPObject recObj = HTTPReceiverUtils.receive(is);
					String keepaliveflag = recObj.header.attributes.get(StringConstants.connection);
					// Check if close connection received
					if(keepaliveflag != null && 
						keepaliveflag.toLowerCase().equals(StringConstants.closeConnection.toLowerCase()))
					{	
						keepaliveEnabled = false;
					}
					// Process and send response
					HTTPSenderUtils.send(HTTPRequestProcessor.getResponse(recObj), os);
				}
			} 
			catch (Exception e) 
			{	
				Debug.print("Finished rec proc send loop.", debugCode);
			}
		}
		System.err.println("HTTP Session ended. Closing down connection and other stuff");
		closeSocket();
		return;
	}
}
