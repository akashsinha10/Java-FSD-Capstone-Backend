package com.dell.webservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dell.webservice.entity.User;
import com.dell.webservice.interfaces.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public boolean checkAdmin(String loginUser) {
		Iterable<User> users = this.userRepository.findAll();
		int flag = 0;
		for(User u : users) {
			if(u.getUsername().equals(loginUser) && u.getRole().toLowerCase().equals("admin")) {
				flag = 1;
				break;
			}
		}
		if(flag == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	
	public boolean loginEntityUser(User loginUser) {
		Iterable<User> users = this.userRepository.findAll();
		int flag = 0;
		for(User u : users) {
			if(u.getUsername().equals(loginUser.getUsername()) && u.getPassword().equals(loginUser.getPassword())) {
				u.setLoggedIn(true);
				this.userRepository.save(u);
				flag = 1;
				break;
			}
		}
		if(flag == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean logoutEntityUser(String logoutUser) {
		Iterable<User> users = this.userRepository.findAll();
		
		int flag = 0;
		for(User u : users) {
			if(u.getUsername().equals(logoutUser)) {
				System.out.println(u.isLoggedIn());
				if(u.isLoggedIn() == true) {
					u.setLoggedIn(false);
					this.userRepository.save(u);
					flag = 1;
					break;
				}
			}
		}
		if(flag == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	public List<User> getEntityUsers(Integer pageNo, Integer pageSize, String sortBy) throws Exception{
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
			Page<User> pagedResult;
			pagedResult = userRepository.findAll(paging);
			
			if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<User>();
	        }
		}
		catch(Exception ex) {
			throw new Exception("Unable to retrieve users "+ex.getMessage().toString());
		}
	}
	public Optional<User> getEntityUser(int userId) throws Exception{
		try {
			Optional<User> user = this.userRepository.findById(userId);
			return user;
		}
		catch(Exception ex){
			throw new Exception("Unable to retrieve user with id"+userId+" "+ex.getMessage().toString());
		}
	}
	public void addEntityUser(User addUser) throws Exception {
		try {
			this.userRepository.save(addUser);
		}
		catch(Exception ex) {
			throw new Exception("Unable to add user "+ex.getMessage().toString());
		}
	}
	
	public void updateEntityUser(User updateUser) throws Exception {
		try {
			System.out.println("Inside update");
			System.out.println(updateUser.getWalletBalance());
			this.userRepository.save(updateUser);
		}
		catch(Exception ex) {
			throw new Exception("Unable to update user "+ex.getMessage().toString());
		}
	}
	
	public void deleteEntityUser(int userId) throws Exception {
		try {
			this.userRepository.deleteById(userId);
		}
		catch(Exception ex) {
			throw new Exception("Unable to delete user "+ex.getMessage().toString());
		}
	}
}
