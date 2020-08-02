package com.lw.event.mapping;

import com.lw.event.Search;
import com.lw.event.dto.EventDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapping extends ConfigurableMapper {
    protected void configure(MapperFactory factory) {
        factory.classMap(Search.Events.Event.class, EventDTO.class)
                .byDefault()
                .register();
    }
}
