package com.jis.springbootjpa;

import com.jis.springbootjpa.domain.repository.BookRepository;
import com.jis.springbootjpa.domain.Books;
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
public class BooksTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    BookRepository bookRepository;

    @After
    public void cleanUp(){
        bookRepository.deleteAll();
    }

    @Test
    public void load(){

        String title = "제목";
        String content = "본문";

        bookRepository.save(Books.builder().title(title).content(content).author("tester").build());

        List<Books> booksList = bookRepository.findAll();

        logger.info("start");
        booksList.forEach(System.out::println);

        Books book = booksList.get(0);
        assertThat(book.getTitle()).isEqualTo(title);

    }

    @Test
    public void save(){
        String title = "제목";
        String content = "본문";
        String author = "저자";

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url  = "http://localhost:"+port+"/api/book/save";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,bookRequestDto,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
    }

    @Test
    public void update(){
        String title = "초기제목";
        String content = "초기본문";
        String author = "초기저자";

        Books putData = bookRepository.save(Books.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long updateId = putData.getId();
        String updateTitle = "수정 제목";
        String updateContent = "수정 본문";

        BookRequestDto updateBookRequestDto = BookRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url  = "http://localhost:"+port+"/api/book/update/"+updateId;

        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(updateBookRequestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Books> booksList = bookRepository.findAll();
        assertThat(booksList.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(booksList.get(0).getContent()).isEqualTo(updateContent);
    }
}