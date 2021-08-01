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

//	public synchronized CompletableFuture<Boolean> addRequest() throws Exception {
//		windowStart = Calendar.getInstance();
//		windowStart.add(ConfigConstants.TIME_WINDOW_SIZE, -1);
//		windowStartEpoch = windowStart.getTimeInMillis();
//
//		CompletableFuture<Boolean> future;
//		future = CompletableFuture.runAsync(() -> cleanIfWindowOld())
//		.thenRunAsync(() -> emptyWindow())
//		.;
//		System.out.println("request queue size: " + requests.size());
//		System.out.println(future.get());
//		return future;
//	}

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
		return ConfigConstants.THROTLLING_ENABLED ? ConfigConstants.THROTLLING_REQUEST_LIMIT
				: ConfigConstants.REQUEST_LIMIT;
	}

}
