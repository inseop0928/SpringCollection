package com.jis.springbootjpa.sevice;

import com.jis.springbootjpa.dto.HelloResponseDto;
import com.jis.springbootjpa.dto.UserRequest;
import com.jis.springbootjpa.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class RestTemplateService {

    //클라이언트 요청(URIComponent사용)
    //1.주소만들고
    //2.request body 데이터
    //3.응답지정

    public String getHello(){

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8082")
                .path("/api/getHelloUser/{userId}")
                .queryParam("name","jong")
                .queryParam("age",10)
                .encode()
                .build()
                .expand(1) //PathVariable 사용 여러개 사용 시 메소드 안에 콤마를 사용
                .toUri();
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri,String.class);
        //get방식으로 전송
        ResponseEntity<String> result = restTemplate.getForEntity(uri,String.class);

        log.info("response status {}",result.getStatusCode());
        log.info("response body {}",result.getBody());

        return result.getBody();
    }

    public UserResponse postHello(){

       URI uri = UriComponentsBuilder
               .fromUriString("http://localhost:8082")
               .path("/api/postHelloUser")
               .encode()
               .build()
               .toUri();

       log.info(uri.toString());

        UserRequest userRequest = new UserRequest();
        userRequest.setName("JEONG");
        userRequest.setAge(50);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> responseEntity = restTemplate.postForEntity(uri,userRequest, UserResponse.class);

        log.info("response status {}",responseEntity.getStatusCode());
        log.info("response header {}",responseEntity.getHeaders());
        log.info("response body {}",responseEntity.getBody());
        return responseEntity.getBody();
    }

    public UserResponse addHeaderHello(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8082")
                .path("/api/postHelloUser")
                .encode()
                .build()
                .toUri();

        log.info(uri.toString());

        UserRequest userRequest = new UserRequest();
        userRequest.setName("JEONG");
        userRequest.setAge(50);

        RequestEntity<UserRequest> requestRequestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorzation","tokenValue")
                .body(userRequest);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(requestRequestEntity, UserResponse.class);

        return responseEntity.getBody();
    }

}
