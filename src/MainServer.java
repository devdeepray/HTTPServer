/**
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the main server class. It starts a connection listener and 
 * loads up the permanent files in the file cache.
 */

import java.io.IOException;

public class MainServer 
{
	public static int debugCode = 0x1; // Debug level identifier
	public static void main(String arg[])
	{	
		ConnectionListener connlistener = null; 
		
		try
		{
			ServerSettings.loadServerSettings("res/serverSettings.conf");
		}
		catch (Exception e)
		{
			Debug.print("Server settings not loaded. Defaulting", debugCode);
		}
		
		try
		{
			connlistener = new ConnectionListener(ServerSettings.getPortNumber());
		} 
		catch (IOException e)
		{
			Debug.print("Error starting socket", debugCode);
			e.printStackTrace();
			return;
		}
		try 
		{
			FileCache.initPermCache(); // Permanent file cache
		} 
		catch (IOException e1) 
		{
			Debug.print("Could not initiate permanent cache. Disk error?", debugCode);
			e1.printStackTrace();
			return;
		}
		StatsDaemon stats = null;
		if(ServerSettings.isCollectingStats())
		{
			try {
				stats = new StatsDaemon();
			} catch (IOException e) {
				e.printStackTrace();
				Debug.print("Unable to start stats daemon", debugCode);
				return;
			}
			stats.setDaemon(true);
			stats.start();
		}
		
		Debug.print("Listening on port " + ServerSettings.getPortNumber(), debugCode);
		connlistener.beginListening();	// Start listening
	}
}
