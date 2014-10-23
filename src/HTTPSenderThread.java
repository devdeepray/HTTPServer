import java.io.BufferedOutputStream;
import java.io.IOException;


public class HTTPSenderThread extends Thread{

	HTTPProcessorThread hpt;
	BufferedOutputStream bos;
	
	public HTTPSenderThread(BufferedOutputStream bos, HTTPProcessorThread hpt) {
		this.hpt = hpt;
		this.bos = bos;
	}

	public void run() {
		try {
			while(true)
				HTTPSenderUtils.send(hpt.respMessages.take(), bos);
		} catch (IOException | InterruptedException e) {
			
		}
		System.err.println("Send thread exiting");
		return;
		
	}

}
