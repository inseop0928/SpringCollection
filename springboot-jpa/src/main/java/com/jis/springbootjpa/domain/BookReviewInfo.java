package com.jis.springbootjpa.domain;

import com.jis.springbootjpa.domain.listener.BaseTimeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@Data
public class BookReviewInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long bookId;

    private float avgReviewScore;

    private int reviewCnt;
}
