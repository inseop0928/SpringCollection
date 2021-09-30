package com.jis.springbootjpa.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass//jpa entity 클래스들이 상속할 경우 필드들도 컴럼으로 인식
@EntityListeners(AuditingEntityListener.class)//Auditing 기능을 포함
public abstract class BaseTimeEntity {

    @CreatedDate//entity 생성시 시간 저장
    private LocalDateTime createDate;

    @LastModifiedDate//entity 조회한 값을 변경할 때 시간이 저장
    private LocalDateTime modifiedDate;
}