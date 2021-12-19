package com.jis.springbootjpa.domain.listener;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass//컬럼들을 자동 하위 클래스에 상속해줌
@EntityListeners(AuditingEntityListener.class)//Auditing 기능을 포함
public abstract class BaseTimeEntity {

    @CreatedDate//entity 생성시 시간 저장
    protected LocalDateTime createDate;

    @LastModifiedDate//entity 조회한 값을 변경할 때 시간이 저장
    protected LocalDateTime modifiedDate;
}
