package com.codegym.phone.service;

import com.codegym.phone.model.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BrandService {
    Iterable<Brand> findAll();

    Brand findById(Long id);

    void save(Brand brand);

    void remove(Long id);
}
