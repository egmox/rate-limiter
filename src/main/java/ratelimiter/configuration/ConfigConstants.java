package ratelimiter.configuration;

import java.util.Calendar;

public class ConfigConstants {
	
	// Fixed configurations
	public static final int TIME_WINDOW_SIZE = Calendar.MINUTE;
	public static final Long REQUEST_LIMIT = 3l;
	private static final Integer HIGH_REQUESTS_PERCENTAGE = 10;
	public static Boolean HIGH_TIME_ENABLED = false;
	public static final int HIGH_TIME_START = 9;
	public static final int HIGH_TIME_END = 11;
	
	// Dynamic configurations
	public static final Long HIGH_TIME_REQUEST_LIMIT = ((REQUEST_LIMIT * HIGH_REQUESTS_PERCENTAGE / 100 == 0) ? 2
			: REQUEST_LIMIT * HIGH_REQUESTS_PERCENTAGE / 100) + REQUEST_LIMIT;
}
