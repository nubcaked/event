package com.lw.event.service;

import com.lw.event.Search;
import com.lw.event.Search.Events.Event;
import com.lw.event.dto.EventDTO;
import com.lw.event.exception.MultipleLocationException;
import com.lw.event.gateway.EventfulGateway;
import io.crnk.core.queryspec.QuerySpec;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.lw.event.helper.EventConstant.*;
import static com.lw.event.helper.EventUtil.getFilterMap;
import static com.lw.event.helper.EventUtil.getSortList;

@Slf4j
@Service
public class EventService {

    @Autowired
    EventfulGateway eventfulGateway;

    @Autowired
    MapperFacade mapper;

    public List<EventDTO> getEvents(QuerySpec querySpec) {
        List<EventDTO> eventDTOs = new ArrayList<>();

        Map<String, List<String>> filterMap = getFilterMap(querySpec);
        List<List<String>> sortList = getSortList(querySpec);

        if (filterMap.get(EVENTLOCATION).size() > 1) {
            throw new MultipleLocationException("Please provide only one location.");
        }

        String location = filterMap.containsKey(EVENTLOCATION) ? filterMap.get(EVENTLOCATION).get(0) : "London";
        String date = filterMap.containsKey(EVENTDATE) ? filterMap.get(EVENTDATE).get(0) : "Future";

        log.info("Getting events for: {}", location);
        if (filterMap.containsKey(EVENTCATEGORY)) { // one call for each category for classification
            filterMap.get(EVENTCATEGORY).parallelStream().forEach(category -> {
                MultiValueMap queryParamMap = new LinkedMultiValueMap();
                queryParamMap.set("app_key", "SBX2Rc87dLFCLjw4");
                queryParamMap.set(EVENTLOCATION, location);
                queryParamMap.set(EVENTDATE, date);
                queryParamMap.set(EVENTCATEGORY, category);
                log.debug("Firing... Event: {}...", queryParamMap.get(EVENTCATEGORY));

                eventfulGateway.getSearches(queryParamMap).forEach(search -> {
                    search.getEvents().getEvent().forEach(event -> {
                        EventDTO eventDTO = mapper.map(event, EventDTO.class);
                        eventDTO.setLocation(location);
                        eventDTO.setDate(date);
                        eventDTO.setCategory(category);
                        eventDTOs.add(eventDTO);
                    });
                });
            });
        } else {
            MultiValueMap queryParamMap = new LinkedMultiValueMap();
            queryParamMap.set("app_key", "SBX2Rc87dLFCLjw4");
            queryParamMap.set(EVENTLOCATION, location);
            queryParamMap.set(EVENTDATE, date);
            eventfulGateway.getSearches(queryParamMap).forEach(search -> {
                search.getEvents().getEvent().forEach(event -> {
                    EventDTO eventDTO = mapper.map(event, EventDTO.class);
                    eventDTO.setLocation(location);
                    eventDTO.setDate(date);
                    eventDTOs.add(eventDTO);
                });
            });
        }

        // TODO: sort the events

        return eventDTOs;
    }
}
