package com.personal.project.pgotosClone.repository;

import com.personal.project.pgotosClone.model.Photo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotosRepo extends CrudRepository<Photo, Integer> {

}
