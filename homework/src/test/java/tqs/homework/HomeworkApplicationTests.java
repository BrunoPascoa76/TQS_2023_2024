package tqs.homework;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.homework.data.User;
import tqs.homework.services.AuthenticationService;

@SpringBootTest
@AutoConfigureMockMvc
class HomeworkApplicationTests {

	@Autowired
	private MockMvc mock;

	@MockBean
	private AuthenticationService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testLoginSuccessful() throws JsonProcessingException{
		User user=new User("Bruno","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		Mockito.when(service.login(Mockito.any(),Mockito.any())).thenReturn(user);
		RestAssuredMockMvc.given()
		.mockMvc(mock)
		.contentType(MediaType.APPLICATION_JSON)
		.body(mapper.writeValueAsString(user))
		.when()
		.post("/api/login")
		.then()
		.statusCode(200);
	}

	@Test
	void testLoginWrongCredentials() throws JsonProcessingException{
		User user=new User("Rodrigo","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		Mockito.when(service.login(Mockito.any(),Mockito.any())).thenReturn(null);
		RestAssuredMockMvc.given()
		.mockMvc(mock)
		.contentType(MediaType.APPLICATION_JSON)
		.body(mapper.writeValueAsString(user))
		.when()
		.post("/api/login")
		.then()
		.statusCode(401);
	}

	@Test
	void testRegisterSuccessful() throws JsonProcessingException{
		User user=new User("João","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		Mockito.when(service.register(Mockito.any(),Mockito.any())).thenReturn(user);
		RestAssuredMockMvc.given()
		.mockMvc(mock)
		.contentType(MediaType.APPLICATION_JSON)
		.body(mapper.writeValueAsString(user))
		.when()
		.post("/api/register")
		.then()
		.statusCode(200);
	}

	@Test
	void testRegisterUsernameTaken() throws JsonProcessingException{
		User user=new User("Bruno","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		Mockito.when(service.register(Mockito.any(),Mockito.any())).thenReturn(null);
		RestAssuredMockMvc.given()
		.mockMvc(mock)
		.contentType(MediaType.APPLICATION_JSON)
		.body(mapper.writeValueAsString(user))
		.when()
		.post("/api/register")
		.then()
		.statusCode(409);
	}

	@Test
	void testLogout(){
		RestAssuredMockMvc.given()
		.mockMvc(mock)
		.when()
		.delete("/api/logout")
		.then()
		.statusCode(200);
	}

}
