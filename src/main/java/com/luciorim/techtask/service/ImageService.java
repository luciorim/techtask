package com.luciorim.techtask.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile file);

    Resource load(String fileName);

    void init();

    void deleteAll();
}
