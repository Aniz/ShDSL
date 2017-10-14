package {{systemName|lower}}.smarthome.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class Utils {

	private static Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
	
	private Utils(){}
	
	public static int getHourOfDay(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

}
