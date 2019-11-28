package com.project.calendar.service.impl;

import com.project.calendar.domain.Exposition;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.ExpositionRepository;
import com.project.calendar.service.mapper.ExpositionMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.project.calendar.MockData.MOCK_EXPOSITION;
import static com.project.calendar.MockData.MOCK_EXPOSITION_ENTITY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ExpositionServiceImpl.class)
public class ExpositionServiceImplTest {

    private static final Exposition EXPOSITION = MOCK_EXPOSITION;
    private static final ExpositionEntity EXPOSITION_ENTITY = MOCK_EXPOSITION_ENTITY;

    private static final Page<ExpositionEntity> EXPOSITIONS = new PageImpl<>(singletonList(EXPOSITION_ENTITY));

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private ExpositionRepository expositionRepository;

    @MockBean
    private ExpositionMapper expositionMapper;

    @Autowired
    private ExpositionServiceImpl expositionService;

    @After
    public void resetMocks(){
        reset(expositionRepository, expositionMapper);
    }

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
        when(expositionMapper.mapExpositionToExpositionEntity(any(Exposition.class))).thenReturn(EXPOSITION_ENTITY);

        expositionService.add(EXPOSITION);

        verify(expositionRepository).save(EXPOSITION_ENTITY);
    }

    @Test
    public void showByIdShouldThrowInvalidEntityException() {
        expectedException.expect(InvalidEntityException.class);
        expectedException.expectMessage("There is no exposition with this id");
        when(expositionRepository.findById(anyLong())).thenReturn(Optional.empty());

        expositionService.showById(1L);
    }

    @Test
    public void showByIdShouldReturnExposition() {
        when(expositionRepository.findById(anyLong())).thenReturn(Optional.of(EXPOSITION_ENTITY));
        when(expositionMapper.mapExpositionEntityToExposition(EXPOSITION_ENTITY)).thenReturn(EXPOSITION);

        final Exposition actual = expositionService.showById(4L);

        verify(expositionRepository).findById(anyLong());
        assertThat(actual, is(EXPOSITION));
    }

    @Test
    public void showAllShouldReturnEmptyList() {
        when(expositionRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        final List<Exposition> actual = expositionService.showAll(2, 3);

        verify(expositionRepository).findAll(any(PageRequest.class));
        assertThat(actual, is(emptyList()));
    }

    @Test
    public void showAllShouldReturnExpositionList() {
        when(expositionRepository.findAll(any(PageRequest.class))).thenReturn(EXPOSITIONS);
        when(expositionMapper.mapExpositionEntityToExposition(any(ExpositionEntity.class))).thenReturn(EXPOSITION);

        final List<Exposition> actual = expositionService.showAll(2, 3);

        verify(expositionRepository).findAll(any(PageRequest.class));
        assertThat(actual, is(not(emptyList())));
        assertThat(actual, hasItem(EXPOSITION));
    }

    @Test
    public void showAllNotFinishedShouldReturnEmptyList() {
        when(expositionRepository.findAllWhereEndDateGreaterThanNow(any(PageRequest.class))).thenReturn(Page.empty());

        final List<Exposition> actual = expositionService.showAllNotFinished(2, 3);

        verify(expositionRepository).findAllWhereEndDateGreaterThanNow(any(PageRequest.class));
        assertThat(actual, is(emptyList()));
    }

    @Test
    public void showAllNotFinishedShouldReturnExpositionList() {
        when(expositionRepository.findAllWhereEndDateGreaterThanNow(any(PageRequest.class))).thenReturn(EXPOSITIONS);
        when(expositionMapper.mapExpositionEntityToExposition(any(ExpositionEntity.class))).thenReturn(EXPOSITION);

        final List<Exposition> actual = expositionService.showAllNotFinished(2, 3);

        verify(expositionRepository).findAllWhereEndDateGreaterThanNow(any(PageRequest.class));
        assertThat(actual, is(not(emptyList())));
        assertThat(actual, hasItem(EXPOSITION));
    }

    @Test
    public void showEntriesAmountShouldReturnEntriesAmount() {
        when(expositionRepository.count()).thenReturn(4L);

        final Long actual = expositionService.showEntriesAmount();

        assertThat(actual, is(4L));
    }

    @Test
    public void showNotFinishedEntriesAmountShouldReturnEntriesAmount() {
        when(expositionRepository.countByEndDateGreaterThan(any(LocalDate.class))).thenReturn(4L);

        final Long actual = expositionService.showNotFinishedEntriesAmount();

        assertThat(actual, is(4L));
    }
}