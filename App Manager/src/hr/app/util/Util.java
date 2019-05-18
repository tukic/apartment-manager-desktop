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
}
