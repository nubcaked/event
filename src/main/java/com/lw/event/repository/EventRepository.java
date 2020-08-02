package com.lw.event.repository;

import com.lw.event.dto.EventDTO;
import com.lw.event.service.EventService;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.DefaultResourceList;
import io.crnk.core.resource.list.ResourceList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EventRepository extends ResourceRepositoryBase<EventDTO, Long> {

    @Autowired
    EventService eventService;

//    private static final AtomicLong ID_GENERATOR = new AtomicLong(124);

//    private Map<String, EventDTO> events = new HashMap<>();

    public EventRepository() {
        super(EventDTO.class);
    }

//    @Override
//    public synchronized void delete(Long id) {
//        events.remove(id);
//    }

//    @Override
//    public synchronized <S extends EventDTO> S save(S event) {
//        if (event.getId() == null) {
//            event.setId(ID_GENERATOR.getAndIncrement());
//        }
//        events.put(event.getId(), event);
//        return event;
//    }

    @Override
    public synchronized ResourceList<EventDTO> findAll(QuerySpec querySpec) {
        log.debug("this endpoint is hit");
        ResourceList<EventDTO> eventDTOResourceList = new DefaultResourceList<>();
        eventDTOResourceList.addAll(eventService.getEvents(querySpec));
//        querySpec.apply(eventService.getEvents());
        return eventDTOResourceList;
    }

}
