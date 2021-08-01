package ratelimiter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ratelimiter.controller.ProductServiceController;

@SpringBootTest
class RateLimiterApplicationTests {

	private MockMvc mvc;

	@Autowired
	ProductServiceController productServiceController;

	@Test
	void loadTesting() throws Exception {
		mvc.perform(get("/products").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		Mockito.verify(Mockito.times(2));
	}

}
