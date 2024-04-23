package com.luciorim.techtask.controller;

import com.luciorim.techtask.dto.request.RequestCreateNewsDto;
import com.luciorim.techtask.dto.response.ResponseNewsDto;
import com.luciorim.techtask.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> postNews(@Valid @ModelAttribute RequestCreateNewsDto requestCreateNewsDto){
        newsService.postNews(requestCreateNewsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseNewsDto>> getAllNews(){
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseNewsDto> getNewsById(@PathVariable Long id){
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsById(@PathVariable Long id){
        newsService.deleteNewsById(id);
        return ResponseEntity.ok().build();
    }
}
