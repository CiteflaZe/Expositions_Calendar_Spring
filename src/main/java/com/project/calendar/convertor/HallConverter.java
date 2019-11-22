package com.project.calendar.convertor;

import com.project.calendar.domain.Hall;
import com.project.calendar.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HallConverter implements Converter<String, Hall> {

    HallService hallService;

    @Override
    public Hall convert(String id) {
        final long parseId = Long.parseLong(id);
        return hallService.showById(parseId);
    }
}
