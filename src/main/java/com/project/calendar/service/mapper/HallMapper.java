package com.project.calendar.service.mapper;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {

    public HallEntity mapHallToHallEntity(Hall hall){
        final HallEntity hallEntity = new HallEntity();
        hallEntity.setName(hall.getName());
        hallEntity.setCity(hall.getCity());
        hallEntity.setStreet(hall.getStreet());
        hallEntity.setHouseNumber(hall.getHouseNumber());
        return hallEntity;
    }

    public Hall mapHallEntityToHall(HallEntity entity){
        return Hall.builder()
                .id(entity.getId())
                .name(entity.getName())
                .city(entity.getCity())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .build();
    }

}
