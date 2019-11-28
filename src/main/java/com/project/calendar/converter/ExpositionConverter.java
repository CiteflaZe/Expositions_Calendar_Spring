package com.project.calendar.converter;

import com.project.calendar.domain.Exposition;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.service.ExpositionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExpositionConverter implements Converter<String, Exposition> {

    private ExpositionService expositionService;

    @Override
    public Exposition convert(String id) {
        final long parseId = Long.parseLong(id);
        try {
            return expositionService.showById(parseId);
        } catch (InvalidEntityException e) {
            return null;
        }
    }
}
