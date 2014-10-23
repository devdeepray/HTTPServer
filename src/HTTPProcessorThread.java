import java.util.concurrent.LinkedBlockingQueue;


public class HTTPProcessorThread extends Thread{

	HTTPReceiverThread hrt;
	LinkedBlockingQueue<HTTPObject> respMessages;
	boolean keepalive;
	
	public HTTPProcessorThread(HTTPReceiverThread hrt) {
		
		this.hrt = hrt;
		respMessages = new LinkedBlockingQueue<HTTPObject>();
	}

	public void run(){
		try{

			do{
				
				respMessages.put(HTTPRequestProcessor.getResponse(hrt.recMessages.take()));
			}while(keepalive);
		}
		
		catch (Exception e){
		}
	
		System.err.println("Processor thread exiting");
		return;
		
	}

}
