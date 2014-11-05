

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class StatsDaemon extends Thread{
	
	static BlockingQueue<ConnStats> printQueue;
	static BufferedWriter br;
	
	StatsDaemon() throws IOException
	{
		printQueue = new LinkedBlockingQueue<ConnStats>();
		File f = new File(ServerSettings.getStatsFile());
		br = new BufferedWriter(new FileWriter(f));
	}
	
	public static void printStatsToLog(ConnStats connstats)
	{
		if(!ServerSettings.isCollectingStats()) return;
		try 
		{
			printQueue.put(connstats); // Put stats in the print queue
		}
		catch (InterruptedException e) 
		{
			// Do nothing
		}
	}

	@Override
	public void run() 
	{
		int flushCounter = 0;
		while(true)
		{
			++flushCounter;
			flushCounter %= ServerSettings.getStatsFlushFrequency();
			try
			{
				ConnStats cs = printQueue.take(); // Block till something in the queue
				br.write(cs.getStringRep()); // Write to file
				if(flushCounter == 0)
				{
					br.flush(); // Flush every 20 outputs
				}
			}
			catch (InterruptedException | IOException e) 
			{
				// Do nothing
			}
		}
	}
}
