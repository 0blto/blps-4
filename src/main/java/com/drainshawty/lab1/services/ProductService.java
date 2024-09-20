package com.drainshawty.lab1.services;

import com.drainshawty.lab1.model.Product;
import com.drainshawty.lab1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
