/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the HTTP Processor runnable. It takes requests from above pipe
 * and processes it and puts it into its own queue. Keeps doing till it reads
 * HTTP object with NULL header. 
 */

import java.util.concurrent.LinkedBlockingQueue;

public class HTTPProcessorRunnable implements Runnable
{
	HTTPReceiverRunnable hrt; // Receiver runnable which receives messages
	LinkedBlockingQueue<HTTPObject> respMessages; // Responses put into this
	private static int debugCode = 0xF;
	
	public HTTPProcessorRunnable(HTTPReceiverRunnable hrt) 
	{
		this.hrt = hrt;
		respMessages = new LinkedBlockingQueue<HTTPObject>();
	}

	public void run()
	{
		Debug.print("Start processor pipe", debugCode);
		try
		{
			do // Keep alive loop
			{
				Debug.print("Waiting for queue", debugCode);
				HTTPObject recMessage = hrt.recMessages.take(); // Remove one message to process
				if(recMessage.header == null) // End pipe signal from hrt
					break;
				Debug.print("Start processing message", debugCode);
				respMessages.put(HTTPRequestProcessor.getResponse(recMessage)); // Process request
				Debug.print("End processing message", debugCode);
			} while (ServerSettings.isKeepAlive()); // Keep looping
		}
		catch (Exception e)
		{
			Debug.print("Pipe Exception", debugCode);
		}
		Debug.print("Processor pipe exiting", debugCode);
		try
		{	
			respMessages.put(HTTPObject.nullHTTPObject()); // Signal sender end of pipe
		}
		catch (InterruptedException e) 
		{	
			System.err.println("Interruption of blocking queue. Fatal error.");
		}
	}
}
