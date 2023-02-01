package com.enchere.service;

import com.enchere.model.Photo;
import com.enchere.repo.PhotoRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

@Service
public class PhotoService extends CrudService<Photo, PhotoRepo> {
    public PhotoService(PhotoRepo repo) {
        super(repo);
    }
}
