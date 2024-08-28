package com.personal.project.pgotosClone.web;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.service.PhotosService;
import jdk.jfr.ContentType;
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
public class DownloadController {

    private final PhotosService photosService;

    public DownloadController(PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) throws IOException
    {
        Photo photo = photosService.get(id);

        if(photo == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Photo is not found");
        }

        Path path = Paths.get(photo.getFilePath());

        byte[] data = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(photo.getContentType()));

        ContentDisposition build = ContentDisposition
                .builder("inline")
                .filename(photo.getFileName())
                .build();

        headers.setContentDisposition(build);


        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
