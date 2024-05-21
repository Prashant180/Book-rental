package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.model.ImageData;
import com.example.bookRental.service.ImageDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageDataService imageDataService;

    @PostMapping("/save")
    public CustomResponse<ImageData> uploadImage(@RequestBody String  image) {
        return CustomResponse.success(imageDataService.saveImage(image));
    }

    @GetMapping("/get/{id}")
    public CustomResponse<String> getImage(@PathVariable Integer id){
        return CustomResponse.success(imageDataService.getImageById(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Integer id) throws FileNotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String imagePath=imageDataService.getImageById(id);
        System.out.println(imagePath);
        File file=new File(imagePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }
}
