package com.example.gardencoffeeproduct.service;

import com.example.gardencoffeeproduct.model.Category;
import com.example.gardencoffeeproduct.model.Product;
import com.example.gardencoffeeproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, String description, long price) {
        var newProduct = new Product(UUID.randomUUID(), productName, category, price,description, LocalDateTime.now(), LocalDateTime.now());
        return productRepository.insert(newProduct);
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        var newProduct = new Product(UUID.randomUUID(),productName,category,price);
        return productRepository.insert(newProduct);
    }
}
