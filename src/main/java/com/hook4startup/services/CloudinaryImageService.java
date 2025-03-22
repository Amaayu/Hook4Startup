package com.hook4startup.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    public Map uplodeImage(MultipartFile file){

        try {
            return this.cloudinary.uploader().upload(file.getBytes(),Map.of());
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }

    }


}
