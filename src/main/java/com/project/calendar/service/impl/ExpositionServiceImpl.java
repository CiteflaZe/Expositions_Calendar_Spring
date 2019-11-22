package com.project.calendar.service.impl;

import com.project.calendar.domain.Exposition;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.ExpositionRepository;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.mapper.ExpositionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("expositionService")
@AllArgsConstructor(onConstructor = @_(@Autowired))
@Log4j
public class ExpositionServiceImpl implements ExpositionService {

    private ExpositionRepository expositionRepository;
    private ExpositionMapper mapper;

    @Override
    public void add(Exposition exposition) {
        if(expositionRepository.findByTitle(exposition.getTitle()).isPresent()){
            log.warn("Exposition with this title already exists");
            throw new InvalidEntityException("Exposition with this title already exists");
        }

        final ExpositionEntity expositionEntity = mapper.mapExpositionToExpositionEntity(exposition);

        expositionRepository.save(expositionEntity);

    }
}
