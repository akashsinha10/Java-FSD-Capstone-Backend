package com.dell.webservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "eproduct")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "eproduct_id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "imagePath")
	private String imagePath;
	
	@Column(name = "categoryImagePath")
	private String categoryImagePath;
	
	@Column(name = "seller")
	private String seller;
	
	@Column(name = "createdAt")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	public Product() {
		super();
		//TODO Auto-generated constructor stub
	}

	public Product(int id, String name, double price, String description,String category, String imagePath,String categoryImagePath, String seller) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.imagePath = imagePath;
		this.categoryImagePath = categoryImagePath;
		this.seller = seller;
		this.createdAt = new Date();
	}
	public Product(String name, double price, String description, String category,String imagePath,String categoryImagePath, String seller) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.imagePath = imagePath;
		this.categoryImagePath = categoryImagePath;
		this.seller = seller;
		this.createdAt = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

	public String getCategoryImagePath() {
		return categoryImagePath;
	}

	public void setCategoryImagePath(String categoryImagePath) {
		this.categoryImagePath = categoryImagePath;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", category=" + category + ", imagePath=" + imagePath + ", categoryImagePath=" + categoryImagePath
				+ ", seller=" + seller + ", createdAt=" + createdAt + "]";
	}

	

}
