package com.personal.project.pgotosClone.service;

import com.personal.project.pgotosClone.model.Photo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@Service
public class PhotosService {
    private static int id = 0;
    private Set<Integer> deletedIds = new HashSet<>();

    private Map<Integer, Photo> db = new HashMap<>(){{
        put(++id, new Photo(1, "hello.jpeg"));

    }};
    private final String uploadDir = "/Users/martinmenchev/development/JavaWork/pgotosClone/src/main/resources/uploads/";

    public PhotosService()
    {
        File directory = new File(uploadDir);
    }

    public Collection<Photo> getAll() {
        return db.values();
    }

    public Photo get(Integer id) {
        return db.get(id);
    }

    public Photo remove(Integer id) {
        deletedIds.add(id);
        return db.remove(id);
    }

    public void deleteAll()
    {
        db.clear();
        id = 0;
        deletedIds.clear();
    }

    public void add(Integer id) {

    }

//    public Photo create(MultipartFile file) throws IOException
//    {
//        Photo photo = new Photo();
//
//        int newId;
//
//        if(!deletedIds.isEmpty())
//        {
//            newId = deletedIds.iterator().next();
//            deletedIds.remove(newId);
//        }
//        else {
//            newId = ++id;
//        }
//
//        photo.setId(newId);
//        photo.setFileName(file.getOriginalFilename());
//        photo.setData(file.getBytes());
//
//        db.put(photo.getId(),photo);
//
//        return photo;
//    }

    public Photo put(String fileName, String contentType, byte[] data) throws IOException {
        Photo photo = new Photo();

        int newId;

        if(!deletedIds.isEmpty())
        {
            newId = deletedIds.iterator().next();
            deletedIds.remove(newId);
        }
        else {
            newId = ++id;
        }

        String filePath = uploadDir + newId + "_" + fileName;
        Path path = Paths.get(filePath);

        Files.write(path,data);

        photo.setFilePath(filePath);
        photo.setContentType(contentType);

        photo.setId(newId);
        photo.setFileName(fileName);
        photo.setData(data);


        db.put(photo.getId(),photo);

        return photo;
    }
}
