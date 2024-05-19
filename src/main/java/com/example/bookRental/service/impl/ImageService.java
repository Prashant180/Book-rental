package com.example.bookRental.service.impl;

import com.example.bookRental.dao.ImageDataRepo;
import com.example.bookRental.model.ImageData;
import com.example.bookRental.service.ImageDataService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;

@Service
public class ImageService implements ImageDataService {

    @Autowired
    private ImageDataRepo imageDataRepo;

    @Override
    public ImageData saveImage(String image) {

        image=image.replaceAll("[^A-Za-z0-9+/=]", "");
        byte[] imageByte = Base64.getDecoder().decode(image);
        ImageData imageData = ImageData.builder().build();
        String photoPath="d:/output_image1.png";

        try (FileOutputStream fos = new FileOutputStream(photoPath)) {
            fos.write(imageByte);
            System.out.println("Image saved as output_image.png");
        } catch (IOException e) {
            System.err.println("Error writing the image file: " + e.getMessage());
        }
        imageData.setPhoto(photoPath);

        return imageDataRepo.save(imageData);
    }

    @Override
    public String getImageById(Integer id) {
        String imagePath= null;
        imagePath=imageDataRepo.findById(id).get().getPhoto();

        return imageToString(imagePath);
    }

    public String imageToString(String imagePath){
        String encodedString;
        try {
            byte[] fileContent= FileUtils.readFileToByteArray(new File(imagePath));
            encodedString=Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return encodedString;
    }
}
