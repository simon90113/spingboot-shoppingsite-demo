package com.example.demo.service.implement;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
//import org.springframework.data.repository.CrudRepository;

import com.example.demo.dao.ProductRepository;
import com.example.demo.exception.NotEnoughProductsInStockException;
import com.example.demo.model.Product;
import com.example.demo.service.ShoppingcartService;

/**
 * Shopping Cart is implemented with a Map, 
 * 					and " as a session bean " <--------- Important!
 * @author Dusan
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingcartServiceImpl implements ShoppingcartService {
	
	private final ProductRepository productRepository;
	
	private Map<Product, Integer> products = new HashMap<>();
		
	@Autowired
	public ShoppingcartServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void addProduct(Product product) {
		if (products.containsKey(product)) {
			products.replace(product, products.get(product) + 1);
		} else {
			products.put(product, 1);
		}
	}
	
	public void removeProduct(Product product) {
		if (products.containsKey(product)) {
			
			if(products.get(product) > 1) {
				products.replace(product, products.get(product) -1);
			} else if (products.get(product) == 1) {
				products.remove(product);
			}			
		}
	}
	
	// unmodifiable copy of the map
	@Override
	public Map<Product, Integer> getProductsInCart(){
		return Collections.unmodifiableMap(products);
	}
	
	@Override 
	public void checkout() throws NotEnoughProductsInStockException {
		Product product;
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			// Refresh quantity for every product before checking
			product = productRepository.findById(entry.getKey().getId()).get();
									//  findOne(entry.getKey().getId());			
			if(product.getQuantity() < entry.getValue()) {
				throw new NotEnoughProductsInStockException(product);
			}			
			entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
		}		
		productRepository.saveAll(products.keySet());
		productRepository.flush();
		products.clear();		
	
	}
	
	@Override
	public BigDecimal getTotal() {
		return products.entrySet().stream()
				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
}