package com.pocu.catalog.service;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.exception.SubjectNotFoundException;
import com.pocu.catalog.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private static final String SUBJECT_NOT_FOUND_CODE = "SUBJECT_NOT_FOUND";

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectEntity getSubject(Long id) {
        Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(id);
        if (subjectEntityOptional.isPresent()) {
            return subjectEntityOptional.get();
        } else {
            throw new SubjectNotFoundException("Get subject failed - subject not found", SUBJECT_NOT_FOUND_CODE);
        }
    }

    public List<SubjectEntity> getSubjects() {
        return subjectRepository.findAll();
    }

    public SubjectEntity saveSubject(SubjectEntity subjectEntity) {
        return subjectRepository.save(subjectEntity);
    }

    public SubjectEntity updateEntity(Long id, SubjectEntity updatedEntity) {
        Optional<SubjectEntity> alreadyPersistedSubject = subjectRepository.findById(id);
        if (alreadyPersistedSubject.isPresent()) {
            updatedEntity.setId(id);
            return subjectRepository.save(updatedEntity);
        } else {
            throw new SubjectNotFoundException("Update subject failed - subject not found" , SUBJECT_NOT_FOUND_CODE);
        }
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
