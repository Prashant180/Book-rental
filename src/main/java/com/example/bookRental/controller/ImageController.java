package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.model.ImageData;
import com.example.bookRental.service.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageDataService imageDataService;

    @PostMapping("/save")
    public CustomResponse<ImageData> uploadImage(@RequestBody String  image) {
//        ImageData image = new ImageData();
//        image.setPhoto(base64Data);
//        image = imageDataService.saveImage(image);
//        return CustomResponse.success(image);
        return CustomResponse.success(imageDataService.saveImage(image));
    }

    @GetMapping("/get/{id}")
    public CustomResponse<String> getImage(@PathVariable Integer id){
        return CustomResponse.success(imageDataService.getImageById(id));
    }
}
