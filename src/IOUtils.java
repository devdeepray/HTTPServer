/**
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is a collection of some IO utils like a custom reader, 
 * conversion of Byte->byte and viceversa in arrays, etc 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;


public class IOUtils
{
	
	public static String readLineFromStreamReader(InputStream is) throws IOException
	{	
		String tmpstr = new String();
		boolean flag = false; // For atleast one character
		while(true)
		{
			int tmp = is.read();
			if(tmp == -1) break; // End of stream
			if(tmp == (int)'\n') // New line
			{
				if(tmpstr.length()>0 && tmpstr.charAt(tmpstr.length()-1)=='\r') // Remove prev if it is \r
				{
					tmpstr = tmpstr.substring(0, tmpstr.length()-1);
				}
				break;
			}
			tmpstr += (char)tmp;
			flag = true;
		}
		if(flag)
		{
			return tmpstr; // Atleast one character
		}
		else 
		{
			return null; // No characters
		}
	}

	public static Byte[] b2B(byte[] bodytmp)  // Convert byte[] to Byte[]
	{
		Byte[] tmp = new Byte[bodytmp.length];
		int i = 0;
		for(byte x : bodytmp)
		{
			tmp[i++] = x;
		}
		return tmp;
	}

	public static byte[] B2b(Byte[] body)  // Convert Byte[] to byte[]
	{
		byte[] tmp = new byte[body.length];
		int i = 0;
		for(byte x : body)
		{
			tmp[i++] = x;
		}
		return tmp;
	}

	public static char[] B2c(Byte[] body) // Convert Byte[] to char[] 
	{
		char[] tmp = new char[body.length];
		int i = 0;
		for(Byte x : body)
		{
			tmp[i++] = (char)(byte)x;
		}
		return tmp;
	}

	public static Byte[] c2B(char[] bodytmp) // Convert char[] to Byte[]
	{
		Byte[] tmp = new Byte[bodytmp.length];
		int i = 0;
		for(char x : bodytmp)
		{
			tmp[i++] = (byte)x;
		}
		return tmp;
	}

	public static boolean caseIgnoreEqual(String string1, String string2) // Compares ignoring case 
	{
		if(string1 == null || string2 == null) return false;
		return string1.trim().toLowerCase().equals(string2.trim().toLowerCase());
	}

	public static Byte[] compressGzip(Byte[] data) throws IOException
	{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gs = new GZIPOutputStream(baos);
		gs.write(B2b(data));
		gs.finish();
		gs.flush();
		return b2B(baos.toByteArray());
	}

	public static Byte[] compressDeflate(Byte[] data) throws IOException 
	{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DeflaterOutputStream dos = new DeflaterOutputStream(baos);
		dos.write(B2b(data));
		dos.finish();
		dos.flush();
		return b2B(baos.toByteArray());
	}

	public static Byte[] encode(Byte[] page, String supportedEncoding) throws IOException
	{
		if(IOUtils.caseIgnoreEqual(supportedEncoding, "gzip")) // Encode as gzip
			return compressGzip(page); 
		if(IOUtils.caseIgnoreEqual(supportedEncoding, "deflate")) // Encode as deflate
			return compressDeflate(page);
		return null;
	}
	
}
