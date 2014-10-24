
import java.io.IOException;
import java.util.HashMap;


public class HTTPHeader {
	
	// Structured object to store a http header
	public HashMap<String, String> attributes;
	public String version;
	public String param;
	public String action;
	
	HTTPHeader(){
		
		attributes = new HashMap<String, String>();
		version = new String();
		param = new String();
		action = new String();
	}
	
	@SuppressWarnings("unchecked")
	HTTPHeader(HTTPHeader hdr){
		
		// Copy constructor
		this.attributes = (HashMap<String, String>) hdr.attributes.clone();
		this.version = hdr.version;
		this.param = hdr.param;
		this.action = hdr.action;
	}
	
	HTTPHeader(String header) throws IOException{
		
		// Code for parsing GET, etc
		attributes = new HashMap<String, String>();
		
		String [] headerLines = header.split("\r?\n");
		String [] firstLineFields = headerLines[0].split(" ");
		if(firstLineFields.length != 3) throw new IOException();
		
		version = firstLineFields[2].trim(); // Version eg HTTP/1.0
		action = firstLineFields[0].trim(); // Action = GET, HEAD, POST, etc
		param = firstLineFields[1].trim(); // Parameter for action. eg URI, etc
		
		for(int i = 1; i < headerLines.length; ++i){
			
			String []tokns = headerLines[i].split(":");
			if(tokns.length != 2) continue;
			attributes.put(tokns[0].trim().toLowerCase(), tokns[1].trim());
		}
		
	}
	
}
