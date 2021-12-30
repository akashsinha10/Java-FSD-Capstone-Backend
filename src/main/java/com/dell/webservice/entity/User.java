package com.dell.webservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column(name= "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "walletBalance")
	private double walletBalance;
	
	@Column(name = "loggedIn")
	private boolean loggedIn = false;


	public User() {
		super();
		//TODO Auto-generated constructor stub
	}
	
	public User( String username, String password, String email, String role, double walletBalance,
			boolean loggedIn) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.walletBalance = walletBalance;
		this.loggedIn = loggedIn;
	}
	public User(int id, String username, String password, String email, String role, double walletBalance,
			boolean loggedIn) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.walletBalance = walletBalance;
		this.loggedIn = loggedIn;
	}




	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", role="
				+ role + ", walletBalance=" + walletBalance + ", loggedIn=" + loggedIn + "]";
	}

}
