package com.example.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;
	
	@Column(name = "name")
	@Length(min = 3, message = "*At least 3 characters")
	@NotEmpty(message = "*Must be written")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "quantity")
	@Min(value = 0, message = "*Quantity must not be negative")
	private Integer quantity;
	
	@Column(name = "price")
	@DecimalMin(value = "0.00", message = "*Price must not be negative")
	private BigDecimal price;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal unitprice) {
		this.price = unitprice;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o ) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Product product = (Product) o;
		
		return id.equals(product.id);
	}
	
	@Override 
	public int hashCode() {
		return id.hashCode();
	}
}
