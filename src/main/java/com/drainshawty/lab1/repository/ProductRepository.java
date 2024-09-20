package com.drainshawty.lab1.repository;

import com.drainshawty.lab1.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {}
