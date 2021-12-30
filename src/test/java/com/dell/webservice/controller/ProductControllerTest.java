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

import com.dell.webservice.entity.Product;
import com.dell.webservice.entity.User;
import com.dell.webservice.repository.ProductService;
import com.dell.webservice.repository.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Test")
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private ProductService productService;
	
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
		
		Mockito.when(productService.getEntityProducts(0, 10, "id",null,null)).thenReturn(prodList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/product/getproductsdata").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Get all products without filters");
		System.out.println(result.getResponse().getContentAsString());
		//String expected = "[{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T15:57:06.520+00:00\"}]";
		//assertEquals(expected,result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		
	}
	
	@Test
	public void getProduct_success()throws Exception {
		
		Mockito.when(productService.getEntityProduct(prod1.getId())).thenReturn(java.util.Optional.of(prod1));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/product/getproduct/"+prod1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(200,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"name\":\"Chicken Biryani\",\"price\":250.0,\"description\":\"250/- per plate\",\"category\":\"Mughlai\",\"imagePath\":\"../../../assets/images/ChickenBiryani.jpg\",\"categoryImagePath\":\"../../../assets/images/Mughlai.jpg\",\"seller\":\"Golden Spoon\",\"createdAt\":\"2021-12-17T16:00:04.112+00:00\"}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void getProduct_fail()throws Exception {
		
		Mockito.when(productService.getEntityProduct(prod1.getId())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/ver2/product/getproduct/"+prod1.getId()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("get by id fail");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Product does not exist with id "+prod1.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addProduct_success()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		doNothing().when(us).addEntityProduct(prod2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/product/addproduct?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(prod2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add");
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(201,result.getResponse().getStatus());
		//String expected = "{\"id\":0,\"username\":\"Avilasa\",\"password\":\"pqr\",\"email\":\"Avilasa.Das@dell.com\",\"role\":\"admin\",\"walletBalance\":10000.57,\"loggedIn\":true}";
		//assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void addProduct_fail()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		doNothing().when(us).addEntityProduct(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/ver2/product/addproduct?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("add fail bad request");
		System.out.println(result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Add Product request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateProduct_success()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		Mockito.when(productService.getEntityProduct(prod2.getId())).thenReturn(java.util.Optional.of(prod2));
		doNothing().when(us).updateEntityProduct(prod2);
		prod2.setName("Veg Enchiladas");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/product/updateproduct/"+prod2.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(prod2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void updateProduct_fail()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		doNothing().when(us).updateEntityProduct(null);
		prod2.setName("Veg Enchiladas");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/product/updateproduct/"+prod2.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail bad request");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Update Product request body cannot be empty";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateProduct_fail_notfound()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		Mockito.when(productService.getEntityProduct(prod2.getId())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/product/updateproduct/"+prod2.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(prod2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail not found");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Product does not exist with id "+prod2.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void updateProduct_fail_idmismatch()throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/ver2/product/updateproduct/3"+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(prod2)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("update fail id mismatch");
		assertEquals(400,result.getResponse().getStatus());
		String expected = "Id in request path and request body do not match";
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	
	@Test
	public void deleteProduct_success()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		Mockito.when(productService.getEntityProduct(prod2.getId())).thenReturn(java.util.Optional.of(prod2));
		doNothing().when(us).deleteEntityProduct(prod2.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/product/deleteproduct/"+prod2.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(204,result.getResponse().getStatus());
	}
	
	@Test
	public void deleteUser_fail()throws Exception {
		Mockito.when(userService.checkAdmin(user.getUsername())).thenReturn(true);
		ProductService us = mock(ProductService.class);
		Mockito.when(productService.getEntityProduct(prod2.getId())).thenReturn(null);
		doNothing().when(us).deleteEntityProduct(prod2.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/ver2/product/deleteproduct/"+prod2.getId()+"?userName="+user.getUsername()).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("delete");
		assertEquals(404,result.getResponse().getStatus());
		String expected = "Product does not exist with id "+prod2.getId();
		assertEquals(expected,result.getResponse().getContentAsString());
	}
	

}
