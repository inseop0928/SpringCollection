package com.jis.springbootjpa.dto;

import com.jis.springbootjpa.domain.Books;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    //필드 중 일부만 사용
    @Builder
    public BookResponseDto(Books entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }


}
