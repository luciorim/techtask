package com.luciorim.techtask.service;

import com.luciorim.techtask.dto.request.RequestCreateNewsDto;
import com.luciorim.techtask.dto.response.ResponseNewsDto;

import java.util.List;

public interface NewsService {
    void postNews(RequestCreateNewsDto requestCreateNewsDto);

    ResponseNewsDto getNewsById(Long id);

    List<ResponseNewsDto> getAllNews();

    void deleteNewsById(Long id);
}
