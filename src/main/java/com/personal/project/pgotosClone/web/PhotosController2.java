package com.personal.project.pgotosClone.web;


import com.personal.project.pgotosClone.errors.CustomExceptions;
import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService2;
import com.personal.project.pgotosClone.storage.StorageType;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.personal.project.pgotosClone.service.PhotosService2.isSupportedContentType;

@RestController
public class PhotosController2 {
    private final PhotosService2 photosService;

    public PhotosController2(PhotosService2 photosService) {
        this.photosService = photosService;
    }

    @PostMapping("/photo")
    public Photo create(@RequestPart("data") MultipartFile file,
                        @RequestParam("storage") String storage) throws IOException {

        if(file.isEmpty())
        {
            throw new CustomExceptions("No file was uploaded. Please select a file. ", HttpStatus.BAD_REQUEST.value());
        }

        String contentType = file.getContentType();
        if(!isSupportedContentType(contentType))
        {
            throw new CustomExceptions("Unsupported file type.", HttpStatus.FORBIDDEN.value());
        }

        StorageType storageType = StorageType.valueOf(storage.toUpperCase());
        try {
            photosService.savePhoto(file.getOriginalFilename(), file.getContentType(), file.getBytes(), storageType);
        } catch(ValidationException e) {
            throw new CustomExceptions("Cannot upload an empty file", HttpStatus.FORBIDDEN.value());
        }

        return photosService.savePhoto(file.getOriginalFilename(), file.getContentType(), file.getBytes(), storageType);
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
