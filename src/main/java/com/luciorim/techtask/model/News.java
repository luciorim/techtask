package com.luciorim.techtask.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class News extends BaseEntity{
    private String title;
    private String content;
    private String titleImageUrl;
}
