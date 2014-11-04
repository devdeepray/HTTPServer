/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the sender runnable. Picks out processed data from the
 * request processor runnable's queue and pushes them onto the sockets output
 * stream. End of the pipe line 
 */

import java.io.OutputStream;

public class HTTPSenderRunnable implements Runnable
{
	HTTPProcessorRunnable hpt; // The processor from which to pick responses
	OutputStream os; // Output stream to write to 
	private static int debugCode = 0xF; // Debug mask
	
	public HTTPSenderRunnable(OutputStream os, HTTPProcessorRunnable hpt) 
	{
		this.hpt = hpt;
		this.os = os;
	}

	public void run() 
	{
		Debug.print("Sender pipe starting", debugCode );
		try 
		{
			do // Loop if keepalive.
			{
				Debug.print("Sender gonna take", debugCode);
				HTTPObject tmp = hpt.respMessages.take();
				Debug.print("Sender took", debugCode);
				if(tmp.header == null) // Exit if end of pipe signalled
					break;
				HTTPSenderUtils.send(tmp, os); // Send to socket
			} while (ServerSettings.isKeepAlive());
		} 
		catch ( Exception e) 
		{
			Debug.print("Exception caught in pipeline sender", debugCode);
		}
	}
}
