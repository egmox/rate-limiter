package ratelimiter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import ratelimiter.configuration.ConfigConstants;
import ratelimiter.configuration.URIConstants;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class RateLimiterApplicationTests {

	private RequestPostProcessor postProcessor;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void loadTestingRequestLimit() throws Exception {
		postProcessor = request -> {
			request.setRemoteAddr(URIConstants.HOST);
			return request;
		};
		for (int i = 0; i < ConfigConstants.REQUEST_LIMIT; i++) {
			mvc.perform(get(URIConstants.CONTROLLER_URI).with(postProcessor)).andExpect(status().isOk());
		}
		mvc.perform(get(URIConstants.CONTROLLER_URI)).andExpect(status().is(HttpStatus.TOO_MANY_REQUESTS.value()));
	}

}
