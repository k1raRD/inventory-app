package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.models.Category;
import com.k1rard.inventory.inventory.app.models.Product;
import com.k1rard.inventory.inventory.app.repositories.ICategoryRepository;
import com.k1rard.inventory.inventory.app.repositories.IProductRepository;
import com.k1rard.inventory.inventory.app.response.ProductResponseRest;
import com.k1rard.inventory.inventory.app.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    private IProductRepository productRepository;

    private ICategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            // Search category to set in the product object
            Optional<Category> category = categoryRepository.findById(categoryId);

            if(category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Respuesta no ok", "-1", "Categoria no encontrada para asociar al producto.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Product productSaved = productRepository.save(product);

            if(productSaved != null) {
                list.add(productSaved);
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto guardado exitosamente.");

            } else {
                response.setMetadata("Respuesta no ok", "-1", "Error al intentar guardar el producto");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "Error al intentar guardar el producto");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            // Search category to set in the product object
            Optional<Product> product = productRepository.findById(id);

            if(product.isPresent()) {
                byte[] imageDescompresses = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompresses);
                list.add(product.get());
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");
            } else {
                response.setMetadata("Respuesta no ok", "-1", "Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "Error al intentar buscar el producto");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
