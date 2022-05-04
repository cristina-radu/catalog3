package com.pocu.catalog.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "average_grade")
    private BigDecimal averageGrade;

    @ManyToMany
    @JoinTable(
            name = "subject_enrollment",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> enrolledSubjects;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<SubjectEntity> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void setEnrolledSubjects(List<SubjectEntity> enrolledSubjects) {
        this.enrolledSubjects = enrolledSubjects;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", averageGrade=" + averageGrade +
                "} " + super.toString();
    }

    public void setEnrolledSubject(SubjectEntity subject) {
        if (this.enrolledSubjects == null) {
            this.enrolledSubjects = new ArrayList<>();
        }
        this.enrolledSubjects.add(subject);
    }

    public void removeEnrolledSubject(SubjectEntity subject) {
        if (this.enrolledSubjects == null || this.enrolledSubjects.isEmpty()) {
            return;
        }
        this.enrolledSubjects.remove(subject);
    }
}
