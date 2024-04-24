package com.luciorim.techtask.mapper;

import com.luciorim.techtask.dto.response.ResponseNewsDto;
import com.luciorim.techtask.model.News;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper(componentModel = "spring")
public interface NewsMapper extends BaseMapper<News, ResponseNewsDto> {

    @Override
    default ResponseNewsDto toDto(News news) {
        ResponseNewsDto newsDto = new ResponseNewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setContent(news.getContent());
        newsDto.setCreatedAt(news.getCreatedAt());
        if(news.getTitleImageUrl() != null){
            newsDto.setTitleImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/api/images/" + news.getTitleImageUrl());
        }
        return newsDto;
    }

}
