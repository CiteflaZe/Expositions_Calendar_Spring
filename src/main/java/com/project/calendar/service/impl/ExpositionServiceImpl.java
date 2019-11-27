package com.project.calendar.service.impl;

import com.project.calendar.domain.Exposition;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.ExpositionRepository;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.mapper.ExpositionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service("expositionService")
@AllArgsConstructor(onConstructor = @_(@Autowired))
@Log4j
public class ExpositionServiceImpl implements ExpositionService {

    private ExpositionRepository expositionRepository;
    private ExpositionMapper mapper;

    @Override
    public void add(Exposition exposition) {
        if (exposition == null) {
            log.warn("Exposition is null");
            throw new IllegalArgumentException("Exposition is null");
        }

        if (expositionRepository.findByTitle(exposition.getTitle()).isPresent()) {
            log.warn("Exposition with this title already exists");
            throw new ExpositionAlreadyExistException("Exposition with this title already exists");
        }

        final ExpositionEntity expositionEntity = mapper.mapExpositionToExpositionEntity(exposition);

        expositionRepository.save(expositionEntity);

    }

    @Override
    public Exposition showById(Long id) {
        final Optional<ExpositionEntity> entity = expositionRepository.findById(id);

        if (!entity.isPresent()) {
            log.warn("There is no exposition with this id");
            throw new InvalidEntityException("There is no exposition with this id");
        }

        return mapper.mapExpositionEntityToExposition(entity.get());
    }

    @Override
    public List<Exposition> showAll(Integer page, Integer rowCount) {
        final PageRequest pageRequest = PageRequest.of(page, rowCount);

        final Page<ExpositionEntity> all = expositionRepository.findAll(pageRequest);

        return all.isEmpty() ? emptyList() :
                all.stream()
                        .map(mapper::mapExpositionEntityToExposition)
                        .collect(Collectors.toList());
    }

    @Override
    public Long showEntriesAmount() {
        return expositionRepository.count();
    }
}
