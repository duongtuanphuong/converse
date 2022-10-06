package com.example.converse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.converse.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    
    @Query(value = "Select * from Product order by id desc limit 8",nativeQuery = true)
    List<Product> getListNewProduct();
}
