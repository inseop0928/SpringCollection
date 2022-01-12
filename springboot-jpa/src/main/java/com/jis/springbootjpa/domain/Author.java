package com.jis.springbootjpa.domain;

import com.jis.springbootjpa.domain.listener.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Author extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @ManyToMany//manyToMany는 각각 테이블에 pk를 가지고 있는 중간 테이블을 구성한다.
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        this.books.add(book);
    }

}
