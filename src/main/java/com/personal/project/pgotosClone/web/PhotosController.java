package com.personal.project.pgotosClone.web;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
public class PhotosController {

    private final PhotosService photosService;

    public PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/hello")
    public String hello()
    {
        return "Hello World";
    }

    @GetMapping("/photos")
    public Collection<Photo> get()
    {
        return photosService.getAll();
    }

    @GetMapping("/photo/{id}")
    public Photo getById(@PathVariable Integer id)
    {
        Photo photo = photosService.get(id);

        if(photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo is not found");
        }

        return photo;
    }

    @DeleteMapping("/photo/delete/{id}")
    public void delete(@PathVariable Integer id) {
        Photo photo = photosService.remove(id);

        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo is not found");
        }
        photosService.add(id);

    }

    @DeleteMapping("/photo/deleteAll")
    public void deleteAll()
    {
        photosService.deleteAll();
    }

//    @PostMapping("/photo")
//    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
//        return photosService.create(file);
//    }

    @PostMapping("/photo")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {

        return photosService.put(file.getOriginalFilename(), file.getContentType(),file.getBytes());
    }

}
