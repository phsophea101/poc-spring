package com.sample.spring.web.controller;

import com.sample.spring.dto.UserDto;
import com.sample.spring.mapper.UserMapper;
import com.sample.spring.service.UserService;
import com.sample.spring.web.vo.request.RequestPageableVO;
import com.sample.spring.web.vo.request.UserRequestVo;
import com.sample.spring.web.vo.response.ResponsePageableVO;
import com.sample.spring.web.vo.response.ResponseVO;
import com.sample.spring.web.vo.response.ResponseVOBuilder;
import com.sample.spring.web.vo.response.UserResponseVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("{id}")
    public ResponseVO<UserResponseVo> getUserById(@PathVariable String id) {
        UserDto user = this.service.findById(id);
        return new ResponseVOBuilder<UserResponseVo>().addData(UserMapper.INSTANCE.dtoToVo(user)).build();
    }

    @GetMapping
    public ResponseVO<ResponsePageableVO<UserResponseVo>> getUsers(RequestPageableVO request) {
        Page<UserDto> page = this.service.findByUsers(request);
        List<UserResponseVo> users = UserMapper.INSTANCE.dtoToVoList(page.getContent());
        ResponsePageableVO<UserResponseVo> responseVo = new ResponsePageableVO<>(page.getTotalElements(), users, request);
        return new ResponseVOBuilder<ResponsePageableVO<UserResponseVo>>().addData(responseVo).build();
    }

    @PostMapping
    public ResponseVO<UserResponseVo> save(@RequestBody UserRequestVo vo) {
        UserDto dto = UserMapper.INSTANCE.voToDto(vo);
        this.service.save(dto);
        UserResponseVo responseVo = UserMapper.INSTANCE.dtoToVo(dto);
        return new ResponseVOBuilder<UserResponseVo>().addData(responseVo).build();
    }

}
