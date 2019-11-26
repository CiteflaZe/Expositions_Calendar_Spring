package com.project.calendar.service;

import com.project.calendar.domain.Exposition;

import java.util.List;

public interface ExpositionService {
    void add(Exposition exposition);

    Exposition showById(Long id);

    List<Exposition> showAll(Integer page, Integer rowCount);

    Long showEntriesAmount();
}
