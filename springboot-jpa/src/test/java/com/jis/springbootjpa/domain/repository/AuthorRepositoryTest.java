package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.Author;
import com.jis.springbootjpa.domain.Book;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    void manyToMany(){
        Book book1 = saveBook("book1");
        Book book2 = saveBook("book2");

        Author author1 = saveAuthor("저자1");
        Author author2 = saveAuthor("저자2");

        book1.addAuthor(author1);
        book1.addAuthor(author2);
        //book1.setAuthors(Lists.newArrayList(author1));
        //book2.setAuthors(Lists.newArrayList(author1,author2));

        author1.setBooks(Lists.newArrayList(book1,book2));

        bookRepository.saveAll(Lists.newArrayList(book1,book2));
        authorRepository.saveAll(Lists.newArrayList(author1));



    }

    private Book saveBook(String name){
        Book book = new Book();
        book.setName(name);

        return bookRepository.save(book);
    }

    private Author saveAuthor(String name){
        Author author = new Author();
        author.setName(name);

        return authorRepository.save(author);
    }

}