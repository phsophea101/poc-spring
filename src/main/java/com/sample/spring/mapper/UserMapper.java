package com.sample.spring.mapper;

import com.sample.spring.dto.UserDto;
import com.sample.spring.entity.UserEntity;
import com.sample.spring.web.vo.request.UserRequestVo;
import com.sample.spring.web.vo.response.UserResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(UserEntity entity);

    UserEntity dtoToEntity(UserDto dto);

    UserDto voToDto(UserRequestVo vo);

    UserResponseVo dtoToVo(UserDto dto);

    List<UserDto> entityToDtoList(List<UserEntity> byUsers);

    List<UserResponseVo> dtoToVoList(List<UserDto> users);
}
