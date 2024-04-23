package com.luciorim.techtask.service.impl;

import com.luciorim.techtask.dto.request.RequestCreateNewsDto;
import com.luciorim.techtask.dto.response.ResponseNewsDto;
import com.luciorim.techtask.dto.response.ResponseUserDto;
import com.luciorim.techtask.exceptions.DbObjectNotFoundException;
import com.luciorim.techtask.mapper.NewsMapper;
import com.luciorim.techtask.model.News;
import com.luciorim.techtask.repository.NewsRepository;
import com.luciorim.techtask.service.ImageService;
import com.luciorim.techtask.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.luciorim.techtask.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ImageService imageService;
    private final NewsMapper newsMapper;


    @Override
    public void postNews(RequestCreateNewsDto requestCreateNewsDto) {
        News news = new News();
        news.setTitle(requestCreateNewsDto.getTitle());
        news.setContent(requestCreateNewsDto.getContent());
        news.setCreatedAt(LocalDateTime.now(ZONE_ID));

        if(requestCreateNewsDto.getNewsPicture() != null){
            MultipartFile newsPicture = requestCreateNewsDto.getNewsPicture();
            String filename = imageService.upload(newsPicture);

            news.setTitleImageUrl(filename);
        }

        log.info("Created new news: {}", news);
        newsRepository.save(news);
    }

    @Override
    public ResponseNewsDto getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() ->  new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "News with id " + id + " not found"));

        return newsMapper.toDto(news);
    }

    @Override
    public List<ResponseNewsDto> getAllNews() {
        List<News> news = newsRepository.findAll();
        return newsMapper.toDto(news);
    }

    @Override
    public void deleteNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() ->  new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "News with id " + id + " not found"));

        newsRepository.delete(news);
    }


}
