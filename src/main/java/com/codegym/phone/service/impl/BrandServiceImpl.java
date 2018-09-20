package com.codegym.phone.service.impl;

import com.codegym.phone.model.Brand;
import com.codegym.phone.repository.BrandRepository;
import com.codegym.phone.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Override
    public Iterable<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findOne(id);
    }

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void remove(Long id) {
        brandRepository.delete(id);
    }
}
