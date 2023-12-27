package com.sample.spring.service.impl;

import com.sample.spring.dto.UserDto;
import com.sample.spring.entity.UserEntity;
import com.sample.spring.mapper.UserMapper;
import com.sample.spring.service.UserRepository;
import com.sample.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private static final String QUERY_ALL = "SELECT * from " + UserEntity.TABLE_NAME;
    private static final String QUERY_BY_ID = QUERY_ALL + " WHERE " + UserEntity.Fields.id + " =:id ";

    @Override
    public void save(UserDto dto) {
        this.repository.save(UserMapper.INSTANCE.dtoToEntity(dto));
    }

    @Override
    public void updateUsername(String id, String username) {
        UserEntity entity = this.repository.getById(Long.valueOf(id));
        entity.setUsername(username);
        this.repository.save(entity);
    }

    @Override
    public List<UserDto> findByUsers() {
        return UserMapper.INSTANCE.entityToDtoList(this.repository.findAll());
    }

    @Override
    public UserDto findById(String id) {
        return UserMapper.INSTANCE.entityToDto(this.repository.getById(Long.valueOf(id)));
    }
}
