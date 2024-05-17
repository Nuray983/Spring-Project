package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.mapper.GroupMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.GroupService;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.StudentsApi;
import org.openapitools.model.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentsApi {

    private final StudentService studentService;
    private final GroupService groupService;
    private final StudentMapper studentMapper;
    private final GroupMapper groupMapper;
    // to RequiredArgsConstructor

    @Override
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<StudentDTO> addStudent(StudentDTO studentDTO) {
        if (studentDTO.getGroupId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Group group = groupMapper.toGetId(studentDTO.getGroupId());
        Student student = studentMapper.toModel(studentDTO);

        Group existingGroup = groupService.getGroupById(group.getId());
        if (existingGroup == null) {
            groupService.createGroup(group);
        } else {
            group = existingGroup;
        }
        student.setGroups(List.of(group));
        group.getStudentsList().add(student);
        studentService.addStudent(student);
        return ResponseEntity.ok(studentDTO);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<StudentDTO> detailStudent(Long id) {
        StudentDTO studentDTO = studentMapper.toDTO(studentService.getStudentById(id));
        return ResponseEntity.ok(studentDTO);
    }

    @Override
    public ResponseEntity<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        studentService.updateStudents(id, studentDTO);
        return ResponseEntity.ok(studentDTO);
    }
}