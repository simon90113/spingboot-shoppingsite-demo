package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.exception.NotEnoughProductsInStockException;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingcartService;

@Controller
public class ShoppingcartController {
	
	private final ShoppingcartService shoppingcartService;
	
	private final ProductService productService;
	
	@Autowired
	public ShoppingcartController(ShoppingcartService shoppingcartService, ProductService productService) {
		this.shoppingcartService = shoppingcartService;
		this.productService = productService;
	}
	
	@GetMapping("/shoppingcart")
	public ModelAndView shoppingcart() {
		ModelAndView modelAndView = new ModelAndView("/shoppingcart");
		modelAndView.addObject("products", shoppingcartService.getProductsInCart());
		modelAndView.addObject("total", shoppingcartService.getTotal().toString());
		return modelAndView;
	}
	
	@GetMapping("/shoppingcart/addProduct/{productId}")
	public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
		productService.findById(productId).ifPresent(shoppingcartService::addProduct);
		return shoppingcart();
	}
	
//	DeleteMapping? GetMapping? THE SAME?
	@DeleteMapping("/shoppingcart/addProduct/{productId}")
	public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
		productService.findById(productId).ifPresent(shoppingcartService::removeProduct);
		return shoppingcart();
	}
	
	@GetMapping("/shoppingcart/checkout")
	public ModelAndView checkout() {
		try {
			shoppingcartService.checkout();
		} catch (NotEnoughProductsInStockException e) {
			return shoppingcart().addObject("outOfStockMessage", e.getMessage());
		}
		return shoppingcart();
	}
	
}
