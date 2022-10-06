package com.example.converse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.converse.entity.Image;

import com.example.converse.repository.ImageRepository;
import com.example.converse.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void save(Image image) {
        // TODO Auto-generated method stub
        imageRepository.save(image);
    }

    @Override
    public void deleteImage(String uploadDir, String filename) {
        // TODO Auto-generated method stub
        // String link = "/media/static/" + filename;
        // Image image = imageRepository.findByLink(link);
        // if(image == null){
        //     throw new BadRequestException("File không tồn tại!");
        // }
        // imageRepository.delete(image);
        // File file = new File(uploadDir + "/" + filename);
        // if (file.exists()) {
        //     if (!file.delete()) {
        //         throw new InternalServerException("Lỗi khi xóa ảnh");
        //     }
        // }
    }

    @Override
    public List<Image> getAllImage() {
        // TODO Auto-generated method stub
        return imageRepository.findAll();
    }
    
}
