package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.BookRequestDto;
import com.jis.springbootjpa.sevice.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/book/save")
    public Long save(@RequestBody BookRequestDto bookRequestDto){
        return bookService.save(bookRequestDto);
    }

}
