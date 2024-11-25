package com.saiteja.stripe_payment;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StripePaymentApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void testCheckoutProducts() {
		String requestBody = """
            {
                "amount": 10000,
                "quantity": 1,
                "currency": "USD",
                "name": "Mobiles"
            }
        """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/product/v1/checkout")
				.then()
				.statusCode(200) // Validates the HTTP status
				.body("status", Matchers.notNullValue())          // Validates 'status' in the response
				.body("message", Matchers.notNullValue())         // Validates 'message' in the response
				.body("sessionId", Matchers.notNullValue())       // Validates 'sessionId' in the response
				.body("sessionUrl", Matchers.startsWith("http")); // Validates 'sessionUrl' starts with "http"
	}
}
