package com.k1rard.inventory.inventory.app.response;

import com.k1rard.inventory.inventory.app.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
   private List<Product> products;
}
