package com.dennn66.tasktracker.mappers;

import com.dennn66.gwt.common.UserReferenceDto;
import com.dennn66.tasktracker.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserReferenceMapper {
    UserReferenceMapper MAPPER = Mappers.getMapper(UserReferenceMapper.class);
    User toUser(UserReferenceDto userDto);
    @InheritInverseConfiguration
    UserReferenceDto fromUser(User user);
    List<User> toUserList(List<UserReferenceDto> userDtos);
    List<UserReferenceDto> fromUserList(List<User> users);
}
