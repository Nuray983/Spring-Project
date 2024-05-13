package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Override
    @Transactional
    public void createGroup(Group group) {
        if(group == null){
            throw new IllegalArgumentException("Group cannot be null");
        }
        groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> getAllGroups(){
        try {
            return groupRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching groups", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroupById(Long id){
        if(id == null){
            throw new IllegalArgumentException("Group id cannot be null");
        }else {
            return groupRepository.findById(id).orElse(null);
        }
    }

    @Override
    public void updateGroup(Long id, Group group){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group existingGroup = optionalGroup.get();
            existingGroup.setId(group.getId());
            existingGroup.setGroupName(group.getGroupName());
            existingGroup.setQuantity(group.getQuantity());
            existingGroup.setStudentsList(group.getStudentsList());
        groupRepository.save(existingGroup);
    } else {
        throw new EntityNotFoundException("Group with id " + id + "not found");
    }
}

    @Override
    public void deleteGroupById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Group id cannot be null");
        }
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group with id " + id + " not found");
        }
        groupRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteStudentFromGroup(Long studentId, Long groupId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));

        if (student.getGroups().contains(group)) {
            student.getGroups().remove(group);
            group.getStudentsList().remove(student);
            logger.info("Student with id {} removed from group with id {}", studentId, groupId);
        } else {
            logger.warn("Student with id {} is not in group with id {}", studentId, groupId);
        }
    }

}

