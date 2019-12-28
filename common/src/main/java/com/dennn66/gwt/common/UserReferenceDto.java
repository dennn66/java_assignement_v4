package com.dennn66.gwt.common;


import java.io.Serializable;


public class UserReferenceDto implements Serializable{
    private Long id;//id,
    private String username;// user,
    private String email;// ,

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserReferenceDto() {
    }

    public UserReferenceDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserReferenceDto(Long id) {
        this.id = id;
    }
}
