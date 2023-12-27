package com.sample.spring.service;

import com.sample.spring.dto.UserDto;

import java.util.List;

public interface UserService {

    void save(UserDto dto);

    void updateUsername(String id,String username);

    List<UserDto> findByUsers();

    UserDto findById(String id);
}
