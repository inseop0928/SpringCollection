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
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    //private Long publisherId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude//순환참조가 걸리므로 적용
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Review> reviewList = new ArrayList<Review>();

    @ManyToOne
    @ToString.Exclude
    private Publisher publisher;

    @ManyToMany
    @ToString.Exclude//순환참조가 걸리므로 적용
    private List<Author> authors = new ArrayList<>();

    public void addAuthor(Author author){
        this.authors.add(author);
    }

}
