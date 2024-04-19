package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.error.BadRequestException;
import org.openapitools.model.StudentDTO;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);

    List<StudentDTO> getAllStudents();

    Student getStudentById(Long id);

    void updateStudents(Long id, Student student);

    void deleteStudent(Long id);
}
