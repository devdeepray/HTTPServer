import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;


public class HTTPSenderRunnable implements Runnable{

	HTTPProcessorRunnable hpt;
	BufferedOutputStream bos;
	private static int debugCode = 0xF;
	
	public HTTPSenderRunnable(BufferedOutputStream bos, HTTPProcessorRunnable hpt) {
		this.hpt = hpt;
		this.bos = bos;
	}

	public void run() {
		Debug.print("Sender pipe starting", debugCode );
		try {
			do{
				HTTPObject tmp = hpt.respMessages.take();
				if(tmp.header == null)
					break;
				HTTPSenderUtils.send(tmp, bos);
			}while(ServerSettings.isKeepAlive());
		} catch ( Exception e) {
			
		}
		System.err.println("Send thread exiting");
		return;
	}

}
