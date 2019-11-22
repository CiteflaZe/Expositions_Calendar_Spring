package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.HallEntity;
import org.springframework.stereotype.Component;

@Component
public class ExpositionMapper {

    public Exposition mapExpositionEntityToExposition(ExpositionEntity entity){
        return Exposition.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .theme(entity.getTheme())
                .startDate(entity.getStartTime())
                .endDate(entity.getFinishTime())
                .ticketPrice(entity.getTicketPrice())
                .description(entity.getDescription())
                .hall(createHall(entity))
                .build();
    }

    public ExpositionEntity mapExpositionToExpositionEntity(Exposition exposition){
        final ExpositionEntity entity = new ExpositionEntity();

        entity.setTitle(exposition.getTitle());
        entity.setTheme(exposition.getTheme());
        entity.setStartTime(exposition.getStartDate());
        entity.setFinishTime(exposition.getEndDate());
        entity.setTicketPrice(exposition.getTicketPrice());
        entity.setDescription(exposition.getDescription());
        entity.setHall(createHallEntity(exposition));

        return entity;
    }

    private Hall createHall(ExpositionEntity entity){
        if(entity.getHall() == null){
            return null;
        }else{
            return Hall.builder()
                    .id(entity.getHall().getId())
                    .build();
        }
    }

    private HallEntity createHallEntity(Exposition exposition){
        if(exposition.getHall() == null){
            return null;
        }else{
            final HallEntity hallEntity = new HallEntity();
            hallEntity.setId(exposition.getHall().getId());

            return hallEntity;
        }
    }

}
