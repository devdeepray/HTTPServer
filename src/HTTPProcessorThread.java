import java.util.concurrent.LinkedBlockingQueue;


public class HTTPProcessorThread extends Thread{

	HTTPReceiverThread hrt;
	LinkedBlockingQueue<HTTPObject> respMessages;
	
	public HTTPProcessorThread(HTTPReceiverThread hrt) {
		
		this.hrt = hrt;
		respMessages = new LinkedBlockingQueue<HTTPObject>();
	}

	public void run(){
		try{

			while(true){
				
				respMessages.put(HTTPRequestProcessor.getResponse(hrt.recMessages.take()));
			}
			
		}
		
		catch (Exception e){
			
			e.printStackTrace();
			return;
		}
		
	}

}
