import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.*;


public class HTTPReceiverThread extends Thread{

	LinkedBlockingQueue<HTTPObject> recMessages;
	BufferedInputStream bis;
	boolean keepalive;
	
	
	public HTTPReceiverThread(BufferedInputStream bis) {
		super();
		recMessages = new LinkedBlockingQueue<HTTPObject>();
		this.bis = bis;
	}
	
	public void run()
	{
		try {
			do{
				System.err.println("Starting to receive message");
				recMessages.put(HTTPReceiverUtils.receive(bis));
				System.err.println("End receiving message");
			}while(keepalive);
		} catch (IOException | InterruptedException e) {
		}
		System.err.println("Receiver thread exiting");
		return;
	}

	

}
