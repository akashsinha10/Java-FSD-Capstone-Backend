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

import com.dell.webservice.entity.User;
import com.dell.webservice.repository.UserService;

@RestController
@RequestMapping("/api/ver2/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody(required = false) User loginUser){
		boolean flag = this.userService.loginEntityUser(loginUser);
		if(flag == true) {
			return new ResponseEntity<String>("{\"loggedIn\" : true}",new HttpHeaders(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("{\"error\" : \"Unauthorized\",\"message\" : \"Access denied\"}",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
		}
	}
	@PostMapping("/logout/{username}")
	public ResponseEntity<String> logoutUser(@PathVariable("username") String logoutUser){
		boolean flag = this.userService.logoutEntityUser(logoutUser);
		if(flag == true) {
			return new ResponseEntity<String>("{\"loggedOut\" : true}",new HttpHeaders(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("{\"error\" : \"Unauthorized\",\"message\" : \"Access denied\"}",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
		}
	}
	@GetMapping("/getusers")
	public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		try {
			List<User> userList = userService.getEntityUsers(pageNo, pageSize, sortBy);
			return new ResponseEntity<List<User>>(userList, new HttpHeaders(), HttpStatus.OK); 
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to fetch users", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	@GetMapping("/getuser/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") int id) {
		try {
			Optional<User> user = this.userService.getEntityUser(id);
			if(user == null) {
				return new ResponseEntity<String>("User does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else if(!(user.isPresent())) {
				return new ResponseEntity<String>("User does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else {
				return new ResponseEntity<Optional<User>>(user,new HttpHeaders(), HttpStatus.OK);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage().toString());
			return new ResponseEntity<String>("Unable to fetch users",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<?> addUser(@RequestBody(required = false) User addUser){
		if(addUser == null) {
			return new ResponseEntity<String>("Add User request body cannot be empty", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			this.userService.addEntityUser(addUser);
			return new ResponseEntity<User>(addUser, new HttpHeaders(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Unable to add users",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/updateuser/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable("userId") int id, @RequestBody(required = false) User updateUser) {
		if(updateUser == null) {
			return new ResponseEntity<String>("Update User request body cannot be empty",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(id != updateUser.getId()) {
			return new ResponseEntity<String>("Id in request path and request body do not match",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<User> getUser = this.userService.getEntityUser(id);
			if(getUser == null) {
				return new ResponseEntity<String>("User does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else if(!(getUser.isPresent())) {
				return new ResponseEntity<String>("User does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			else {
				this.userService.updateEntityUser(updateUser);
				return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to update users",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<?> deleteProduct(@PathVariable("userId") int id){
		try {
			Optional<User> getUser = this.userService.getEntityUser(id);
			if(getUser == null) {
				return new ResponseEntity<String>("User does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else if(!(getUser.isPresent())) {
				return new ResponseEntity<String>("User does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			else {
				this.userService.deleteEntityUser(id);
				return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<String>("Unable to delete users",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
