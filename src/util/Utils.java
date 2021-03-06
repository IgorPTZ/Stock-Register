package util;

import java.io.InputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class Utils {
	
	@SuppressWarnings("unused")
	public static boolean isNumerico(String texto) {
		
		if(texto == null) {
			
			return false;
		}
		
		try {
			
			double numero = Double.parseDouble(texto);
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	public static byte[] converterDeStreamParaByte(InputStream arquivo) {
		
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			int read = arquivo.read();
			
			while(read != - 1) {
				
				outputStream.write(read);
				read = arquivo.read();
			}
			
			outputStream.close();
			
			return outputStream.toByteArray();
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
