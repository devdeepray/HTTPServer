import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;


public class HTTPSenderThread extends Thread{

	HTTPProcessorThread hpt;
	BufferedOutputStream bos;
	boolean keepalive;
	
	public HTTPSenderThread(BufferedOutputStream bos, HTTPProcessorThread hpt) {
		this.hpt = hpt;
		this.bos = bos;
	}

	public void run() {
		try {
			do{
				HTTPSenderUtils.send(hpt.respMessages.take(), bos);
			}while(keepalive);
		} catch (IOException | InterruptedException e) {
			
		}
		System.err.println("Send thread exiting");
		return;
		
	}

}
