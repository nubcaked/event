package com.lw.event.repository;

import com.lw.event.dto.EventDTO;
import com.lw.event.service.EventService;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.resource.list.DefaultResourceList;
import io.crnk.core.resource.list.ResourceList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class EventRepositoryTest {

    @InjectMocks
    private EventRepository eventRepository;
    @Mock
    private EventService eventService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ShouldReturnResourceList_When_MethodFindAllIsCalled() {
        List<EventDTO> eventDTOs = new ArrayList<>();
        when(eventService.getEvents(any(QuerySpec.class))).thenReturn(eventDTOs);
        assertThat(eventRepository.findAll(new QuerySpec("events"))).isInstanceOf(ResourceList.class);
        verify(eventService, times(1)).getEvents(any(QuerySpec.class));
    }
}
