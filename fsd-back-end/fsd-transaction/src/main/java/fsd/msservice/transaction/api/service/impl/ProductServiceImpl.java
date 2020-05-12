package fsd.msservice.product.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import fsd.msservice.product.api.domain.Product;
import fsd.msservice.product.api.domain.ProductCarousel;
import fsd.msservice.product.api.exception.ProductNotFoundException;
import fsd.msservice.product.api.repository.ProductCarouselRepository;
import fsd.msservice.product.api.repository.ProductRepository;
import fsd.msservice.product.api.service.ProductService;

@Service
// @Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductCarouselRepository carouselRepository;

    public Long count() {
        return repository.count();
    }

    /**
     * Get all products
     * 
     * @return
     */
    public List<Product> findAll() {
        return repository.findAll();
    }

    /**
     * Find all products matched the name
     * 
     * @param name
     * @return
     */
    public List<Product> findByName(String name) {
        return repository.findByProductName(name);
    }

    /**
     * Find all products undr one catetory
     * 
     * @param categoryId
     * @return
     */
    public List<Product> findAllByCategoryId(String categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    /**
     * Find one product
     * 
     * @param id
     * @return
     */
    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }

    /**
     * Add new product
     * 
     * @param product
     * @return
     */
    @Transactional
    public Product add(Product product) {
        product = repository.save(product);

        if (!ObjectUtils.isEmpty(product.getCarousels())) {
            for (ProductCarousel carousel : product.getCarousels()) {
                carousel.setProduct(product);
                carouselRepository.save(carousel);
            }
        }

        return product;
        
    }

    /**
     * Update one product
     * 
     * @param product
     * @return
     */
    @Transactional
    public Product update(Product product) {
        if (product.getId() != null) {
            if (!findById(product.getId()).isPresent()) {
                throw new ProductNotFoundException("Product not found with id: (0}", product.getId());
            }
            // delete carousels under this product
            if (ObjectUtils.isEmpty(product.getCarousels())) {
                carouselRepository.deleteByProductId(product.getId());
            } else {
                for (ProductCarousel carousel : product.getCarousels()) {
                    carousel.setProduct(product);
                }
            }
            return repository.save(product);
        } else {
            return add(product);
        }
        
    }

    /**
     * Delete the product by id
     * 
     * @param id
     */
    @Transactional
    public void deleteById(String id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            carouselRepository.deleteByProductId(id);
        }
        repository.deleteById(id);
    }

}