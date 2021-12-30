package com.dell.webservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dell.webservice.entity.Order;
import com.dell.webservice.entity.Product;
import com.dell.webservice.entity.User;
import com.dell.webservice.repository.OrderService;
import com.dell.webservice.repository.UserService;


@RestController
@RequestMapping("api/ver2/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getordersdata")
	public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,@RequestParam(required = false) String username){
		try {
			List<Order> list = orderService.getEntityOrders(pageNo, pageSize, sortBy,username);
			return new ResponseEntity<List<Order>>(list, new HttpHeaders(), HttpStatus.OK); 
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to fetch orders", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/getorder/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable("orderId") int id) {
		try {
			Optional<Order> order = this.orderService.getEntityOrder(id);
			if(order == null) {
				return new ResponseEntity<String>("Order does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else if(!(order.isPresent())) {
				return new ResponseEntity<String>("Order does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else {
				return new ResponseEntity<Optional<Order>>(order,new HttpHeaders(), HttpStatus.OK);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage().toString());
			return new ResponseEntity<String>("Unable to fetch orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/addorder")
	public ResponseEntity<?> addOrder(@RequestBody(required = false) Order addOrder){
		if(addOrder == null) {
			return new ResponseEntity<String>("Add Order request body cannot be empty", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			this.orderService.addEntityOrder(addOrder);
			return new ResponseEntity<Order>(addOrder, new HttpHeaders(), HttpStatus.CREATED);
		} 
		catch (Exception e) {
			return new ResponseEntity<String>("Unable to add orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateorder/{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable("orderId") int id, @RequestBody(required = false) Order updateOrder,@RequestParam(required = false) String userName) {
		if(updateOrder == null) {
			return new ResponseEntity<String>("Update Order request body cannot be empty",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(id != updateOrder.getId()) {
			return new ResponseEntity<String>("Id in request path and request body do not match",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			boolean check = userService.checkAdmin(userName);
			if(check == true) {
				Optional<Order> getOrder = this.orderService.getEntityOrder(id);
				if(getOrder == null) {
					return new ResponseEntity<String>("Order does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
				}
				else if(!(getOrder.isPresent())) {
					return new ResponseEntity<String>("Order does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
				}
				else {
					this.orderService.updateEntityOrder(updateOrder);
					return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.NO_CONTENT);
				}
			}
			else {
				return new ResponseEntity<String>("Unauthorized Request",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to update orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteorder/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable("orderId") int id,@RequestParam(required = true) String userName){
		try {
			boolean check = userService.checkAdmin(userName);
			if(check == true) {
				Optional<Order> getOrder = this.orderService.getEntityOrder(id);
				if(getOrder == null) {
					return new ResponseEntity<String>("Order does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
				}
				else if(!(getOrder.isPresent())) {
					return new ResponseEntity<String>("Order does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
				}
				else {
					this.orderService.deleteEntityOrder(id);
					return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.NO_CONTENT);
				}
			}
			else {
				return new ResponseEntity<String>("Unauthorized Request",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to delete orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}