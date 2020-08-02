//package com.lw.event.mapping;
//
//import com.lw.event.Search;
//import com.lw.event.Search.Events.Event;
//import com.lw.event.dto.EventDTO;
//import lombok.extern.slf4j.Slf4j;
//import ma.glasnost.orika.CustomMapper;
//import ma.glasnost.orika.MappingContext;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//@Slf4j
//public class EventCustomMapper extends CustomMapper<Event, EventDTO> {
//
//    @Override
//    public void mapAtoB(Event event, EventDTO eventDTO, MappingContext context) {
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        try {
//            eventDTO.setStartTime(dateFormatter.parse(event.getStartTime()));
////            eventDTO.setStopTime(dateFormatter.parse(event.getStopTime()));
//        } catch (ParseException e) {
//            log.error("[EventCustomMapper] {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void mapBtoA(EventDTO eventDTO, Event event, MappingContext context) {
//        event.setStartTime(eventDTO.getStartTime().toString());
//    }
//}
