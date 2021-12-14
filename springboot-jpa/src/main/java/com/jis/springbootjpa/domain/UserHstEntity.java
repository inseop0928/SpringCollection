package com.jis.springbootjpa.domain;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class UserHstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Enumerated(value = EnumType.STRING)//String으로 선언하지 않으면 index로 리턴하는데 순서 변경 시 값이 꼬일 수가 있다.
    private Gender gender;

    @Column(nullable = true, insertable = true)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
