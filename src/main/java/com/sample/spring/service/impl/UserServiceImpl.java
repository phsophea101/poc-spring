package com.sample.spring.service.impl;

import com.sample.spring.conts.BizErrorCode;
import com.sample.spring.dto.UserDto;
import com.sample.spring.entity.UserEntity;
import com.sample.spring.exception.BizException;
import com.sample.spring.mapper.UserMapper;
import com.sample.spring.service.UserRepository;
import com.sample.spring.service.UserService;
import com.sample.spring.web.vo.request.RequestPageableVO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public void save(UserDto dto) {
        UserEntity entity = this.repository.save(UserMapper.INSTANCE.dtoToEntity(dto));
        dto.setId(String.valueOf(entity.getId()));
    }

    @Override
    public void updateUsername(String id, String username) {
        UserEntity entity = this.repository.getById(Long.valueOf(id));
        entity.setUsername(username);
        this.repository.save(entity);
    }

    @Override
    public Page<UserDto> findByUsers(RequestPageableVO request) {
        UserEntity entity = new UserEntity();
        entity.setStatus("ACTIVE");
        Page<UserEntity> entityPage = this.repository.findAll(Example.of(entity), Pageable.ofSize(request.getRpp()).withPage(request.getPage() - 1));
        return new PageImpl<>(UserMapper.INSTANCE.entityToDtoList(entityPage.getContent()), PageRequest.of(request.getPage() - 1, request.getRpp()), entityPage.getTotalElements());
    }

    @SneakyThrows
    @Override
    public UserDto findById(String id) {
        Optional<UserEntity> user = this.repository.findById(Long.valueOf(id));
        if (user.isEmpty())
            throw new BizException(BizErrorCode.E0002);
        return UserMapper.INSTANCE.entityToDto(user.get());
    }
}
