package com.luciorim.techtask.service.impl;

import com.luciorim.techtask.exceptions.StorageException;
import com.luciorim.techtask.service.ImageService;
import com.luciorim.techtask.validators.ValidFile;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.luciorim.techtask.constants.ValueConstants.UPLOADED_FOLDER;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final Path rootLocation;

    public ImageServiceImpl() {
        this.rootLocation = Paths.get(UPLOADED_FOLDER);
    }

    @Override
    public String upload(@ValidFile MultipartFile file) {
        try {
            if (file.getOriginalFilename() == null || file.isEmpty()) {
                throw new StorageException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Failed to store empty file.");
            }

            String filename = UUID.randomUUID() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));


            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(filename))
                    .normalize().toAbsolutePath();


            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Cannot store file outside current directory.");
            }

            @Cleanup
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);

            log.info("Successfully uploaded file: {}", filename);

            return filename;
        } catch (IOException e) {
            log.error("IOException: ", e);
            throw new StorageException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Failed to store file.");
        }
    }

    private Path loadFile(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = loadFile(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new StorageException(HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Could not read file: " + fileName);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            log.info("Create dir: {}", rootLocation);
        } catch (IOException e) {
            log.error("Could not initialize storage.\nIOException: ", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
