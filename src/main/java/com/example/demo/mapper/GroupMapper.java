package com.example.demo.mapper;

import com.example.demo.entity.Group;
import org.mapstruct.Mapper;
import org.openapitools.model.GroupDTO;
import org.openapitools.model.GroupShortInfoDTO;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface GroupMapper {
    GroupDTO toDTO(Group model);
    Group toModel(GroupDTO dto);
    Group toGetId(GroupShortInfoDTO groupShortInfoDTO);
}
