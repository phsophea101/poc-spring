package com.sample.spring.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Setter
@Getter
@Document(collection = UserEntity.TABLE_NAME)
@FieldNameConstants
public class UserEntity {
    public static final String TABLE_NAME = "users";
    @Id
    @Indexed
    protected String id;
    @Indexed(unique = true)
    private String username;
    @Indexed
    private String password;
    @Indexed
    private String status;
    private boolean locked;
    @Field("created_by")
    @CreatedBy
    protected String createdBy;
    @Field("created_date")
    @CreatedDate
    protected Date createdDate;
    @Field("updated_by")
    @LastModifiedBy
    protected String updatedBy;
    @Field("updated_date")
    @LastModifiedDate
    protected Date updatedDate;
}
