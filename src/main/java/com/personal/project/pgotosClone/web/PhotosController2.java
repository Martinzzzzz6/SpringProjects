package com.personal.project.pgotosClone.web;


import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService2;
import com.personal.project.pgotosClone.storage.StorageType;
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

        StorageType storageType = StorageType.valueOf(storage.toUpperCase());

        return photosService.savePhoto(file.getOriginalFilename(),file.getContentType(),file.getBytes(),storageType);
    }

    @GetMapping("/local/photo/{id}")
    public Photo readByIdLocal(@PathVariable Integer id) {
        return photosService.get(id, StorageType.FILE);
    }

    @GetMapping("/database/photo/{id}")
    public Photo readByIdDatabase(@PathVariable Integer id) {
        return photosService.get(id, StorageType.DATABASE);
    }

    @GetMapping("/local/photos")
    public Iterable<Photo> getAllLocal()
    {
        return photosService.getAll(StorageType.FILE);
    }

    @GetMapping("/database/photos")
    public Iterable<Photo> getAllDatabase()
    {
        return photosService.getAll(StorageType.DATABASE);
    }

    @DeleteMapping ("/database/delete/{id}")
    public void deletePhotoDatabase(@PathVariable Integer id)
    {
        photosService.delete(id, StorageType.DATABASE);
    }

    @DeleteMapping("/local/delete/{id}")
    public void deletePhotoLocal(@PathVariable Integer id)
    {
        photosService.delete(id, StorageType.FILE);
    }

    @DeleteMapping("/database/deleteAll")
    public void deleteAllDatabase()
    {
        photosService.deleteAll(StorageType.DATABASE);
    }

    @DeleteMapping("/local/deleteAll")
    public void deleteAllLocal()
    {
        photosService.deleteAll(StorageType.FILE);
    }

}
