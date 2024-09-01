package com.personal.project.pgotosClone.storage;

import com.personal.project.pgotosClone.model.Photo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class FileStorage implements PhotosStorage{

    private final Map<Integer,Photo> db = new HashMap<>();

    private static int id = 0;
    private final Set<Integer> deletedIds = new HashSet<>();

    private final String uploadDir = "/Users/martinmenchev/development/JavaWork/pgotosClone/src/main/resources/uploads/";

        public FileStorage() throws IOException {
        File directory = new File(uploadDir);
        startUpInit();
    }


    @PostConstruct
    public void startUpInit()
    {
        File directory = new File(uploadDir);
        File[] files = directory.listFiles();

            if(files != null)
            {
                for (File file : files)
                {
                    try {

                        String fileName = file.getName();
                        String[] parts = fileName.split("\\.",2);

                        int photoId = Integer.parseInt(parts[0]);
                        byte[] data = Files.readAllBytes(file.toPath());

                        String contentType = Files.probeContentType(file.toPath());

                        Photo photo = new Photo(photoId,fileName);
                        photo.setData(data);
                        photo.setContentType(contentType);
                        photo.setFilePath(file.getAbsolutePath());


                        db.put(photoId,photo);
                        id = Math.max(id,photoId);


                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
    }

    private int getNextId()
    {
        if(!deletedIds.isEmpty())
        {
            int newId = deletedIds.iterator().next();
            deletedIds.remove(newId);
            return newId;
        }
        else {
            return ++id;
        }

    }

    public String helper(String type) throws IOException {
            switch(type)
            {
                case "image/png":
                    return "png";
                case "image/jpeg":
                    return "jped";
                case "image/gif":
                    return "gif";
                default:
                    throw new IllegalArgumentException("Unsupported file type " + type);
            }
    }

    @Override
    public Photo save(String fileName, String contentType, byte[] data) throws IOException {
        Photo photo = new Photo();

        int newId;

        if(!deletedIds.isEmpty())
        {
            newId = deletedIds.iterator().next();
            deletedIds.remove(newId);
        }
        else
        {
            newId = ++id;
        }

        String filePath = uploadDir + newId + "." + helper(contentType.toLowerCase());
        Path path = Paths.get(filePath);
        Files.write(path, data);

        photo.setFileName(fileName);
        photo.setId(newId);
        photo.setFilePath(filePath);
        photo.setContentType(contentType);
        photo.setData(data);

        db.put(photo.getId(),photo);

        return photo;
    }

    @Override
    public Photo get(Integer id) {
        return db.get(id);
    }

    @Override
    public void delete(Integer id) {
            deletedIds.add(id);

            File directory = new File(uploadDir);

            File[] files = directory.listFiles();

            if(files != null)
            {
                for(File file : files)
                {
                    String fileName = file.getName();
                    String[] parts = fileName.split("\\.",2);

                    if(Objects.equals(parts[0],id.toString()))
                    {
                        if(file.delete())
                        {
                            System.out.println("File " + fileName + " deleted :(");
                        }
                        else {
                            System.out.println("Failed to delete: " + fileName);
                        }
                    }
                }
            }
            Photo photo = db.remove(id);
    }

    @Override
    public Collection<Photo> getAll() {
        return db.values();
    }

    @Override
    public void deleteAll() {
            db.clear();
            id = 0;
            deletedIds.clear();

            File directory = new File(uploadDir);
            File[] files = directory.listFiles();

            if(files != null)
            {
                for(File file : files)
                {
                    String fileName = file.getName();
                    if(file.delete()) {
                        System.out.println(fileName + " successfully deleted!");
                    } else {
                        System.out.println("Error :(");
                    }
                }
            }
    }
}
