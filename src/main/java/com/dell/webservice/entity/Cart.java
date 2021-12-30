package com.dell.webservice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ecart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ecart_id")
	private int id;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	private Set<Product> products;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;
	
	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(int id, Set<Product> products, User user) {
		super();
		this.id = id;
		this.products = products;
		this.user = user;
	}
	public Cart(Set<Product> products, User user) {
		super();
		this.products = products;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Product> getproductsdata() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", products=" + products + ", user=" + user + "]";
	}
	
	
}
