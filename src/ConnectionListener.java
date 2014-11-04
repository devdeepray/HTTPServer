/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the connection listener. Waits for incoming TCP connections,
 * creates a new session corresponding to it, and starts threads(or not) to process
 * the request.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener 
{	
	public static int debugCode = 0x2; // Debug level identifiers
	ServerSocket serversocket = null; // To listen to incoming conn.
	
	public ConnectionListener(int port) throws IOException 
	{
		serversocket = new ServerSocket(port); // Start server tcp socket
	}

	public void beginListening()
	{
		while (true) // Begins listening on the socket in an infinite loop.
		{
			Debug.print("Trying to accept incoming TCP connection", debugCode);	
			Socket socket;
			HTTPSession httpsession;
			try 
			{
				socket = serversocket.accept(); // Block till connection comes
				socket.setSoTimeout(ServerSettings.getSoTimeout()); // Set timeout
				Debug.print("TCP connection established", debugCode); 
				httpsession = new HTTPSession(socket); // Create new HTTPSession
			} 
			catch (IOException e)
			{
				Debug.print("Exception occurred while listening for connection", debugCode);
				continue; // Go back to listening
			} 
			
			if (ServerSettings.isMultiThreaded()) // Multithread enabled
			{
				Debug.print("Start http session multithreaded", debugCode);
				ThreadPool.executeSessionThread(httpsession); // Execute session on threadpool
				Debug.print("Started http session multithreaded", debugCode);
			}
			else // Threading disabled
			{
				Debug.print("Start http session non threaded", debugCode);
				httpsession.run(); // Execute session on this thread
				Debug.print("End http session non threaded", debugCode);
			}
		}		
	}
}
