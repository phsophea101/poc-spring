package com.sample.spring.service.impl;

import com.sample.spring.dto.UserDto;
import com.sample.spring.entity.UserEntity;
import com.sample.spring.mapper.UserMapper;
import com.sample.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate template;

    @Override
    public void save(UserDto dto) {
        this.template.save(UserMapper.INSTANCE.dtoToEntity(dto));
    }

    @Override
    public void updateUsername(String id, String username) {
        UserEntity entity = this.template.findById(id, UserEntity.class);
        entity.setUsername(username);
        this.template.save(entity);
    }

    @Override
    public List<UserDto> findByUsers() {
        return UserMapper.INSTANCE.entityToDtoList(this.template.findAll(UserEntity.class));
    }

    @Override
    public UserDto findById(String id) {
        return UserMapper.INSTANCE.entityToDto(this.template.findById(id, UserEntity.class));
    }
}
