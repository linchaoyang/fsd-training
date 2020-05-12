package fsd.msservice.transaction.api.service;

import java.util.List;
import java.util.Optional;

import fsd.msservice.transaction.api.domain.Product;

public interface ProductService {

    Long count();

    List<Product> findAll();
    
    List<Product> findByName(String name);

    List<Product> findAllByCategoryId(String categoryId);

    Optional<Product> findById(String id);

    Product add(Product product);

    Product update(Product product);

    void deleteById(String id);

}