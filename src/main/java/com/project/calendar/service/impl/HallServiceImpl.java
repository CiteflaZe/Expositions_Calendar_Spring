package com.project.calendar.service.impl;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.repository.HallRepository;
import com.project.calendar.service.HallService;
import com.project.calendar.service.mapper.HallMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("hallService")
@AllArgsConstructor(onConstructor = @_(@Autowired))
@Log4j
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private  final HallMapper mapper;

    @Override
    public boolean add(Hall hall) {
        return false;
    }

    @Override
    public List<Hall> showAll() {
        final List<HallEntity> allHalls = hallRepository.findAll();
        return allHalls.stream()
                .map(mapper::mapHallEntityToHall)
                .collect(Collectors.toList());
    }
}
