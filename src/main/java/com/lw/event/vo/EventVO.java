package com.lw.event.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventVO {
    private String id;
    private String title;
    private String url;
    private String description;
    private String startTime;
    private String stopTime;
    private String tzId;
    private String tzOlsonPath;
    private String venueId;
    private String venueUrl;
    private String venueName;
    private String venueDisplay;
    private String venueAddress;
    private String cityName;
    private String regionName;
    private String regionAbbr;
    private String postalCode;
    private String countryName;
    private String countryAbbr2;
    private String countryAbbr;
    private String latitude;
    private String longitude;
    private String geocodeType;
    private String allDay;
    private String recurString;
    private String calendarCount;
    private String commentCount;
    private String linkCount;
    private String goingCount;
    private String watchingCount;
    private String created;
    private String owner;
    private String modified;
    private String performers;
    private String privacy;
    private String calendars;
    private String groups;
    private String going;
}
