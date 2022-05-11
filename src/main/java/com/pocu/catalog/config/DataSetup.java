package com.pocu.catalog.config;

import com.pocu.catalog.entity.StudentEntity;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.service.StudentService;
import com.pocu.catalog.service.SubjectService;
import com.pocu.catalog.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnExpression("${insert.test.data}")
public class DataSetup implements ApplicationRunner {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    public DataSetup(SubjectService subjectService, TeacherService teacherService, StudentService studentService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


       SubjectEntity subjectOne = saveSubject("Subject1", true, 5);
        SubjectEntity subjectTwo = saveSubject("Subject2", true, 2);
        SubjectEntity subjectThree = saveSubject("Subject3", false, 4);
        SubjectEntity subjectFore = saveSubject("Subject4", false, 5);
        SubjectEntity subjectFive= saveSubject("Subject5", true, 5);

        List<SubjectEntity> subjects = new ArrayList<>();
        subjects.add(subjectOne);
        subjects.add(subjectTwo);
        subjects.add(subjectThree);
        subjects.add(subjectFore);
        subjects.add(subjectFive);

        saveTeacher("FirstName1", "LastName1", "1234567890123", 10L, subjects);
        saveTeacher("FirstName2", "LastName2", "2234567890123", 15L, new ArrayList<>());
        saveTeacher("FirstName3", "LastName3", "3234567890123", 22L, new ArrayList<>());
        saveTeacher("FirstName4", "LastName4", "4234567890123", 22L, new ArrayList<>());
        saveTeacher("FirstName5", "LastName5", "5234567890123", 3L, new ArrayList<>());
        saveTeacher("FirstName6", "LastName6", "6234567890123", 12L, new ArrayList<>());
        saveTeacher("FirstName7", "LastName7", "7234567890123", 5L, new ArrayList<>());
        saveTeacher("FirstName8", "LastName8", "8234567890123", 22L, new ArrayList<>());
        saveTeacher("FirstName9", "LastName9", "9234567890123", 4L, new ArrayList<>());
        saveTeacher("FirstName10", "LastName10", "1034567890123", 2L, new ArrayList<>());

        saveStudent("Student1", "Student1", BigDecimal.TEN);
        saveStudent("Student2", "Student2", BigDecimal.TEN);
        saveStudent("Student3", "Student3", BigDecimal.TEN);
        saveStudent("Student4", "Student4", BigDecimal.TEN);
        saveStudent("Student5", "Student5", BigDecimal.TEN);
        saveStudent("Student6", "Student6", BigDecimal.TEN);
        saveStudent("Student7", "Student7", BigDecimal.TEN);
        saveStudent("Student8", "Student8", BigDecimal.TEN);
        saveStudent("Student9", "Student9", BigDecimal.TEN);
        saveStudent("Student10", "Student10", BigDecimal.TEN);

    }

    private SubjectEntity saveSubject(String name, Boolean optional, Integer creditPoints) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setName(name);
        subjectEntity.setOptional(optional);
        subjectEntity.setCreditPoints(creditPoints);
        return subjectService.saveSubject(subjectEntity);
    }

    private void saveTeacher(String firstName, String lastName, String cnp, Long salary, List<SubjectEntity> subjects) {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setFirstName(firstName);
        teacherEntity.setLastName(lastName);
        teacherEntity.setCnp(cnp);
        teacherEntity.setSalary(salary);
        teacherEntity.setSubjects(subjects);
        teacherService.saveTeacher(teacherEntity);
    }

    private void saveStudent(String firstName, String lastName, BigDecimal averageGrade) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setFirstName(firstName);
        studentEntity.setLastName(lastName);
        studentEntity.setAverageGrade(averageGrade);

        studentService.saveStudent(studentEntity);
    }
}
