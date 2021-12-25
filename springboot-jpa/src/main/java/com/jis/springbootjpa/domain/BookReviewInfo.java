package com.jis.springbootjpa.domain;

import com.jis.springbootjpa.domain.listener.BaseTimeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Data
public class BookReviewInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Book book;

    private float avgReviewScore;

    private int reviewCnt;
}
