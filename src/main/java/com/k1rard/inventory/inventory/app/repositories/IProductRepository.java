package com.k1rard.inventory.inventory.app.repositories;

import com.k1rard.inventory.inventory.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
