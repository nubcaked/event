package com.lw.event.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@JsonApiResource(type = "events")
public class EventDTO {

    @JsonApiId
    private String id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String url;

    @JsonProperty
    private String description;

    @JsonProperty
    private String startTime;

    @JsonProperty
    private String stopTime;

    @JsonProperty
    private String tzId;

    @JsonProperty
    private String tzOlsonPath;

    @JsonProperty
    private String venueId;

    @JsonProperty
    private String venueUrl;

    @JsonProperty
    private String venueName;

    @JsonProperty
    private String venueDisplay;

    @JsonProperty
    private String venueAddress;

    @JsonProperty
    private String cityName;

    @JsonProperty
    private String regionName;

    @JsonProperty
    private String regionAbbr;

    @JsonProperty
    private String postalCode;

    @JsonProperty
    private String countryName;

    @JsonProperty
    private String countryAbbr2;

    @JsonProperty
    private String countryAbbr;

    @JsonProperty
    private String latitude;

    @JsonProperty
    private String longitude;

    @JsonProperty
    private String geocodeType;

    @JsonProperty
    private String allDay;

    @JsonProperty
    private String recurString;

    @JsonProperty
    private String calendarCount;

    @JsonProperty
    private String commentCount;

    @JsonProperty
    private String linkCount;

    @JsonProperty
    private String goingCount;

    @JsonProperty
    private String watchingCount;

    @JsonProperty
    private String created;

    @JsonProperty
    private String owner;

    @JsonProperty
    private String modified;

    @JsonProperty
    private String performers;

    @JsonProperty
    private String privacy;

    @JsonProperty
    private String calendars;

    @JsonProperty
    private String groups;

    @JsonProperty
    private String going;

    @JsonProperty
    private String location;

    @JsonProperty
    private String category;

    @JsonProperty
    private String date;
}
