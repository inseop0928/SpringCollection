package com.jis.springbootjpa;

import com.jis.springbootjpa.domain.Book;
import com.jis.springbootjpa.domain.BookReviewInfo;
import com.jis.springbootjpa.domain.Books;
import com.jis.springbootjpa.domain.repository.BookRepository;
import com.jis.springbootjpa.domain.repository.BookReviewInfoRepository;
import com.jis.springbootjpa.domain.repository.BooksRepository;
import com.jis.springbootjpa.dto.BookRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/*@WebMvcTest의 경우 JPA기능은 작동 X 따라서 @SpringBootTest를  사용*/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @LocalServerPort
    private int port;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @After
    public void cleanUp() {
       // bookRepository.deleteAll();
    }

    @Test
    public void bookReviewCrud(){
        Book book = new Book();
        book.setAuthorId(1L);
        book.setName("1번째 책");
        book.setCategory("소설");
        bookRepository.save(book);

        logger.info("Book >> {}",bookRepository.findAll().toString());

        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        //bookReviewInfo.setId(1L);
        bookReviewInfo.setBook(book);
        bookReviewInfo.setAvgReviewScore(4);
        bookReviewInfo.setReviewCnt(2);

        bookReviewInfoRepository.save(bookReviewInfo);

        logger.info("BookReview >> {}", bookReviewInfoRepository.findAll().toString());

        Book result = bookReviewInfoRepository.findById(1L).orElseThrow(RuntimeException::new).getBook();

        System.out.println(">>>> " + result);
    }
}