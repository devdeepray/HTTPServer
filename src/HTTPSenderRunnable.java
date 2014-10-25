import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;


public class HTTPSenderRunnable implements Runnable{

	HTTPProcessorRunnable hpt;
	OutputStream os;
	private static int debugCode = 0xF;
	
	public HTTPSenderRunnable(OutputStream os, HTTPProcessorRunnable hpt) {
		this.hpt = hpt;
		this.os = os;
	}

	public void run() {
		Debug.print("Sender pipe starting", debugCode );
		try {
			do{
				HTTPObject tmp = hpt.respMessages.take();
				if(tmp.header == null)
					break;
				HTTPSenderUtils.send(tmp, os);
			}while(ServerSettings.isKeepAlive());
		} catch ( Exception e) {
			
		}
		System.err.println("Send thread exiting");
		return;
	}

}
