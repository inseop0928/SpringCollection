package com.jis.springbootjpa.dto;

import com.jis.springbootjpa.domain.Books;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
public class BookRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public BookRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Books toEntity() {
        return Books.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

    @Override
    public String toString() {
        return "BookRequestDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
