package com.personal.project.pgotosClone.web;


import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotosController2 {
    private final PhotosService2 photosService;

    public PhotosController2(PhotosService2 photosService) {
        this.photosService = photosService;
    }

    @PostMapping("/photo")
    public Photo create(@RequestPart("data")MultipartFile file,
                        @RequestParam("storage") String storage) throws IOException {
        boolean saveToDatabase = "database".equalsIgnoreCase(storage);

        return photosService.savePhoto(file.getOriginalFilename(),file.getContentType(),file.getBytes(),saveToDatabase);
    }

    @GetMapping("/local/photo/{id}")
    public Photo readByIdLocal(@PathVariable Integer id) {
        return photosService.getPhotoLocal(id);
    }

    @GetMapping("/database/photo/{id}")
    public Photo readByIdDatabase(@PathVariable Integer id) {
        return photosService.getPhotoDatabase(id);
    }

    @GetMapping("/local/photos")
    public Iterable<Photo> getAllLocal()
    {
        return photosService.getAllPhotosLocal();
    }

    @GetMapping("/database/photos")
    public Iterable<Photo> getAllDatabase()
    {
        return photosService.getAllPhotosDatabase();
    }

    @DeleteMapping("/photo/delete/{id}")
    public void deleteById(@PathVariable Integer id,
                           @RequestParam("storage") String storage)
    {
        boolean fromDatabase = "database".equalsIgnoreCase(storage);

        photosService.deletePhoto(id,fromDatabase);
    }

    @DeleteMapping("/photo/deleteAll")
    public void deleteAll(@RequestParam("storage") String storage)
    {
        boolean fromDatabase = "database".equalsIgnoreCase(storage);

        photosService.deleteAll(fromDatabase);
    }
    @GetMapping("/")
    public String home()
    {
        return "index";
    }
    @GetMapping("/photoes")
    public String photosRedirect(@RequestParam(required = false) String storage) {
        if(storage == null || storage.isEmpty())
        {
            return "index";
        }
        return "photos";
    }
}
