package com.example.gardencoffeeproduct.service;

import com.example.gardencoffeeproduct.model.Category;
import com.example.gardencoffeeproduct.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, String description, long price);

    Product createProduct(String productName, Category category, long price);
}
