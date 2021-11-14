package com.jis.springbootjpa.dto;

import com.jis.springbootjpa.dto.model.Error;

import java.util.List;

public class ErrorResponseDto {

    private String statusCode;
    private String requestUrl;
    private String code;
    private String message;
    private String resultCode;

    List<Error> errors;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
                "statusCode='" + statusCode + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errors=" + errors +
                '}';
    }
}
