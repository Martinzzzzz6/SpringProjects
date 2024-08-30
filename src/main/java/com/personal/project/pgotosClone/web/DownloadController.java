package com.personal.project.pgotosClone.web;

import com.personal.project.pgotosClone.service.DownloadService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/local/show/{id}")
    public ResponseEntity<byte[]> showLocal(@PathVariable Integer id) throws IOException
    {
        return downloadService.helperLocal(id,"inline");
    }

    @GetMapping("/local/download/{id}")
    public ResponseEntity<byte[]> downloadLocal(@PathVariable Integer id) throws IOException
    {
       return downloadService.helperLocal(id, "attachment");
    }

    @GetMapping("/database/show/{id}")
    public ResponseEntity<byte[]> showDatabase(@PathVariable Integer id) throws IOException
    {
        return downloadService.helperDatabse(id,"inline");
    }

    @GetMapping("/database/download/{id}")
    public ResponseEntity<byte[]> downloadDatabase(@PathVariable Integer id) throws IOException
    {
        return downloadService.helperDatabse(id, "attachment");
    }
}
