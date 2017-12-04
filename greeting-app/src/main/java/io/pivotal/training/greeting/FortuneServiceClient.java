package io.pivotal.training.greeting;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class FortuneServiceClient {
	private RestTemplate restTemplate;

	private final Logger logger = LoggerFactory
			.getLogger(FortuneServiceClient.class);

	public FortuneServiceClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(fallbackMethod = "defaultFortune")
	public String getFortune() {
		@SuppressWarnings("unchecked")
		Map<String, String> result = restTemplate.getForObject(
				"http://fortune/", Map.class);
		String fortune = result.get("fortune");
		logger.info("received fortune '{}'", fortune);
		return fortune;
	}

	public String defaultFortune() {
		logger.info("Default fortune used.");
		return "Your future is uncertain";
	}
}