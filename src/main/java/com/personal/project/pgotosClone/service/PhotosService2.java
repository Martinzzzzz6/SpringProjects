package com.personal.project.pgotosClone.service;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.storage.DatabaseStorage;
import com.personal.project.pgotosClone.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PhotosService2 {
    private final DatabaseStorage databaseStorage;
    private final FileStorage fileStorage;

    @Autowired
    public PhotosService2(DatabaseStorage databaseStorage, FileStorage fileStorage) {
        this.databaseStorage = databaseStorage;
        this.fileStorage = fileStorage;
    }

    public Photo savePhoto(String fileName, String contentType, byte[] data, boolean saveToDatabase) throws IOException
    {
        if(saveToDatabase)
        {
            return databaseStorage.save(fileName,contentType,data);
        }
        else
        {
            return fileStorage.save(fileName,contentType,data);
        }
    }

    public Photo getPhotoLocal(Integer id)
    {
        return fileStorage.get(id);
    }

    public Photo getPhotoDatabase(Integer id)
    {
        return databaseStorage.get(id);
    }

    public Iterable<Photo> getAllPhotosLocal()
    {
        return fileStorage.getAll();
    }

    public Iterable<Photo> getAllPhotosDatabase()
    {
        return databaseStorage.getAll();
    }

    public void deletePhotoDatabase(Integer id)
    {
        databaseStorage.delete(id);
    }

    public void deletePhotoLocal(Integer id)
    {
        fileStorage.delete(id);
    }

    public void deleteAllDatabase()
    {
        databaseStorage.deleteAll();
    }

    public void deleteAllLocal()
    {
        fileStorage.deleteAll();
    }

}
