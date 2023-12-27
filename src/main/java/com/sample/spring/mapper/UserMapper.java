package com.sample.spring.mapper;

import com.sample.spring.dto.UserDto;
import com.sample.spring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(UserEntity entity);

    UserEntity dtoToEntity(UserDto dto);

    void copy(UserEntity dtoA, @MappingTarget UserEntity dtoB);

    List<UserDto> entityToDtoList(List<UserEntity> byUsers);

}
