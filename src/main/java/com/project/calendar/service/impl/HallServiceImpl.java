package com.project.calendar.service.impl;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.exception.HallAlreadyExistException;
import com.project.calendar.repository.HallRepository;
import com.project.calendar.service.HallService;
import com.project.calendar.service.mapper.HallMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("hallService")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

    @Override
    public void add(Hall hall) {
        if (hall == null) {
            log.warn("Hall is null");
            throw new IllegalArgumentException("Hall is null");
        }

        if (hallRepository.findByName(hall.getName()).isPresent()) {
            log.warn("Hall with this name already exists");
            throw new HallAlreadyExistException("Hall with this name already exists");
        }

        final HallEntity hallEntity = hallMapper.mapHallToHallEntity(hall);

        hallRepository.save(hallEntity);
    }

    @Override
    public Hall showById(Long id) {
        if (id == null) {
            log.warn("Id is null");
            throw new EntityNotFoundException("There is no hall with this id");
        }

        final Optional<HallEntity> entity = hallRepository.findById(id);

        if (!entity.isPresent()) {
            log.warn("There is no hall with this id");
            throw new EntityNotFoundException("There is no hall with this id");
        }

        return hallMapper.mapHallEntityToHall(entity.get());
    }

    @Override
    public List<Hall> showAll() {
        final List<HallEntity> allHalls = hallRepository.findAll();
        return allHalls.stream()
                .map(hallMapper::mapHallEntityToHall)
                .collect(Collectors.toList());
    }
}
