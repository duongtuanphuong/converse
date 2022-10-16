package com.example.converse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.converse.entity.Category;
import com.example.converse.entity.Image;
import com.example.converse.entity.Product;
import com.example.converse.exception.NotFoundException;
import com.example.converse.payload.request.CreateProductReq;
import com.example.converse.repository.CategoryRepository;
import com.example.converse.repository.ImageRepository;
import com.example.converse.repository.ProductRepository;
import com.example.converse.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getListProduct() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

    @Override
    public List<Product> getListNewProduct() {
        // TODO Auto-generated method stub
        return productRepository.getListNewProduct();
    }

    @Override
    public List<Product> getListProductByCategoryId(long id) {
        // TODO Auto-generated method stub
        return productRepository.getProductByCategoryId(id);
    }
    

    @Override
    public Page<Product> getPageProduct(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> listProduct = productRepository.findAll(pageable);
        // if(listProduct.hasContent()){
        //     return listProduct.getContent();
        // }else{
        //     return new ArrayList<Product>();
        // }
        return listProduct;
    }

    @Override
    public Page<Product> getListProductByCategoryId(long id,Integer pageNo,Integer pageSize,String sortBy) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> listProduct = productRepository.getProductByCategoryId(id,pageable);
        return listProduct;
    }

    @Override
    public List<Product> getNewListProductByCategoryId(long id) {
        // TODO Auto-generated method stub
        return productRepository.getNewProductByCategoryId(id);
    }

    @Override
    public Product findProductById(long id) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(id).get();
        return product;
    }



    @Override
    public Product createProduct(CreateProductReq req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        Category category = categoryRepository.findById(req.getCategory_id()).get();
        product.setCategory(category);
        ArrayList<Image> images = new ArrayList<>();
        for(Long id : req.getImageIds()){
            Image image = imageRepository.findById(id).get();
            images.add(image);
        }
        product.setImages(images);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long id, CreateProductReq req) {
        Optional<Product> rs = productRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        Product product = rs.get();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        Category category = categoryRepository.findById(req.getCategory_id()).get();
        product.setCategory(category);
        ArrayList<Image> images = new ArrayList<>();
        for(Long imageId : req.getImageIds()){
            Image image = imageRepository.findById(imageId).get();
            images.add(image);
        }
        product.setImages(images);
        productRepository.save(product);
        return product;
    }


    @Override
    public void deleteProduct(long id) {
        // TODO Auto-generated method stub
        Optional<Product> rs = productRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        Product product = rs.get();
        product.getImages().remove(this);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getListProductByCost() {
        // TODO Auto-generated method stub
        return productRepository.getListProductByCost();
    }



}
