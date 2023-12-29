package com.sample.spring.service;

import com.sample.spring.dto.UserDto;
import com.sample.spring.web.vo.request.RequestPageableVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void save(UserDto dto);

    void updateUsername(String id, String username);

    Page<UserDto> findByUsers(RequestPageableVO request);


    UserDto findById(String id);
}
