package util;

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
}
