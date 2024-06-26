package com.example.demo.service;

import com.example.demo.entity.Group;
import org.openapitools.model.GroupDTO;

import java.util.List;

public interface GroupService {
        void createGroup(Group group);

        List<Group> getAllGroups();

        Group getGroupById(Long id);

        void updateGroup(Long id, Group group);

        void deleteGroupById(Long id);

        void deleteStudentFromGroup(Long studentId, Long groupId);
}
