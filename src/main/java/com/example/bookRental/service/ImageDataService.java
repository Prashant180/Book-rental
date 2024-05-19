package com.example.bookRental.service;

import com.example.bookRental.model.ImageData;

public interface ImageDataService {
    ImageData saveImage(String photo);
    String  getImageById(Integer id);
    String imageToString(String imagePath);
}
