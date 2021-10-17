package com.jis.springbootjpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

//NULL값은 포함하지 않겠다는 의미
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private  Long userId;
    private  String userName;
    private String userEmail;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
