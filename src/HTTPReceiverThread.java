import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.concurrent.*;


public class HTTPReceiverThread extends Thread{

	LinkedBlockingQueue<HTTPObject> recMessages;
	BufferedInputStream bis;
	
	
	
	public HTTPReceiverThread(BufferedInputStream bis) {
		super();
		recMessages = new LinkedBlockingQueue();
		this.bis = bis;
	}
	
	public void run()
	{
		try {
			while(true)
				recMessages.put(HTTPReceiverUtils.receive(bis));
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	

}
