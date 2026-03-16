package com.example.demoproduct.service;

import com.example.demoproduct.model.Product;
import com.example.demoproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return repo.findByNameContainingIgnoreCase(keyword);
        }
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

