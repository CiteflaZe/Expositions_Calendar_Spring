package com.project.calendar.service;

import com.project.calendar.domain.Hall;

import java.util.List;

public interface HallService {
    boolean add(Hall hall);

    Hall showById(Long id);

    List<Hall> showAll();
}
