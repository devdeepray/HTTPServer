import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class IOUtils {
	
	public static String readLineFromStreamReader(BufferedInputStream bis) throws IOException{
		
		String tmpstr = new String();
		boolean flag = false;
		while(true){

			int tmp = bis.read();
			if(tmp == -1) break;
			if(tmp == (int)'\n'){
				
				if(tmpstr.charAt(tmpstr.length()-1)=='\r')
					tmpstr = tmpstr.substring(0, tmpstr.length()-1);
				break;
			}
			
			tmpstr += (char)tmp;
			flag = true;
		}
		
		if(flag) return tmpstr;
		else return null;
		
	}

	public static Byte[] b2B(byte[] bodytmp) {
		// TODO Auto-generated method stub
		Byte[] tmp = new Byte[bodytmp.length];
		int i = 0;
		for(byte x : bodytmp){
			tmp[i++] = x;
		}
			
		return tmp;
	}

	public static byte[] B2b(Byte[] body) {
		// TODO Auto-generated method stub
		byte[] tmp = new byte[body.length];
		int i = 0;
		for(byte x : body){
			tmp[i++] = x;
		}
			
		return tmp;
	}

	public static char[] B2c(Byte[] body) {
		// TODO Auto-generated method stub
		char[] tmp = new char[body.length];
		int i = 0;
		for(Byte x : body){
			tmp[i++] = (char)(byte)x;
		}
		return null;
	}
	
}
