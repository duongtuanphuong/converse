package com.example.converse.service;

import java.util.List;

import com.example.converse.entity.Image;

public interface ImageService {
    
    void save(Image image);

    List<Image> getAllImage();

    void deleteImage(String uploadDir,String filename);
    
}
