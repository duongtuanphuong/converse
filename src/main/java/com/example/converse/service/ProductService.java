package com.example.converse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.converse.entity.Image;
import com.example.converse.entity.Product;
import com.example.converse.payload.request.CreateProductReq;

public interface ProductService {
    
    List<Product> getListProduct();

    List<Product> getPageProduct(Integer pageNo,Integer pageSize,String sortBy);

    Product findProductById(long id);

    Product createProduct(CreateProductReq req);

    Product updateProduct(long id,CreateProductReq req);

    void deleteProduct(long id);
}
