package com.pocu.catalog.web;

import com.pocu.catalog.converter.StudentConverter;
import com.pocu.catalog.entity.StudentEntity;
import com.pocu.catalog.service.StudentService;
import com.pocu.catalog.web.dto.StudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/student")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final StudentConverter studentConverter;

    @Autowired
    public StudentController(StudentService studentService, StudentConverter studentConverter) {
        this.studentService = studentService;
        this.studentConverter = studentConverter;
    }

    @GetMapping(value = "")
    public List<StudentDto> getStudents(@RequestParam(name = "page") Integer page,
                                        @RequestParam(name = "size") Integer size) {
        logger.debug("Get all students - page {} size {}", page, size);
        return studentConverter.fromEntitiesToDtos(studentService.getStudents(page, size));
    }

    @GetMapping(value = "/{id}")
    public StudentDto getStudent(@PathVariable(name = "id") Long id) {
        logger.debug("Get student by id {}", id);

        return studentConverter.fromEntityToDto(studentService.getStudent(id));
    }

    @PostMapping(value = "")
    public StudentDto saveStudent(@Valid @RequestBody StudentDto studentDto) {
        logger.debug("Save student {}", studentDto.toString());

        StudentEntity studentEntity = studentService.saveStudent(studentConverter.fromDtoToEntity(studentDto));
        logger.debug("Student saved with id {}", studentEntity.getId());

        return studentConverter.fromEntityToDto(studentEntity);
    }

    @PostMapping(value = "/{id}/enrollment/{status}/{subjectId}")
    public StudentDto enrollStudentToSubject(@PathVariable(name = "id") Long id,
                                             @PathVariable(name = "subjectId") Long subjectId,
                                             @PathVariable(name = "status") Boolean status) {
        logger.debug("Enroll student {} to subject {} status {}", id, subjectId, status);

        StudentEntity studentEntity = studentService.enroll(id, subjectId, status);

        return studentConverter.fromEntityToDto(studentEntity);
    }

    @PutMapping(value = "/{id}")
    public StudentDto updateStudent(@PathVariable(name = "id") Long id,
                              @Valid @RequestBody StudentDto studentDto) {
        logger.debug("Updating student with id {}", id);
        StudentEntity studentEntity = studentService.updateStudent(id, studentConverter.fromDtoToEntity(studentDto));

        return studentConverter.fromEntityToDto(studentEntity);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStudent(@PathVariable(name = "id") Long id) {
        logger.debug("Delete student with id {}", id);

        studentService.deleteStudent(id);
    }

}
