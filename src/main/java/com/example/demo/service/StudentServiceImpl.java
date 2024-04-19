package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.StudentDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    // to private final
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Override
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        studentRepository.save(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream()
                    .map(studentMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching students", e);
        }
    }


// TODO to mapper

    @Override
    public Student getStudentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public void updateStudents(Long id, Student student) {
        Optional<Student> optionalExistingStudent = studentRepository.findById(id);
        if (optionalExistingStudent.isPresent()) {
            Student existingStudent = optionalExistingStudent.get();
            if (student.getGroup() != null && student.getGroup().getId() != null) {
                Optional<Group> optionalGroup = groupRepository.findById(student.getGroup().getId());
                if (optionalGroup.isPresent()) {
                    existingStudent.setGroup(optionalGroup.get());
                } else {
                    throw new EntityNotFoundException("Group with id " + student.getGroup().getId() + " not found");
                }
            } else {
                studentMapper.update(existingStudent, student);
                studentRepository.save(existingStudent);
            }
        } else {
            throw new EntityNotFoundException("Student with id " + id + " not found");
        }
    }

    @Override
    public void deleteStudent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}
