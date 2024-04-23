package com.luciorim.techtask.controller;

import com.luciorim.techtask.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody Resource getImageByFileName(@PathVariable("filename") String imageName) {
        log.info("Getting image by name {}", imageName);
        return imageService.load(imageName);
    }
}
