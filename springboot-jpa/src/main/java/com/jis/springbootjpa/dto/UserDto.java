package com.jis.springbootjpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.jis.springbootjpa.aop.YearMonth;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

//NULL값은 포함하지 않겠다는 의미
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long userId;
    private String userName;

    @Max(value = 100)
    private int age;

    @Size(min = 8, max = 8,message = "yyyyMMdd 형태로 입력하시기 바랍니다.")
    private String requestDate;

    @YearMonth
    private String requestYearMonth;

    //@Email//validation 검사
    private String userEmail;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다.xxx-xxxx-xxxx")
    private String userPhoneNumber;

    @Valid//특정클래스를 검사를 하고 싶으면 넣어줘야함 class안에 validation 관련 어노테이션을 넣어도 해당어노테이션이 빠지만 안됨
    private List<CarRequestDto> carRequestDtoList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRequestYearMonth() {
        return requestYearMonth;
    }
    public void setRequestYearMonth(String requestYearMonth) {
        this.requestYearMonth = requestYearMonth;
    }

    @AssertTrue//true이면 정상
    public boolean isRequestDateValidation(){//is변수명Validation을 해야 동작

        try {
            LocalDate localDate = LocalDate.parse(this.requestDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public String getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public List<CarRequestDto> getCarRequestDtoList() {
        return carRequestDtoList;
    }

    public void setCarRequestDtoList(List<CarRequestDto> carRequestDtoList) {
        this.carRequestDtoList = carRequestDtoList;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", requestDate='" + requestDate + '\'' +
                ", requestYearMonth='" + requestYearMonth + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", carRequestDtoList=" + carRequestDtoList +
                '}';
    }
}
