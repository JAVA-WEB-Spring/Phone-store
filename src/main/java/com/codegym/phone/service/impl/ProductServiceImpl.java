package com.codegym.phone.service.impl;

import com.codegym.phone.model.Brand;
import com.codegym.phone.model.Category;
import com.codegym.phone.model.Product;
import com.codegym.phone.repository.CategoryRepository;
import com.codegym.phone.repository.ProductRepository;
import com.codegym.phone.service.ProductService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<Product> findAllByNameContaining(String name, Pageable pageable) {
        Page<Product> products = productRepository.findAllByNameContaining(name, pageable);
        products.forEach(product -> Hibernate.initialize(product.getCategories()));
        return products;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(product -> Hibernate.initialize(product.getCategories()));
        products.forEach(product -> Hibernate.initialize(product.getImage()));
        return products;
    }

    @Override
    public Iterable<Product> findAllByBrand(Brand brand) {
        return productRepository.findAllByBrand(brand);
    }

    @Override
    public Iterable<Product> findAllByCategory(Category category) {
        return categoryRepository.findAllProductByCategory(category.getId());
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findOne(id);
        if (product != null) {
            Hibernate.initialize(product.getCategories());
        }
        return product;
    }

    @Override
    public void save(Product product) {
        List<Category> categories = new ArrayList<>();
        for (Category category : product.getCategories()) {
            categories.add(categoryRepository.findOne(category.getId()));
        }
        product.setCategories(categories);
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findOne(id);
        product.getCategories().removeAll(product.getCategories());
        productRepository.delete(id);
    }
}
