package eu.euipo.tools.em.utils;

import eu.euipo.tools.em.persistence.model.CurrentTaskVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static int getCurrentDay() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	public static int getCurrentMonth() {		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public static int getCurrentYear() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public static Date getDateFromString(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(strDate, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getDateFromStringUSA(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(strDate, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static String getCurrentYear(Date date) {
		// create a calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// get the value of all the calendar date fields.
		return String.valueOf(cal.get(Calendar.YEAR));


	}

	public static String getCurrentMonth(Date date) {
		// create a calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// get the value of all the calendar date fields.
		return String.valueOf(cal.get(Calendar.MONTH));
	}

	public static String getCurrentDay(Date date) {
		// create a calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// get the value of all the calendar date fields.
		return String.valueOf(cal.get(Calendar.DATE));
	}
}
