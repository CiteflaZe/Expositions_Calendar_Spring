package com.project.calendar.service.impl;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.exception.HallAlreadyExistException;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.HallRepository;
import com.project.calendar.service.mapper.HallMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HallServiceImplTest {
    private static final Long ID = 4L;
    private static final String NAME = "Great Hall";
    private static final String CITY = "Kiev";
    private static final String STREET = "Some Street";
    private static final Integer HOUSE_NUMBER = 15;

    private static final Hall HALL = initHall();
    private static final HallEntity HALL_ENTITY = initEntity();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private HallRepository hallRepository;
    @Mock
    private HallMapper mapper;

    @InjectMocks
    private HallServiceImpl hallService;

    @Test
    public void addShouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Hall is null");

        hallService.add(null);
    }

    @Test
    public void addShouldThrowHallAlreadyExistException() {
        expectedException.expect(HallAlreadyExistException.class);
        expectedException.expectMessage("Hall with this name already exists");

        when(hallRepository.findByName(anyString())).thenReturn(Optional.of(HALL_ENTITY));

        hallService.add(HALL);
    }

    @Test
    public void addShouldAddHall() {
        when(hallRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.mapHallToHallEntity(HALL)).thenReturn(HALL_ENTITY);

        hallService.add(HALL);

        verify(hallRepository).save(HALL_ENTITY);
    }

    @Test
    public void showByIdShouldThrowInvalidEntityException() {
        expectedException.expect(InvalidEntityException.class);
        expectedException.expectMessage("There is no hall with this id");

        when(hallRepository.findById(anyLong())).thenReturn(Optional.empty());

        hallService.showById(4L);
    }

    @Test
    public void showByIdShouldReturnHall() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.of(HALL_ENTITY));
        when(mapper.mapHallEntityToHall(HALL_ENTITY)).thenReturn(HALL);

        final Hall actual = hallService.showById(4L);

        verify(mapper).mapHallEntityToHall(HALL_ENTITY);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void showAllShouldReturnEmptyList() {
        when(hallRepository.findAll()).thenReturn(emptyList());

        final List<Hall> halls = hallService.showAll();

        assertThat(halls, is(emptyList()));
    }

    @Test
    public void showAllShouldReturnHallList() {
        when(hallRepository.findAll()).thenReturn(singletonList(HALL_ENTITY));
        when(mapper.mapHallEntityToHall(HALL_ENTITY)).thenReturn(HALL);

        final List<Hall> halls = hallService.showAll();

        assertThat(halls.size(), is(1));
        assertThat(halls.get(0), is(HALL));
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