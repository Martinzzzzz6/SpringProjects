package com.personal.project.pgotosClone.storage;

import com.personal.project.pgotosClone.model.Photo;
import com.personal.project.pgotosClone.repository.PhotosRepo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
public class DatabaseStorage implements PhotosStorage {
    private final PhotosRepo photosRepo;

    public DatabaseStorage(PhotosRepo photosRepo)
    {
        this.photosRepo = photosRepo;
    }

    @Override
    public Photo save(String fileName, String contentType, byte[] data) throws IOException {
        Photo photo = new Photo();

        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setData(data);

        return photosRepo.save(photo);
    }

    @Override
    public Photo get(Integer id) {
        return photosRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        photosRepo.deleteById(id);
    }

    @Override
    public Iterable<Photo> getAll() {
        return photosRepo.findAll();
    }

    @Override
    public void deleteAll() {
        photosRepo.deleteAll();
    }

}
