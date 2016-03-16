package com.example.app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.example.app.entity.Authentication;
import com.example.app.test.TestBase;

public class LoginResourceTest extends TestBase {

	@Test
	public void login() throws Exception {
		String username = "jane";
		String password = "best";

		Login.LoginForm loginForm = new Login.LoginForm();
		loginForm.setUsername(username);
		loginForm.setPassword(password);

		Authentication authentication = instance(LoginResource.class).login(loginForm);
		assertNotNull(authentication.getToken());
		assertEquals(username, authentication.getUser().getUsername());
	}

	@Test
	public void loginWrongPassword() throws Exception {
		String username = "jane";
		String password = "wrong-pw";

		Login.LoginForm loginForm = new Login.LoginForm();
		loginForm.setUsername(username);
		loginForm.setPassword(password);

		Authentication authentication = instance(LoginResource.class).login(loginForm);
		assertNull(authentication);
	}

}
