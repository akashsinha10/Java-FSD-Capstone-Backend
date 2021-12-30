package com.dell.webservice.controller;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dell.webservice.entity.User;
import com.dell.webservice.interfaces.UserRepository;
import com.dell.webservice.repository.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@DisplayName("User Controller Test")
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private UserService userService;
	
	User user1 = new User("Tanuja","abcd","Tanuja.Lavu@dell.com","admin",10000.57,true);
	User user2 = new User("Avilasa","pqr","Avilasa.Das@dell.com","admin",10000.57,true);
	User user3 = new User("Alisha","mnb","Alisha.Panda@dell.com","admin",10000.57,true);
	
	@Test
	public void getEntityUsers_withoutfilters_success()throws Exception {
		
		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		
		Mockito.when(userService.getEntityUsers(0, 10, "id")).thenReturn(userList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/user/getusers").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Get all users without filters");
		System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"id\":0,\"username\":\"Tanuja\",\"password\":\"abcd\",\"email\":\"Tanuja.Lavu@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true},{\"id\":0,\"username\":\"Avilasa\",\"password\":\"pqr\",\"email\":\"Avilasa.Das@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true},{\"id\":0,\"username\":\"Alisha\",\"password\":\"mnb\",\"email\":\"Alisha.Panda@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}]";
		assertEquals(expected,result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		
	}
	
	@Test
	public void getUser_success()throws Exception {
		
		Mockito.when(userService.getEntityUser(user1.getId())).thenReturn(java.util.Optional.of(user1));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/user/getuser/"+user1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		String expected = "{\"id\":0,\"username\":\"Tanuja\",\"password\":\"abcd\",\"email\":\"Tanuja.Lavu@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void getUser_fail()throws Exception {
		
		Mockito.when(userService.getEntityUser(user1.getId())).thenReturn(null);
		System.out.println(user1.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/user/getuser/"+user1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id fail");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(404,result.getResponse().getStatus());
		String expected = "User does not exist with id "+user1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addUser_success()throws Exception {
		UserService us = mock(UserService.class);
		doNothing().when(us).addEntityUser(user2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/user/adduser/").accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(user2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(201,result.getResponse().getStatus());
		String expected = "{\"id\":0,\"username\":\"Avilasa\",\"password\":\"pqr\",\"email\":\"Avilasa.Das@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addUser_fail()throws Exception {
		UserService us = mock(UserService.class);
		doNothing().when(us).addEntityUser(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/user/adduser/").accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add fail bad request");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Add User request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateUser_success()throws Exception {
		
		UserService us = mock(UserService.class);
		Mockito.when(userService.getEntityUser(user2.getId())).thenReturn(java.util.Optional.of(user2));
		doNothing().when(us).updateEntityUser(user2);
		user2.setRole("user");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/user/updateuser/"+user2.getId()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(user2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void updateUser_fail()throws Exception {
		
		UserService us = mock(UserService.class);
		doNothing().when(us).updateEntityUser(null);
		user2.setRole("user");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/user/updateuser/"+user2.getId()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail bad request");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Update User request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateUser_fail_notfound()throws Exception {
		
		Mockito.when(userService.getEntityUser(user2.getId())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/user/updateuser/"+user2.getId()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(user2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail not found");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "User does not exist with id "+user2.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateUser_fail_idmismatch()throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/user/updateuser/3").accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(user2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail id mismatch");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Id in request path and request body do not match";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void deleteUser_success()throws Exception {
		
		UserService us = mock(UserService.class);
		Mockito.when(userService.getEntityUser(user2.getId())).thenReturn(java.util.Optional.of(user2));
		doNothing().when(us).deleteEntityUser(user2.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/user/deleteuser/"+user2.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void deleteUser_fail()throws Exception {
		
		UserService us = mock(UserService.class);
		Mockito.when(userService.getEntityUser(user2.getId())).thenReturn(null);
		doNothing().when(us).deleteEntityUser(user2.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/user/deleteuser/"+user2.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "User does not exist with id "+user2.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	

}
