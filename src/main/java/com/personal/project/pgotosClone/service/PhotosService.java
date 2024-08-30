package com.personal.project.pgotosClone.service;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.repository.PhotosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PhotosService {


    private final PhotosRepo photosRepo;

    private static int id = 0;
    private Set<Integer> deletedIds = new HashSet<>();




//    private Map<Integer, Photo> db = new HashMap<>(){{
//        put(++id, new Photo(1, "hello.jpeg"));
//
//    }};
//    private final String uploadDir = "/Users/martinmenchev/development/JavaWork/pgotosClone/src/main/resources/uploads/";

//    public PhotosService() throws IOException {
//        File directory = new File(uploadDir);
//        startUpInit();
//    }


//    @PostConstruct
//    public void startUpInit()
//    {
//        File directory = new File(uploadDir);
//        File[] files = directory.listFiles();
//
//            if(files != null)
//            {
//                for (File file : files)
//                {
//                    try {
//
//                        String fileName = file.getName();
//                        String[] parts = fileName.split("\\.",2);
//
//                        int photoId = Integer.parseInt(parts[0]);
//                        byte[] data = Files.readAllBytes(file.toPath());
//
//                        String contentType = Files.probeContentType(file.toPath());
//
//                        Photo photo = new Photo(photoId,fileName);
//                        photo.setData(data);
//                        photo.setContentType(contentType);
//                        photo.setFilePath(file.getAbsolutePath());
//
//
//                        db.put(photoId,photo);
//                        id = Math.max(id,photoId);
//
//
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//    }


    public PhotosService(PhotosRepo photosRepo) {
        this.photosRepo = photosRepo;
    }

    public Iterable<Photo> getAll() {
        return photosRepo.findAll();
    }

    public Photo get(Integer id) {
        return photosRepo.findById(id).orElse(null);
    }

//    public Photo remove(Integer id) {
//        deletedIds.add(id);
//        File directory = new File(uploadDir);
//        File[] files = directory.listFiles();
//
//        if(files != null)
//        {
//            for(File file : files)
//            {
//                String fileName = file.getName();
//                String[] parts = fileName.split("\\.",2);
//
//                if(Objects.equals(parts[0], id.toString()))
//                {
//                    if(file.delete())
//                    {
//                        System.out.println("File " + fileName + " deleted from folder");
//                    }
//                    else {
//                        System.out.println("Failed to delete file " + fileName);
//                    }
//                }
//            }
//        }
//        return db.remove(id);
//    }
//
//    public void deleteAll()
//    {
//        db.clear();
//        id = 0;
//        deletedIds.clear();
//    }

    public void delete(Integer id)
    {
        photosRepo.deleteById(id);
    }

    public void deleteAll()
    {
        photosRepo.deleteAll();
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

//    public String helper(String type) throws IOException {
//        switch (type)
//        {
//            case "image/jpeg":
//                return "jpeg";
//            case "image/png":
//                return "png";
//            case "image/gif":
//                return "gif";
//            default:
//                throw new IllegalArgumentException("Unsupported file type:" + type);
//        }
//    }

//    public Photo put(String fileName, String contentType, byte[] data) throws IOException {
//        Photo photo = new Photo();
//
//        int newId;
//
//        if(!deletedIds.isEmpty()) {
//            newId = deletedIds.iterator().next();
//            deletedIds.remove(newId);
//        } else {
//            newId = ++id;
//        }
//
//        String filePath = uploadDir + newId + "." + helper(contentType.toLowerCase());
//
//        Path path = Paths.get(filePath);
//
//        Files.write(path,data);
//
//        photo.setFilePath(filePath);
//        photo.setContentType(contentType);
//        photo.setId(newId);
//        photo.setFileName(fileName);
//        photo.setData(data);
//
//
//        db.put(photo.getId(),photo);
//
//        return photo;
//    }

    public Photo put(String fileName, String contentType, byte[] data) {
        Photo photo = new Photo();

        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setData(data);

        photosRepo.save(photo);

        return photo;
    }
}
