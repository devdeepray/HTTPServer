import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.*;


public class HTTPReceiverRunnable implements Runnable{

	LinkedBlockingQueue<HTTPObject> recMessages;
	BufferedInputStream bis;
	private static int debugCode = 0xF;
	
	
	public HTTPReceiverRunnable(BufferedInputStream bis) {
		super();
		recMessages = new LinkedBlockingQueue<HTTPObject>();
		this.bis = bis;
	}
	
	public void run()
	{
		Debug.print("Start receiver pipe", debugCode);
		try {
			do{
				Debug.print("Starting to receive message", debugCode );
				recMessages.put(HTTPReceiverUtils.receive(bis));
				System.err.println("End receiving message");
			}while(ServerSettings.isKeepAlive());
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.err.println("Receiver pipe exiting");
			try {
				recMessages.put(HTTPObject.nullHTTPObject());
			} catch (InterruptedException e1) {
				System.err.println("Interruption of blocking queue. Shouldnt happen. Fatal error.");
			}
			return;
		}
		
	}

	

}
