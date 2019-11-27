package com.project.calendar.service.mapper;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HallMapperTest {
    private static final Long ID = 4L;
    private static final String NAME = "Great Hall";
    private static final String CITY = "Kiev";
    private static final String STREET = "Some Street";
    private static final Integer HOUSE_NUMBER = 15;

    private static final Hall HALL = initHall();
    private static final HallEntity ENTITY = initEntity();

    private final HallMapper mapper = new HallMapper();

    @Test
    public void mapHallToHallEntityShouldReturnHallEntity() {
        final HallEntity actual = mapper.mapHallToHallEntity(HALL);

        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getCity(), is(CITY));
        assertThat(actual.getStreet(), is(STREET));
        assertThat(actual.getHouseNumber(), is(HOUSE_NUMBER));
    }

    @Test
    public void mapHallEntityToHallShouldReturnHall() {
        final Hall actual = mapper.mapHallEntityToHall(ENTITY);

        assertThat(actual.getId(), is(ID));
        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getCity(), is(CITY));
        assertThat(actual.getStreet(), is(STREET));
        assertThat(actual.getHouseNumber(), is(HOUSE_NUMBER));
    }

    private static Hall initHall() {
        return Hall.builder()
                .name(NAME)
                .city(CITY)
                .street(STREET)
                .houseNumber(HOUSE_NUMBER)
                .build();
    }

    private static HallEntity initEntity() {
        final HallEntity hallEntity = new HallEntity();
        hallEntity.setId(ID);
        hallEntity.setName(NAME);
        hallEntity.setCity(CITY);
        hallEntity.setStreet(STREET);
        hallEntity.setHouseNumber(HOUSE_NUMBER);

        return hallEntity;
    }

}