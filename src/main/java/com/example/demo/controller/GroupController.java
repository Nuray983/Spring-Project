package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.mapper.GroupMapper;
import com.example.demo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.GroupsApi;
import org.openapitools.model.GroupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupsApi {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Override
    public ResponseEntity<GroupDTO> createGroup(GroupDTO groupDTO) {
        Group group = groupMapper.toModel(groupDTO);
        groupService.createGroup(group);
        return ResponseEntity.ok(groupDTO);
    }


    @Override
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groupDTO = groupService.getAllGroups();
        return ResponseEntity.ok().body(groupDTO);
    }

    @Override
    public ResponseEntity<GroupDTO> groupDetail(Long id){
        Group group = groupService.getGroupById(id);
        GroupDTO groupDTO = groupMapper.toDTO(group);
        return ResponseEntity.ok().body(groupDTO);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(Long id){
        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }

   @Override
    public ResponseEntity<GroupDTO> updateGroup(Long id, GroupDTO groupDTO){
            Group group = groupMapper.toModel(groupDTO);
            group.setId(id);
            groupService.updateGroup(id, group);
            GroupDTO updatedGroupDTO = groupMapper.toDTO(group);
            return ResponseEntity.ok().body(updatedGroupDTO);
    }
}