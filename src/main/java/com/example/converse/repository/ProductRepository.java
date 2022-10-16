package com.example.converse.repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.converse.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    
    @Query(value = "Select * from Product order by id desc limit 8",nativeQuery = true)
    List<Product> getListNewProduct();

    Page<Product> getProductByCategoryId(long id,Pageable pageable);

    @Query(value ="Select * from Product where category_id = :id order by id desc limit 5",nativeQuery = true)
    List<Product> getNewProductByCategoryId(long id);

    @Query(value = "Select * from Product where category_id = :id order by id limit 8",nativeQuery = true)
    List<Product> getProductByCategoryId(long id);

    @Query(value = "Select * from Product order by price limit 8 ",nativeQuery = true)
    List<Product> getListProductByCost();

}
