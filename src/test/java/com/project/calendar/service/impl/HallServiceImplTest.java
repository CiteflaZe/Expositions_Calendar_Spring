package com.project.calendar.service.impl;

import com.project.calendar.domain.Hall;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.exception.HallAlreadyExistException;
import com.project.calendar.repository.HallRepository;
import com.project.calendar.service.mapper.HallMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.project.calendar.MockData.MOCK_HALL;
import static com.project.calendar.MockData.MOCK_HALL_ENTITY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HallServiceImpl.class)
public class HallServiceImplTest {

    private static final Hall HALL = MOCK_HALL;
    private static final HallEntity HALL_ENTITY = MOCK_HALL_ENTITY;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private HallRepository hallRepository;

    @MockBean
    private HallMapper hallMapper;

    @Autowired
    private HallServiceImpl hallService;

    @After
    public void resetMocks() {
        reset(hallRepository, hallMapper);
    }

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
        when(hallMapper.mapHallToHallEntity(HALL)).thenReturn(HALL_ENTITY);

        hallService.add(HALL);

        verify(hallRepository).save(HALL_ENTITY);
    }

    @Test
    public void showByIdShouldThrowInvalidEntityException() {
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("There is no hall with this id");

        when(hallRepository.findById(anyLong())).thenReturn(Optional.empty());

        hallService.showById(4L);
    }

    @Test
    public void showByIdShouldReturnHall() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.of(HALL_ENTITY));
        when(hallMapper.mapHallEntityToHall(HALL_ENTITY)).thenReturn(HALL);

        final Hall actual = hallService.showById(4L);

        verify(hallMapper).mapHallEntityToHall(HALL_ENTITY);
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
        when(hallMapper.mapHallEntityToHall(HALL_ENTITY)).thenReturn(HALL);

        final List<Hall> halls = hallService.showAll();

        assertThat(halls.size(), is(1));
        assertThat(halls.get(0), is(HALL));
    }

}