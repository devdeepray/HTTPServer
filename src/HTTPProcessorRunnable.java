import java.util.concurrent.LinkedBlockingQueue;


public class HTTPProcessorRunnable implements Runnable{

	HTTPReceiverRunnable hrt;
	LinkedBlockingQueue<HTTPObject> respMessages;
	private static int debugCode = 0xF;
	
	public HTTPProcessorRunnable(HTTPReceiverRunnable hrt) {
		
		this.hrt = hrt;
		respMessages = new LinkedBlockingQueue<HTTPObject>();
	}

	public void run(){
		Debug.print("Start processor pipe", debugCode);
		try{

			do{
				HTTPObject recMessage = hrt.recMessages.take();
				if(recMessage.equals(HTTPObject.nullHTTPObject()))
					break;

				Debug.print("Start processing message", debugCode);
				respMessages.put(HTTPRequestProcessor.getResponse(recMessage));
				Debug.print("End processing message", debugCode);
			}while(ServerSettings.isKeepAlive());
		}
		
		catch (Exception e){
		}
	
		Debug.print("Processor pipe exiting", debugCode);
		try {
			respMessages.put(HTTPObject.nullHTTPObject());
		} catch (InterruptedException e) {
			System.err.println("Interruption of blocking queue. Fatal error.");
		}
		return;
		
	}

}
