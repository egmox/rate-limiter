package ratelimiter.configuration;

import java.util.Calendar;

public class ConfigConstants {
	public static final int TIME_WINDOW_SIZE = Calendar.MINUTE;
	public static final Long REQUEST_LIMIT = 3l;
	public static final Integer THROTLLING_PERCENTAGE = 10;
	public static final Boolean THROTLLING_ENABLED = false;
	public static final Long THROTLLING_REQUEST_LIMIT = ((REQUEST_LIMIT * THROTLLING_PERCENTAGE / 100 == 0) ? 2
			: REQUEST_LIMIT * THROTLLING_PERCENTAGE / 100) + REQUEST_LIMIT;
}
