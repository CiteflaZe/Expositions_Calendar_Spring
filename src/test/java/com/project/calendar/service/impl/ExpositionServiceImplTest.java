package com.project.calendar.service.impl;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.ExpositionRepository;
import com.project.calendar.service.mapper.ExpositionMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpositionServiceImplTest {
    private static final Long ID = 5L;
    private static final String TITLE = "Generic Title";
    private static final String THEME = "Theme";
    private static final LocalDate START_DATE = LocalDate.of(2019, 11, 12);
    private static final LocalDate END_DATE = LocalDate.of(2019, 12, 13);
    private static final BigDecimal TICKET_PRICE = BigDecimal.valueOf(28.25);
    private static final String DESCRIPTION = "Description";

    private static final Exposition EXPOSITION = initExposition();
    private static final ExpositionEntity EXPOSITION_ENTITY = initExpositionEntity();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ExpositionRepository expositionRepository;
    @Mock
    private ExpositionMapper mapper;

    @InjectMocks
    private ExpositionServiceImpl expositionService;

    @Test
    public void addShouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Exposition is null");

        expositionService.add(null);
    }

    @Test
    public void addShouldThrowExpositionAlreadyExistException() {
        expectedException.expect(ExpositionAlreadyExistException.class);
        expectedException.expectMessage("Exposition with this title already exists");

        when(expositionRepository.findByTitle(anyString())).thenReturn(Optional.of(EXPOSITION_ENTITY));

        expositionService.add(EXPOSITION);
    }

    @Test
    public void addShouldSaveExposition() {
        when(expositionRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(mapper.mapExpositionToExpositionEntity(any(Exposition.class))).thenReturn(EXPOSITION_ENTITY);

        expositionService.add(EXPOSITION);

        verify(expositionRepository).save(EXPOSITION_ENTITY);
    }

    @Test
    public void showByIdShouldThrowInvalidEntityException(){
        expectedException.expect(InvalidEntityException.class);
        expectedException.expectMessage("There is no exposition with this id");
        when(expositionRepository.findById(anyLong())).thenReturn(Optional.empty());

        expositionService.showById(1L);
    }

    @Test
    public void showByIdShouldReturnExposition(){
        when(expositionRepository.findById(anyLong())).thenReturn(Optional.of(EXPOSITION_ENTITY));
        when(mapper.mapExpositionEntityToExposition(EXPOSITION_ENTITY)).thenReturn(EXPOSITION);

        final Exposition actual = expositionService.showById(4L);

        verify(expositionRepository).findById(anyLong());
        assertThat(actual, is(EXPOSITION));
    }

    @Test
    public void showEntriesAmountShouldReturnEntriesAmount(){
        when(expositionRepository.count()).thenReturn(4L);

        final Long actual = expositionService.showEntriesAmount();

        assertThat(actual, is(4L));
    }

    private static Exposition initExposition() {
        final Hall hall = Hall.builder()
                .id(1L)
                .build();

        return Exposition.builder()
                .title(TITLE)
                .theme(THEME)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .ticketPrice(TICKET_PRICE)
                .description(DESCRIPTION)
                .hall(hall)
                .build();
    }

    private static ExpositionEntity initExpositionEntity() {
        final HallEntity hallEntity = new HallEntity();
        hallEntity.setId(1L);

        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(ID);
        expositionEntity.setTitle(TITLE);
        expositionEntity.setTheme(THEME);
        expositionEntity.setStartTime(START_DATE);
        expositionEntity.setFinishTime(END_DATE);
        expositionEntity.setTicketPrice(TICKET_PRICE);
        expositionEntity.setDescription(DESCRIPTION);
        expositionEntity.setHall(hallEntity);

        return expositionEntity;
    }
}