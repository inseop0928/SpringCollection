package com.jis.springbootjpa.sevice;

import com.jis.springbootjpa.domain.BookRepository;
import com.jis.springbootjpa.dto.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor//autowired 대신 사용
@Service
public class BookService {
    private final BookRepository bookRepository;
    
    @Transactional
    public Long save(BookRequestDto bookRequestDto){
       return bookRepository.save(bookRequestDto.toEntity()).getId();
    }
}
