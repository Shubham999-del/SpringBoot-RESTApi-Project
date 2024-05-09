package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("api/v1")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@PostMapping("/product")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		Product savedProduct = productService.saveProduct(product);
		return ResponseEntity.ok(savedProduct);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id){
		Optional<Product> product  = productService.findById(id);
		return product.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products = productService.fetchAllProducts();
		return ResponseEntity.ok(products);
	}

	//update a product
	@PutMapping(path = "/product/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct){
		Optional<Product> updatedProduct = productService.updateProductById(updateProduct, id);
		return updatedProduct.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}

	//delete a product
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		boolean deleteStatus = productService.deleteProduct(id);
		if(deleteStatus){
			return ResponseEntity.ok("Product with ID " + id + " deleted");
		}else{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product with id : " + id);
		}
	}
}
