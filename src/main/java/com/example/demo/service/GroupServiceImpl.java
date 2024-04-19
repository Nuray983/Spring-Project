package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.mapper.GroupMapper;
import com.example.demo.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.GroupDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

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
    public List<GroupDTO> getAllGroups(){
        try {
            List<Group> groups = groupRepository.findAll();
            return groups.stream()
                    // todo use mapper instead local method(use Mapstruct library)
                    .map(groupMapper::toDTO)
                    .collect(Collectors.toList());
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
        }
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + id + " not found"));
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
}

