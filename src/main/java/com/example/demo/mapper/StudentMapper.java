package com.example.demo.mapper;

import com.example.demo.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.openapitools.model.StudentDTO;

@Mapper(componentModel = "spring", uses = {GroupMapper.class})
public interface StudentMapper {
    StudentDTO toDTO(Student model);
    Student toModel(StudentDTO dto);

    void update(@MappingTarget Student existingStudent, Student student);
}
