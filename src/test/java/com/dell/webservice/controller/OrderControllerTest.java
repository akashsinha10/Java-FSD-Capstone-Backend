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


import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dell.webservice.entity.Order;
import com.dell.webservice.entity.Product;
import com.dell.webservice.entity.User;
import com.dell.webservice.repository.OrderService;
import com.dell.webservice.repository.ProductService;
import com.dell.webservice.repository.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OrderController.class)
@DisplayName("Order Controller Test")
public class OrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private UserService userService;
	User user = new User("Alisha","mnb","Alisha.Panda@dell.com","admin",10000.57,true);
	Product prod1 = new Product("Chicken Biryani",250.0,"250/- per plate","Mughlai","../../../assets/images/ChickenBiryani.jpg","../../../assets/images/Mughlai.jpg","Golden Spoon");
	Product prod2 = new Product("Chicken Enchiladas",190.0,"190/- per plate","Mexican","../../../assets/images/Enchiladas.jpg","../../../assets/images/Mexican.jpg","Qdoba");
	Product prod3 = new Product("Veg Farmhouse Pizza",200.0,"200/- per plate","Italian","../../../assets/images/VegPizza.jpg","../../../assets/images/Italian.jpg","Dominos");
	
	
	@Test
	public void getEntityProducts_withoutfilters_success()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		prodList.add(prod3);
		Order order2 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		List<Order> orderList = new ArrayList<>();
		orderList.add(order1);
		orderList.add(order2);
		
		Mockito.when(orderService.getEntityOrders(0, 10, "id",null)).thenReturn(orderList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/order/getordersdata").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Get all orders without filters");
		System.out.println(result.getResponse().getContentAsString());
		//String expected = "[{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T15:57:06.520+00:00\"}]";
		//assertEquals(expected,result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		
	}
	
	@Test
	public void getOrder_success()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(java.util.Optional.of(order1));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/order/getorder/"+order1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T16:00:04.112+00:00\"}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void getOrder_fail()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/order/getorder/"+order1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id fail");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Order does not exist with id "+order1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addOrder_success()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		doNothing().when(us).addEntityOrder(order1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/order/addorder?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(order1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(201,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"username\":\"Avilasa\",\"password\":\"pqr\",\"email\":\"Avilasa.Das@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addOrder_fail()throws Exception {
		
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		doNothing().when(us).addEntityOrder(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/order/addorder?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add fail bad request");
		System.out.println(result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Add Order request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateOrder_success()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(java.util.Optional.of(order1));
		doNothing().when(us).updateEntityOrder(order1);
		order1.setAddress("abcd apartments");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/order/updateorder/"+order1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(order1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void updateOrder_fail()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		doNothing().when(us).updateEntityOrder(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/order/updateorder/"+order1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail bad request");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Update Order request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateOrder_fail_notfound()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/order/updateorder/"+order1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(order1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail not found");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Order does not exist with id "+order1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateOrder_fail_idmismatch()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/order/updateorder/3"+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(order1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail id mismatch");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Id in request path and request body do not match";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void deleteOrder_success()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(java.util.Optional.of(order1));
		doNothing().when(us).deleteEntityOrder(order1.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/order/deleteorder/"+order1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void deleteOrder_fail()throws Exception {
		List<Product> prodList = new ArrayList<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Order order1 = new Order(0.0,"abc@gmail.com","xyz apartment","9876543212",prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		OrderService us = mock(OrderService.class);
		Mockito.when(orderService.getEntityOrder(order1.getId())).thenReturn(null);
		doNothing().when(us).deleteEntityOrder(order1.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/order/deleteorder/"+order1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Order does not exist with id "+order1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	

}
