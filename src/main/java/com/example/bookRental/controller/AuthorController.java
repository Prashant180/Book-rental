package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.AuthorDto;
import com.example.bookRental.model.Author;
import com.example.bookRental.model.Book;
import com.example.bookRental.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @GetMapping("/all")
    public CustomResponse<List<AuthorDto>> getAuthors(){
        return CustomResponse.success(service.getAllAuthors());
    }

    @PostMapping("/add")
    public CustomResponse<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto){
        return CustomResponse.success(service.addAuthor(authorDto));
    }
    @PutMapping("/update")
    public CustomResponse<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto){
        return CustomResponse.success(service.updateAuthor(authorDto));
    }

    @GetMapping("/get-by-id/{id}")
    public CustomResponse<AuthorDto> getAuthor(@PathVariable Integer id){
        return CustomResponse.success(service.getAuthorById(id));
    }

    @DeleteMapping("/delete/{id}")
    public CustomResponse<AuthorDto> deleteAuthor(@PathVariable Integer id){
        service.deleteAuthor(id);
        return CustomResponse.success("Author deleted with Id "+id);
    }

}
