package com.pocu.catalog.service;

import com.pocu.catalog.entity.StudentEntity;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.exception.StudentAlreadyEnrolledException;
import com.pocu.catalog.exception.StudentNotFoundException;
import com.pocu.catalog.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final String STUDENT_NOT_FOUND_CODE = "STUDENT_NOT_FOUND";

    private final StudentRepository studentRepository;
    private final SubjectService subjectService;

    @Autowired
    public StudentService(StudentRepository studentRepository, SubjectService subjectService) {
        this.studentRepository = studentRepository;
        this.subjectService = subjectService;
    }

    public List<StudentEntity> getStudents(Integer page, Integer size) {
        return studentRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public StudentEntity saveStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public StudentEntity getStudent(Long id) {
        Optional<StudentEntity> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            throw new StudentNotFoundException("Student not found", STUDENT_NOT_FOUND_CODE);
        }
    }

    public StudentEntity enroll(Long studentId, Long subjectId, Boolean enroll) {
        StudentEntity student = getStudent(studentId);
        SubjectEntity subject = subjectService.getSubject(subjectId);

        if (enroll) {
            if (student.getEnrolledSubjects() != null && student.getEnrolledSubjects().contains(subject)) {
                throw new StudentAlreadyEnrolledException("Student already enrolled!");
            }
            student.setEnrolledSubject(subject);
        } else {
            student.removeEnrolledSubject(subject);
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentEntity updateStudent(Long id, StudentEntity studentToUpdate) {
        StudentEntity persistedStudent = getStudent(id);

        studentToUpdate.setId(id);
        studentToUpdate.setEnrolledSubjects(persistedStudent.getEnrolledSubjects());

        return studentRepository.save(studentToUpdate);
    }
}
