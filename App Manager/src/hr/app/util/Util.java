package hr.app.util;

public class Util {

	public static String prepareString(String string) {
		if(string.equals("")) return "NULL";
		else return "'" + string + "'";
	}
	
	public static String prepareBoolean(boolean bool) {
		if(bool)
			return "TRUE";
		else 
			return "FALSE";
	}
	
	public static String prepareToParseMoney(String string) {
		if(string.equals("")) return "0";
		else return string;
	}
	
	public static String standardErrorBody() {
		return "Provjerite jesu li svi podatci uneseni u ispravnom formatu";
	}
	
	public static String standardErrorTitle() {
		return "Greška";
	}
}
