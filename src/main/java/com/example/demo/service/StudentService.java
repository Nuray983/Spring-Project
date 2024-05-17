package com.example.demo.service;

import com.example.demo.entity.Student;
import org.openapitools.model.StudentDTO;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);

    List<StudentDTO> getAllStudents();

    Student getStudentById(Long id);

    void updateStudents(Long id, StudentDTO studentDTO);

    void deleteStudent(Long id);
}
