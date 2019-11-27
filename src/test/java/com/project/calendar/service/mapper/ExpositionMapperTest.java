package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.entity.ExpositionEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExpositionMapperTest {
    private static final Long ID = 5L;
    private static final String TITLE = "Generic Title";
    private static final String THEME = "Theme";
    private static final LocalDate START_DATE = LocalDate.of(2019, 11, 12);
    private static final LocalDate END_DATE = LocalDate.of(2019, 12, 13);
    private static final BigDecimal TICKET_PRICE = BigDecimal.valueOf(28.25);
    private static final String DESCRIPTION = "Description";

    private static final Exposition EXPOSITION = initExposition();
    private static final ExpositionEntity EXPOSITION_ENTITY = initExpositionEntity();

    private ExpositionMapper mapper = new ExpositionMapper();

    @Test
    public void mapExpositionEntityToExpositionShouldReturnExposition() {
        final Exposition actual = mapper.mapExpositionEntityToExposition(EXPOSITION_ENTITY);

        assertThat(actual.getId(), is(ID));
        assertThat(actual.getTitle(), is(TITLE));
        assertThat(actual.getTheme(), is(THEME));
        assertThat(actual.getStartDate(), is(START_DATE));
        assertThat(actual.getEndDate(), is(END_DATE));
        assertThat(actual.getTicketPrice(), is(TICKET_PRICE));
        assertThat(actual.getDescription(), is(DESCRIPTION));
        assertThat(actual.getHall(), is(nullValue()));
    }

    @Test
    public void mapExpositionToExpositionEntity() {
        final ExpositionEntity actual = mapper.mapExpositionToExpositionEntity(EXPOSITION);

        assertThat(actual.getTitle(), is(TITLE));
        assertThat(actual.getTheme(), is(THEME));
        assertThat(actual.getStartTime(), is(START_DATE));
        assertThat(actual.getFinishTime(), is(END_DATE));
        assertThat(actual.getTicketPrice(), is(TICKET_PRICE));
        assertThat(actual.getDescription(), is(DESCRIPTION));
        assertThat(actual.getHall(), is(nullValue()));
    }

    private static Exposition initExposition() {
        return Exposition.builder()
                .title(TITLE)
                .theme(THEME)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .ticketPrice(TICKET_PRICE)
                .description(DESCRIPTION)
                .hall(null)
                .build();
    }

    private static ExpositionEntity initExpositionEntity() {
        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(ID);
        expositionEntity.setTitle(TITLE);
        expositionEntity.setTheme(THEME);
        expositionEntity.setStartTime(START_DATE);
        expositionEntity.setFinishTime(END_DATE);
        expositionEntity.setTicketPrice(TICKET_PRICE);
        expositionEntity.setDescription(DESCRIPTION);
        expositionEntity.setHall(null);

        return expositionEntity;
    }

}