package ratelimiter.limiter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitVerifierInterceptor implements HandlerInterceptor {

	@Autowired
	RequestService requestService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!requestService.addRequest()) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			return false;
		}
		return true;
	}
}