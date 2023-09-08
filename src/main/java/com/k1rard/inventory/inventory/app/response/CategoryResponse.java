package com.k1rard.inventory.inventory.app.response;

import com.k1rard.inventory.inventory.app.models.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> categories;
}
