package org.indyoracle.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingHelper {
	public static void log(String txt) {
		Date now = new Date();
		String nowString = new SimpleDateFormat("hh:mm:ss").format(now);
		
		System.out.println(nowString + " : " + txt);
	}
}
