package com.sample.spring.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = UserEntity.TABLE_NAME)
@FieldNameConstants
public class UserEntity {
    public static final String TABLE_NAME = "users";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String status;
    private boolean locked;
    @CreatedBy
    protected String createdBy;
    @CreatedDate
    protected Date createdDate;
    @LastModifiedBy
    protected String updatedBy;
    @LastModifiedDate
    protected Date updatedDate;
}
