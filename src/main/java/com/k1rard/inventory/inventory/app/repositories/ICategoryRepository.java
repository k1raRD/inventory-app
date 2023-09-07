package com.k1rard.inventory.inventory.app.repositories;

import com.k1rard.inventory.inventory.app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
