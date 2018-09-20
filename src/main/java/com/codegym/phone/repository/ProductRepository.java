package com.codegym.phone.repository;

import com.codegym.phone.model.Brand;
import com.codegym.phone.model.Category;
import com.codegym.phone.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Iterable<Product> findAllByBrand(Brand brand);



}
