package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.domain.Books;
import com.jis.springbootjpa.dto.BookRequestDto;
import com.jis.springbootjpa.dto.BookResponseDto;
import com.jis.springbootjpa.sevice.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/book")
@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookResponseDto findById(@PathVariable Long id) {

        return bookService.findById(id);
    }

    @PostMapping("/save")
    public Long save(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }


}
