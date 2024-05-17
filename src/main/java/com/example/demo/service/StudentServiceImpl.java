package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.StudentDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    // to private final
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
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

    @Override
    public Student getStudentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public void updateStudents(Long id, StudentDTO studentDTO) {
        studentRepository.findById(id).ifPresentOrElse(existingStudent -> {
            studentMapper.update(existingStudent, studentMapper.toModel(studentDTO));
            studentRepository.save(existingStudent);
        }, () -> {
            throw new EntityNotFoundException("Student with id " + id + " not found");
        });
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
        for (Group group : student.getGroups()) {
            group.getStudentsList().remove(student);
        }
        student.getGroups().clear();
        studentRepository.deleteById(id);
    }
}
