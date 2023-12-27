package com.sample.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserDto {
    private String username;
    private String password;
    private boolean locked;
    private String status;
    protected String id;
    protected String createdBy;
    protected Date createdDate = new Date();
    protected String updatedBy;
    protected Date updatedDate;
}
