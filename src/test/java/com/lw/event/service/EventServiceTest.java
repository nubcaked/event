package com.lw.event.service;

import com.lw.event.Search;
import com.lw.event.Search.Events.Event;
import com.lw.event.dto.EventDTO;
import com.lw.event.gateway.EventfulGateway;
import com.lw.event.helper.EventConstant;
import com.lw.event.helper.EventUtil;
import io.crnk.core.queryspec.*;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.Filter;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    EventService eventService;
    @Mock
    EventfulGateway eventfulGateway;
    @Mock
    MapperFacade mapperFacade;

    @Test
    void ShouldReturnListOfEventDTO_When_MethodGetEventsCalled() {
        List<EventDTO> eventDTOList = new ArrayList<>();

        QuerySpec querySpec = new QuerySpec("events");
        List<FilterSpec> filters = new ArrayList<>();
        List<SortSpec> sort = new ArrayList<>();
        FilterSpec dateFilter = new FilterSpec(new ArrayList<>(Arrays.asList("date")), FilterOperator.EQ,"2019080100-2020092700");
        FilterSpec categoryFilter = new FilterSpec(new ArrayList<>(Arrays.asList("category")), FilterOperator.EQ, new HashSet<>(Arrays.asList("art", "books", "business", "animals")));
        FilterSpec locationFilter = new FilterSpec(new ArrayList<>(Arrays.asList("location")), FilterOperator.EQ,"London");
        SortSpec categorySort = new SortSpec(PathSpec.of("category"), Direction.ASC);
        SortSpec startTimeSort = new SortSpec(PathSpec.of("startTime"), Direction.DESC);
        filters.add(dateFilter); filters.add(categoryFilter); filters.add(locationFilter);
        sort.add(categorySort); sort.add(startTimeSort);
        querySpec.setFilters(filters);
        querySpec.setSort(sort);


//        Map<String, List<String>> filterMap = new HashMap<>();
//        List<String> locationList = new ArrayList<>();
//        List<String> categoryList = new ArrayList<>();
//        List<String> dateList = new ArrayList<>();
//        locationList.add("London");
//        categoryList.add("animals"); categoryList.add("art"); categoryList.add("books"); categoryList.add("business");
//        dateList.add("2019080100-2020092700");
//        filterMap.put("location", locationList); filterMap.put("category", categoryList); filterMap.put("date", dateList);
//
//        Map<String, String> sortMap = new HashMap<>();
//        sortMap.put("category", "ASC"); sortMap.put("startTime", "DESC");

        List<Search> searchList = new ArrayList<>();
        Search search = new Search();
        Search.Events events = new Search.Events();
        Event event1 = new Event();
        Event event2 = new Event();
        Event event3 = new Event();
        event1.setId("E0-001-134513857-0");
        event1.setTitle("How To Write An Essay");
        event1.setStartTime("2020-08-14 07:00:00");
        event2.setId("E0-001-134464969-9");
        event2.setTitle("Henry Normal - The Escape Plan");
        event2.setStartTime("2020-11-23 19:30:00");
        event3.setId("E0-001-134201152-8");
        event3.setTitle("The London Book Fair - LBF");
        event3.setStartTime("2021-03-09 00:00:00");
        events.getEvent().add(event1); events.getEvent().add(event2); events.getEvent().add(event2);
        search.setEvents(events);
        searchList.add(search);

        EventDTO eventDTO1 = new EventDTO();
        EventDTO eventDTO2 = new EventDTO();
        EventDTO eventDTO3 = new EventDTO();
        eventDTO1.setId("E0-001-134513857-0");
        eventDTO1.setTitle("How To Write An Essay");
        eventDTO1.setStartTime("2020-08-14 07:00:00");
        eventDTO2.setId("E0-001-134464969-9");
        eventDTO2.setTitle("Henry Normal - The Escape Plan");
        eventDTO2.setStartTime("2020-11-23 19:30:00");
        eventDTO3.setId("E0-001-134201152-8");
        eventDTO3.setTitle("The London Book Fair - LBF");
        eventDTO3.setStartTime("2021-03-09 00:00:00");
//        when(mapperFacade.map(event1, EventDTO.class)).thenReturn(eventDTO1);
//        when(mapperFacade.map(event2, EventDTO.class)).thenReturn(eventDTO2);
//        when(mapperFacade.map(event3, EventDTO.class)).thenReturn(eventDTO3);

        when(eventfulGateway.getSearches(any(MultiValueMap.class))).thenReturn(searchList);
        when(mapperFacade.map(any(Event.class), eq(EventDTO.class))).thenReturn(eventDTO1);
        assertThat(eventService.getEvents(querySpec)).isInstanceOf(List.class);
        verify(eventfulGateway, times(4)).getSearches(any(MultiValueMap.class));
    }
}
