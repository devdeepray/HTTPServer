import java.io.IOException;
import java.util.HashMap;


public class CGIHeader extends HTTPHeader{
		CGIHeader(String header) throws IOException{
		
		// Code for parsing GET, etc
		attributes = new HashMap<String, String>();
		
		String [] headerLines = header.split("\r?\n");
		for(int i = 1; i < headerLines.length; ++i){
			String []tokns = headerLines[i].split(":");
			if(tokns.length != 2) continue;
			attributes.put(tokns[0].trim().toLowerCase(), tokns[1].trim());
		}
		
	}

}
