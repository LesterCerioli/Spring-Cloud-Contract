package com.example;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.web.client.RestTemplate;



/**
 * @author Marcin Grzejszczak
 */
@SpringBootTest
//@org.junit.jupiter.api.Disabled
public class GenerateStubsTest {

	@RegisterExtension
	static StubRunnerExtension rule = new StubRunnerExtension()
			.downloadStub("com.example","beer-api-producer-latest", "0.0.1-SNAPSHOT")
			.repoRoot("stubs://file://" + System.getenv("ROOT") + "/producer_with_latest_2_2_features/src/test/resources/contracts/beer/in_progress")
			.stubsMode(StubRunnerProperties.StubsMode.REMOTE)
			.withGenerateStubs(true);

	
	@Test public void should_generate_a_stub_at_runtime() throws Exception {
		
		int port = rule.findStubUrl("beer-api-producer-latest").getPort();

		String object = new RestTemplate().getForObject("http://localhost:" + port + "/stuff", String.class);

		BDDAssertions.then(object).isEqualTo("OK");
		
	}
	
}
