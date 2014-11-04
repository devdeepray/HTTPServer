/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the receiver runnable. Runs in a loop and keeps accepting
 * requests till connection closed by client or till exception raised. Before quitting, 
 * it signals the next pipe segment the end of flow. 
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class HTTPReceiverRunnable implements Runnable
{
	// Receiver receives messages and puts it in the queue for the processor to drain
	LinkedBlockingQueue<HTTPObject> recMessages; // Queue where received messages are put
	InputStream is; // Input stream to receive messages from socket
	private static int debugCode = 0xF;
	
	public HTTPReceiverRunnable(InputStream is) {
		super();
		recMessages = new LinkedBlockingQueue<HTTPObject>();
		this.is = is;
	}
	
	public void run()
	{
		Debug.print("Start receiver pipe", debugCode);
		try 
		{
			int count = 0; // Count of number of requests processed
			do
			{	
				Debug.print("Starting to receive message", debugCode );
				HTTPObject htoTmp = HTTPReceiverUtils.receive(is);
				recMessages.put(htoTmp); // Put received data in queue
				if(htoTmp.header != null && 
					IOUtils.caseIgnoreEqual(htoTmp.header.attributes.get(StringConstants.connection), 
											StringConstants.closeConnection))
				{
					break; // Close connection attribute by client
				}
				Debug.print("End receiving message", debugCode);
				++count; // Increment number of requests processed
			} while (ServerSettings.isKeepAlive() && count < ServerSettings.getKeepAliveMaxRequests());	
		}
		catch (IOException | InterruptedException e) 
		{
			Debug.print("IOException raised from something related to socket.", debugCode);
		}
		Debug.print("Receiver pipe exiting", debugCode);
		try 
		{
			recMessages.put(HTTPObject.nullHTTPObject()); // Signal next pipe end of pipeline
		} 
		catch (InterruptedException e1) 
		{
			System.err.println("Interruption of blocking queue. Shouldnt happen. Fatal error.");
		}
	}
}
