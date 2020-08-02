package com.lw.event.mapping;

import com.lw.event.Search;
import com.lw.event.Search.Events.Event;
import com.lw.event.dto.EventDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapping extends ConfigurableMapper {
    protected void configure(MapperFactory factory) {
        factory.classMap(Event.class, EventDTO.class)
//                .customize(new EventCustomMapper())
                .byDefault()
                .register();
    }
}
