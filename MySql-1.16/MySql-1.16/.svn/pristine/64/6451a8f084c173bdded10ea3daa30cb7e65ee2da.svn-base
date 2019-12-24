package com.hrms.common.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ahada
 *
 */
public class MiscUtils {
	private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * @return
	 */
	public static String getRandomAlphaNumeric() {
		StringBuffer sb = new StringBuffer(10);
		for (int i = 0; i < 10; i++) {
			int indx = (int) (Math.random() * MiscUtils.ALPHA_NUM.length());
			sb.append(MiscUtils.ALPHA_NUM.charAt(indx));
		}
		return sb.toString();
	}

	/**
	 * @param anString
	 * @return
	 */
	public static String trimLeadingAndTrailingComma(String anString) {
		if (anString == null) {
			return anString;
		}
		try {
			anString = anString.trim();
			if (anString.indexOf(',') == 0) {
				anString = anString.substring(1, anString.length());
			}
			if (anString.length() == anString.lastIndexOf(',') + 1) {
				anString = anString.substring(0, anString.lastIndexOf(',')).trim();
			}
			return anString;
		} catch (Exception e) {
			return anString.trim();
		}
	}

	/**
	 * @param timeString
	 * @return
	 */
	public static int get12HourTimeToMinutes(String timeString) {
		String time[] = timeString.split(" ");
		String timePart[] = time[0].split(":");
		int hours = Integer.parseInt(timePart[0]);
		int minutes = Integer.parseInt(timePart[1]);
		String amPM = time[1];

		int hoursToMinutes = 0;
		if (amPM.equalsIgnoreCase("AM")) {
			if (hours == 12) {
				hours = 0;
			}
			hoursToMinutes = hours * 60;
		} else if (amPM.equalsIgnoreCase("PM")) {
			if (hours == 12) {
				hours = 0;
			}
			hoursToMinutes = (12 + hours) * 60;
		}
		int totalMinutes = hoursToMinutes + minutes;
		return totalMinutes;
	}

	/**
	 * @param input
	 * @return
	 */
	public static boolean isFloat(String input) {
		try {
			Float.parseFloat(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * @param input
	 * @return
	 */
	public static boolean isNumber(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * @param date
	 * @return
	 */
	public static Timestamp timeStampConversion(Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
}
