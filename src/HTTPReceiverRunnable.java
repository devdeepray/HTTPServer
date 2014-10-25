import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;


public class HTTPReceiverRunnable implements Runnable{
	
	// Receiver receives messages and puts it in the queue for the processor to drain
	LinkedBlockingQueue<HTTPObject> recMessages;
	InputStream is;
	private static int debugCode = 0xF;
	
	
	public HTTPReceiverRunnable(InputStream is) {
		super();
		recMessages = new LinkedBlockingQueue<HTTPObject>();
		this.is = is;
	}
	
	public void run()
	{
		
		Debug.print("Start receiver pipe", debugCode);
		try {
			
			do{
				
				Debug.print("Starting to receive message", debugCode );
				recMessages.put(HTTPReceiverUtils.receive(is));
				System.err.println("End receiving message");
			}while(ServerSettings.isKeepAlive());
			
		}catch (IOException | InterruptedException e) {
			
			System.err.println("Receiver pipe exiting");
			try {
				System.out.println("Start put nul object in q");
				recMessages.put(HTTPObject.nullHTTPObject());
				System.out.println("End put nul object in q");
			} catch (InterruptedException e1) {
				
				System.err.println("Interruption of blocking queue. Shouldnt happen. Fatal error.");
			}
			
			return;
		}
		
	}

}
