package com.project.calendar.service.mapper;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import org.junit.Test;

import static com.project.calendar.MockData.MOCK_HALL;
import static com.project.calendar.MockData.MOCK_HALL_ENTITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HallMapperTest {

    private static final Hall HALL = MOCK_HALL;
    private static final HallEntity HALL_ENTITY = MOCK_HALL_ENTITY;

    private final HallMapper hallMapper = new HallMapper();

    @Test
    public void mapHallToHallEntityShouldReturnHallEntity() {
        final HallEntity actual = hallMapper.mapHallToHallEntity(HALL);

        assertThat(actual.getName(), is(MOCK_HALL.getName()));
        assertThat(actual.getCity(), is(MOCK_HALL.getCity()));
        assertThat(actual.getStreet(), is(MOCK_HALL.getStreet()));
        assertThat(actual.getHouseNumber(), is(MOCK_HALL.getHouseNumber()));
    }

    @Test
    public void mapHallEntityToHallShouldReturnHall() {
        final Hall actual = hallMapper.mapHallEntityToHall(HALL_ENTITY);

        assertThat(actual.getId(), is(MOCK_HALL_ENTITY.getId()));
        assertThat(actual.getName(), is(MOCK_HALL_ENTITY.getName()));
        assertThat(actual.getCity(), is(MOCK_HALL_ENTITY.getCity()));
        assertThat(actual.getStreet(), is(MOCK_HALL_ENTITY.getStreet()));
        assertThat(actual.getHouseNumber(), is(MOCK_HALL_ENTITY.getHouseNumber()));
    }

}