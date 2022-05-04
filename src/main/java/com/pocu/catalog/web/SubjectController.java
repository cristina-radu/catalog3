package com.pocu.catalog.web;

import com.pocu.catalog.converter.SubjectConverter;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.service.SubjectService;
import com.pocu.catalog.web.dto.SubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    private Logger logger = LoggerFactory.getLogger(SubjectController.class);

    private final SubjectService subjectService;
    private final SubjectConverter subjectConverter;

    @Autowired
    public SubjectController(SubjectService subjectService, SubjectConverter subjectConverter) {
        this.subjectService = subjectService;
        this.subjectConverter = subjectConverter;
    }

    @GetMapping(value = "")
    public List<SubjectDto> getSubjects() {
        logger.debug("Get all subjects");
        return subjectConverter.fromEntitiesToDtos(subjectService.getSubjects());
    }

    @GetMapping(value = "/{id}")
    public SubjectDto getSubject(@PathVariable Long id) {
        logger.debug("Get subject with id {}", id);
        return subjectConverter.fromEntityToDto(subjectService.getSubject(id));
    }

    @PostMapping(value = "")
    public SubjectDto addSubject(@Valid @RequestBody SubjectDto subjectDto) {
        logger.debug("Save new subject with name {}", subjectDto.getName());
        SubjectEntity subjectEntity = subjectService.saveSubject(subjectConverter.fromDtoToEntity(subjectDto));

        logger.debug("Save new subject with name {} successful - id {}", subjectDto.getName(), subjectDto.getId());
        return subjectConverter.fromEntityToDto(subjectEntity);
    }

    @PutMapping(value = "/{id}")
    public SubjectDto updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectDto subjectDto) {
        logger.debug("Update subject with id {}", id);
        SubjectEntity updatedEntity = subjectConverter.fromDtoToEntity(subjectDto);
        return subjectConverter.fromEntityToDto(subjectService.updateEntity(id, updatedEntity));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSubject(@PathVariable Long id) {
        logger.debug("Delete subject with id {}", id);
        subjectService.deleteSubject(id);
    }

}
