package com.codegym.phone.repository;

import com.codegym.phone.model.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends PagingAndSortingRepository<Brand,Long> {
}
