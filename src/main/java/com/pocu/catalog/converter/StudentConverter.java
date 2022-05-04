package com.pocu.catalog.converter;

import com.pocu.catalog.entity.StudentEntity;
import com.pocu.catalog.web.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter extends BaseConverter<StudentDto, StudentEntity> {

    private final SubjectConverter subjectConverter;

    @Autowired
    public StudentConverter(SubjectConverter subjectConverter) {
        this.subjectConverter = subjectConverter;
    }

    @Override
    public StudentDto fromEntityToDto(StudentEntity entity) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(entity.getId());
        studentDto.setFirstName(entity.getFirstName());
        studentDto.setLastName(entity.getLastName());
        studentDto.setAverageGrade(entity.getAverageGrade());
        studentDto.setEnrolledSubjects(subjectConverter.fromEntitiesToDtos(entity.getEnrolledSubjects()));

        return studentDto;
    }

    @Override
    public StudentEntity fromDtoToEntity(StudentDto dto) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setFirstName(dto.getFirstName());
        studentEntity.setLastName(dto.getLastName());
        studentEntity.setAverageGrade(dto.getAverageGrade());

        return studentEntity;
    }
}
