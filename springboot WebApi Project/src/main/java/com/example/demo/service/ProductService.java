package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Product;
import com.example.demo.repo.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

	// save product
	public Product saveProduct (Product product){
		try {
			return productRepository.save(product);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to save product: " + e.getMessage());
		}
	}

	// get all products
	public List<Product> fetchAllProducts(){
		try {
			return  productRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to fetch all products: " + e.getMessage());
		}
	}

	//get product by id
	public Optional<Product> findById(long id){
		try {
			return productRepository.findById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to fetch product with id : " + e.getMessage());
		}
	}

	//update product by ID
	public Optional<Product> updateProductById(Product updateProduct, Long id){
		try {
			Optional<Product> optionalProduct = productRepository.findById(id);
			if(optionalProduct.isPresent()){
				Product existingProduct = optionalProduct.get();
				existingProduct.setId(id);
				existingProduct.setName(updateProduct.getName());
				existingProduct.setPrice(updateProduct.getPrice());
				existingProduct.setQuantity(updateProduct.getQuantity());
				Product saveProduct = productRepository.save(existingProduct);
				return Optional.of(saveProduct);
			}else{
				return Optional.empty();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to update product " + e.getMessage());
		}
	}

	public boolean deleteProduct(Long id){
		try {
			productRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to delete product " + e.getMessage());
		}
	}
}