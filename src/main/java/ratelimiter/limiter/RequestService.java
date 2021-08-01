package ratelimiter.limiter;

import java.util.Calendar;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import ratelimiter.configuration.ConfigConstants;

@Service
public class RequestService {
	static LinkedList<Long> requests = new LinkedList<>();
	static Calendar windowStart = null;
	static Long windowStartEpoch = null;

	public synchronized boolean addRequest() {
		windowStart = Calendar.getInstance();
		windowStart.add(ConfigConstants.TIME_WINDOW_SIZE, -1);
		windowStartEpoch = windowStart.getTimeInMillis();
		updateThrotlling();
		resetIfWindowOld();
		discardOldRequests();
		return checkAndAdd();
	}

	private void resetIfWindowOld() {
		if (requests.size() > 0 && requests.getLast() <= windowStartEpoch) {
			requests.removeAll(requests);
		}
	}

	private void discardOldRequests() {
		while (requests.size() > 0 && requests.getFirst() <= windowStartEpoch) {
			requests.removeFirst();
		}
	}

	private void updateThrotlling() {

	}

	private boolean checkAndAdd() {
		if (requests.size() >= getRequestLimit())
			return false;
		requests.addLast(Calendar.getInstance().getTimeInMillis());
		return true;
	}

	private Long getRequestLimit() {
//		setHighTime();
		return ConfigConstants.HIGH_TIME_ENABLED ? ConfigConstants.HIGH_TIME_REQUEST_LIMIT
				: ConfigConstants.REQUEST_LIMIT;
	}

//	private void setHighTime() {
//		if (Calendar.HOUR_OF_DAY >= ConfigConstants.HIGH_TIME_START
//				&& Calendar.HOUR_OF_DAY <= ConfigConstants.HIGH_TIME_END) {
//			ConfigConstants.HIGH_TIME_ENABLED = true;
//		}
//		ConfigConstants.HIGH_TIME_ENABLED = false;
//	}

}
