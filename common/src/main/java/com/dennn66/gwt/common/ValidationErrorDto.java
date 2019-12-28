package com.dennn66.gwt.common;

public class ValidationErrorDto {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationErrorDto() {
    }

    public ValidationErrorDto(String message) {
        this.message = message;
    }
}
