package com.appsdeveloperblog.app.ws.shared;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateUserId() {
		String userId1 = utils.generateUserId(30);
		String userId2 = utils.generateUserId(30);

		assertNotNull(userId1);
		assertNotNull(userId2);

		assertTrue(userId1.length() == 30);
		assertTrue(!userId1.equalsIgnoreCase(userId2));
	}

	@Test
	void testHasTokenNotExpired() {
		String token = utils.generateEmailVerificationToken(anyString());

		boolean hasTokenExpired = Utils.hasTokenExpired(token);

		assertFalse(hasTokenExpired);
	}

	@Test
	void testHasTokenExpired() {
		String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NDQ3MTI2NjB9.LGNdo4jdj1kwvnvxlOeC7PyM2rSnjpVQvB2hY6THWFzHYPP10nU7wpaJ3P60U1l9MiZ7XmWLEE-CymjZfsa_Kg";

		boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);

		assertTrue(hasTokenExpired);
	}
}
