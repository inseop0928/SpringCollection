package com.jis.springbootjpa.type;


import lombok.*;

@Data
@EqualsAndHashCode//equalsTo시 참조값이 아닌 값까지 비교
@AllArgsConstructor
@NoArgsConstructor
public class IpPort {

    private String ip;
    private int port;
}
