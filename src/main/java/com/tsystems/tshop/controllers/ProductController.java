package com.tsystems.tshop.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tsystems.tshop.domain.Product;
import com.tsystems.tshop.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Integer id) {
		return productService.getProductById(id);
	}
	
	@GetMapping
	public List<Product> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/totalsaled")
	public List<Product> getTotalSaledProducts(){
		return productService.getTotalSaledProducts();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/writenew")
	@ResponseBody
	public List<Product> writeNewProducts(@RequestBody Product[] products) {
		productService.writeNewProducts(products);
		return productService.getProducts();
	}

}
