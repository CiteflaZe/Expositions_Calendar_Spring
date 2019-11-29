package com.project.calendar.converter;

import com.project.calendar.domain.Hall;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HallConverter implements Converter<String, Hall> {

    private final HallService hallService;

    @Override
    public Hall convert(String id) {
        final long parseId = Long.parseLong(id);
        try {
            return hallService.showById(parseId);
        } catch (InvalidEntityException e) {
            return null;
        }
    }
}
