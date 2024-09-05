package com.personal.project.pgotosClone.service;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.storage.DatabaseStorage;
import com.personal.project.pgotosClone.storage.FileStorage;
import com.personal.project.pgotosClone.storage.PhotosStorage;
import com.personal.project.pgotosClone.storage.StorageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PhotosService2 {

    private final List<PhotosStorage> photosStorageList;

    @Autowired
    public PhotosService2(List<PhotosStorage> photosStorageList) {
        this.photosStorageList = photosStorageList;
    }

    public PhotosStorage getStorageByType(StorageType storageType) {
        return photosStorageList.stream()
                .filter(ps -> ps.getStorageType().equals(storageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No storage type found: " + storageType));
    }

    public Photo savePhoto(String fileName, String contentType, byte[] data, StorageType storageType) throws IOException
    {
        PhotosStorage storage = getStorageByType(storageType);
        return storage.save(fileName, contentType, data);
    }

    public Photo get(Integer id, StorageType storageType)
    {
        PhotosStorage storage = getStorageByType(storageType);
        return storage.get(id);
    }

    public Iterable<Photo> getAll(StorageType storageType)
    {
        PhotosStorage storage = getStorageByType(storageType);
        return storage.getAll();
    }

    public void delete(Integer id, StorageType storageType)
    {
        PhotosStorage storage = getStorageByType(storageType);
        storage.delete(id);
    }

    public void deleteAll(StorageType storageType)
    {
        PhotosStorage storage = getStorageByType(storageType);
        storage.deleteAll();
    }

}
