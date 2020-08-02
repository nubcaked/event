package com.lw.event;

import com.lw.event.gateway.EventfulGateway;
import com.lw.event.helper.EventUtil;
import com.lw.event.mapping.EventMapping;
import com.lw.event.repository.EventRepository;
import com.lw.event.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EventApplicationTests {

	@Autowired
	EventRepository eventRepository;
	@Autowired
	EventService eventService;
	@Autowired
	EventfulGateway eventfulGateway;
	@Autowired
	EventMapping eventMapping;
	@Autowired
	EventUtil eventUtil;

	@Test
	public void contextLoads() {
	    assertThat(eventRepository).isNotNull();
	}

}
