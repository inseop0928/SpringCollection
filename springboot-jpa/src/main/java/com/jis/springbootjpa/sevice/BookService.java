package com.jis.springbootjpa.sevice;

import com.jis.springbootjpa.domain.repository.BookRepository;
import com.jis.springbootjpa.domain.Books;
import com.jis.springbootjpa.dto.BookRequestDto;
import com.jis.springbootjpa.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor//autowired 대신 사용
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Long save(BookRequestDto bookRequestDto) {
        return bookRepository.save(bookRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BookRequestDto bookRequestDto) {

        Books books = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재 하지 않습니다. id=" + id));

        //jpa 영속성 컨텍스트 때문에 따로 업데이트를 날리지 앖는다.
        books.update(bookRequestDto.getTitle(), bookRequestDto.getContent());
        return id;
    }

    public BookResponseDto findById(Long id) {

        Books books = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new BookResponseDto(books);
    }
}
