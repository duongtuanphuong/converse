package com.example.converse.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.converse.entity.Image;
import com.example.converse.exception.BadRequestException;
import com.example.converse.exception.InternalServerException;
import com.example.converse.repository.ImageRepository;
import com.example.converse.service.ImageService;

@Controller
public class ImageController {

    private static String UPLOAD_DIR  = System.getProperty("user.dir") + "/src/main/resources/static/image/";

    @Autowired
    private ImageService imageService;

    @PostMapping("admin/api/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);;
        if (originalFilename != null && originalFilename.length() > 0) {
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                throw new BadRequestException("Không hỗ trợ định dạng file này");
            }
            try {
                Image img = new Image();
                img.setName(originalFilename);
                img.setData(Base64.getEncoder().encodeToString(file.getBytes()));
                img.setType(extension); 
                String uid = UUID.randomUUID().toString();
                img.setLink(UPLOAD_DIR + uid + file.getOriginalFilename());
                // Create file
                File serverFile = new File(img.getLink());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();

                imageService.save(img);
                return ResponseEntity.ok(img);
            } catch (Exception e) {
                throw new InternalServerException("Lỗi khi upload file");
            }
        }

        throw new BadRequestException("File không hợp lệ");
    }
}
