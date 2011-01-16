package com.ttime.parse;

import java.text.ParseException;

public class ParserUtil {
	public static int dayLetterToNumber(Character day_letter) {
		return (day_letter - 'א');
	}

	/**
	 * @param s
	 *            Time string in the form HH.MM, 24-hour, HH may be single-digit
	 *            (MM may not)
	 * @return Seconds from midnight to s
	 * @throws ParseException
	 */
	public static int parseTime(String s) throws ParseException {
		try {
			String bits[] = reverse(s).split("\\.");
			if (bits.length != 2) {
				throw new ParseException("", 0);
			}
			int seconds = Integer.valueOf(bits[0]) * 3600
					+ Integer.valueOf(bits[1]) * 60;
			return seconds;
		} catch (Exception e) {
			throw new ParseException("Could not parse time", 0);
		}
	}

	/**
	 * Reverse a string, dropping leading 0s
	 *
	 * @param s
	 *            string to reverse
	 * @return s, reversed.
	 */
	public static String reverse(String s) {
		String result = new StringBuffer(s).reverse().toString();
		int i;

		for (i = 0; i < result.length(); i++) {
			if (result.charAt(i) != '0') {
				break;
			}
		}

		return result.substring(i);
	}
}
