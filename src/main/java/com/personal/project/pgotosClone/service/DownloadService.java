package com.personal.project.pgotosClone.service;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService2;
import com.personal.project.pgotosClone.storage.StorageType;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DownloadService {

    private final PhotosService2 photosService;

    public DownloadService(PhotosService2 photosService) {
        this.photosService = photosService;
    }

    public ResponseEntity<byte[]> helperLocal(Integer id, String showType) throws IOException
    {
        Photo photo = photosService.get(id, StorageType.FILE);

        if(photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Path path = Paths.get(photo.getFilePath());

        byte[] data = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(photo.getContentType()));

        ContentDisposition contentDisposition = ContentDisposition
                .builder(showType)
                .filename(photo.getFileName())
                .build();

        headers.setContentDisposition(contentDisposition);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> helperDatabse(Integer id, String showType) throws IOException
    {
        Photo photo = photosService.get(id, StorageType.DATABASE);

        if(photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        byte[] data = photo.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(photo.getContentType()));

        ContentDisposition contentDisposition = ContentDisposition
                .builder(showType)
                .filename(photo.getFileName())
                .build();

        headers.setContentDisposition(contentDisposition);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

}
