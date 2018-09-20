package com.codegym.phone.service;

import com.codegym.phone.model.Brand;
import com.codegym.phone.model.Category;
import com.codegym.phone.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ProductService {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Iterable<Product> findAllByBrand(Brand brand);

    Iterable<Product> findAllByCategory(Category category);

    Product findById(Long id);

    void save(Product product);

    void remove(Long id);

}
