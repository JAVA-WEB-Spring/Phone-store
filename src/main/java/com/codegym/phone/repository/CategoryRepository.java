package com.codegym.phone.repository;

import com.codegym.phone.model.Category;
import com.codegym.phone.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Long> {
    @Query("SELECT c.products from Category c WHERE  c.id = :id")
    Iterable<Product> findAllProductByCategory(@Param("id") long categoryId);

}
