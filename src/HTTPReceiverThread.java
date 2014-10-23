import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.*;


public class HTTPReceiverThread extends Thread{

	LinkedBlockingQueue<HTTPObject> recMessages;
	BufferedInputStream bis;
	
	
	
	public HTTPReceiverThread(BufferedInputStream bis) {
		super();
		recMessages = new LinkedBlockingQueue<HTTPObject>();
		this.bis = bis;
	}
	
	public void run()
	{
		try {
			while(true){
				System.err.println("Starting to receive message");
				recMessages.put(HTTPReceiverUtils.receive(bis));
				System.err.println("End receiving message");
			}
		} catch (IOException | InterruptedException e) {
			
		}
		System.err.println("Receiver thread exiting");
		return;
	}

	

}
