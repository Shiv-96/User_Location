package com.location.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.Entity.User;
import com.location.Entity.UserType;
import com.location.Service.UserService;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private User reuestUser;
	private User responseUser;

	@BeforeEach
	public void init() {

		reuestUser = new User();
		reuestUser.setEmail("shiv@gmail.com");
		reuestUser.setLatitude(102.00);
		reuestUser.setLongitude(14.02);
		reuestUser.setName("Shiv");
		reuestUser.setUserType(UserType.ADMIN);

		responseUser = new User();
		responseUser.setUserId(15);
		responseUser.setEmail("shiv@gmail.com");
		responseUser.setLatitude(102.00);
		responseUser.setLongitude(14.02);
		responseUser.setName("Shiv");
		responseUser.setUserType(UserType.ADMIN);

	}

	@Test
	@DisplayName("Methos working fine")
	public void testAddTheUser_withValidDetails() throws Exception {

		Mockito.when(userService.addTheUser(any(User.class))).thenReturn(responseUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add_data")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(reuestUser));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String responseBodyAsString = mvcResult.getResponse().getContentAsString();

		User registeredUser = new ObjectMapper().readValue(responseBodyAsString, User.class);

		assertEquals(responseUser.getEmail(), registeredUser.getEmail(), "Return object is not correct");

		assertNotNull(registeredUser.getUserId(), "Registered userid should not be empty");

	}
	
	
	@Test
	@DisplayName("Methos working fine")
	public void testAddTheUser_withNullNameCheck() throws Exception {

		Mockito.when(userService.addTheUser(any(User.class))).thenReturn(responseUser);
		
		reuestUser.setName(null);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add_data")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(reuestUser));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "Http status code is not sent to 400");

	}

}
