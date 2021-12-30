package com.dell.webservice.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.dell.webservice.entity.Cart;
import com.dell.webservice.entity.Order;
import com.dell.webservice.entity.Product;
import com.dell.webservice.entity.User;
import com.dell.webservice.repository.CartService;
import com.dell.webservice.repository.OrderService;
import com.dell.webservice.repository.ProductService;
import com.dell.webservice.repository.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CartController.class)
@DisplayName("Cart Controller Test")
public class CartControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private CartService cartService;
	
	@MockBean
	private UserService userService;
	User user = new User("Alisha","mnb","Alisha.Panda@dell.com","admin",10000.57,true);
	Product prod1 = new Product("Chicken Biryani",250.0,"250/- per plate","Mughlai","../../../assets/images/ChickenBiryani.jpg","../../../assets/images/Mughlai.jpg","Golden Spoon");
	Product prod2 = new Product("Chicken Enchiladas",190.0,"190/- per plate","Mexican","../../../assets/images/Enchiladas.jpg","../../../assets/images/Mexican.jpg","Qdoba");
	Product prod3 = new Product("Veg Farmhouse Pizza",200.0,"200/- per plate","Italian","../../../assets/images/VegPizza.jpg","../../../assets/images/Italian.jpg","Dominos");
	
	
	@Test
	public void getEntityProducts_withoutfilters_success()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		prodList.add(prod3);
		Cart cart2 = new Cart(prodList,user);
		List<Cart> cartList = new ArrayList<>();
		cartList.add(cart1);
		cartList.add(cart1);
		
		Mockito.when(cartService.getEntityCarts(0, 10, "id",null)).thenReturn(cartList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/cart/getcarts").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Get all carts without filters");
		System.out.println(result.getResponse().getContentAsString());
		//String expected = "[{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T15:57:06.520+00:00\"}]";
		//assertEquals(expected,result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		
	}
	
	@Test
	public void getCart_success()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(java.util.Optional.of(cart1));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/cart/getcart/"+cart1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T16:00:04.112+00:00\"}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void getCart_fail()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/cart/getcart/"+cart1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id fail");
		System.out.println(result.getResponse().getStatus());
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Cart does not exist with id "+cart1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addCart_success()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		doNothing().when(us).addEntityCart(cart1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/cart/addcart?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(cart1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(201,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"username\":\"Avilasa\",\"password\":\"pqr\",\"email\":\"Avilasa.Das@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addCart_fail()throws Exception {
		
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		doNothing().when(us).addEntityCart(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/cart/addcart?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add fail bad request");
		System.out.println(result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Add Cart request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateCart_success()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		prodList.add(prod3);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(java.util.Optional.of(cart1));
		doNothing().when(us).updateEntityCart(cart1);
		cart1.setProducts(prodList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/cart/updatecart/"+cart1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(cart1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void updateCart_fail()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		doNothing().when(us).updateEntityCart(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/cart/updatecart/"+cart1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail bad request");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Update Cart request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateCart_fail_notfound()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/cart/updatecart/"+cart1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(cart1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail not found");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Cart does not exist with id "+cart1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateCart_fail_idmismatch()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/cart/updatecart/3"+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(cart1)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail id mismatch");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Id in request path and request body do not match";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void deleteCart_success()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(java.util.Optional.of(cart1));
		doNothing().when(us).deleteEntityCart(cart1.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/cart/deletecart/"+cart1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void deleteCart_fail()throws Exception {
		Set<Product> prodList = new HashSet<>();
		prodList.add(prod1);
		prodList.add(prod2);
		Cart cart1 = new Cart(prodList,user);
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		CartService us = mock(CartService.class);
		Mockito.when(cartService.getEntityCart(cart1.getId())).thenReturn(null);
		doNothing().when(us).deleteEntityCart(cart1.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/cart/deletecart/"+cart1.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Cart does not exist with id "+cart1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	

}
