package com.example.demo.util;

import org.springframework.data.domain.Page;

import com.example.demo.model.Product;

public class Pager {
	
	private final Page<Product> products;
	
	public Pager(Page<Product> products) {
		this.products = products;
	}

	public int getPageNumber() {
		return products.getNumber() + 1;
	}

	public int getPageSize() {
		return products.getSize();
	}

	public boolean hasNext() {
		return products.hasNext();
	}

	public boolean hasPrevious() {
		return products.hasPrevious();
	}
	
	public int getTotalPages() {
		return products.getTotalPages();
	}
	
	public long getTotalElements() {
		return products.getTotalElements();
	}
	
	public boolean indexOutOfBounds() {
		return this.getPageNumber() < 0 || this.getPageNumber()	> this.getTotalElements();
	}
}
