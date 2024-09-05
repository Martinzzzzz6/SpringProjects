package com.personal.project.pgotosClone.storage;

import com.personal.project.pgotosClone.model.Photo;

import java.io.IOException;

public interface PhotosStorage {
    Photo save(String fileName, String contentType, byte[] data) throws IOException;
    Photo get(Integer id);
    void delete(Integer id);
    Iterable<Photo> getAll();
    void deleteAll();
    StorageType getStorageType();
}
