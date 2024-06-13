package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class LocationDto {

    @Positive
    private Double lat;

    @Positive
    private Double lon;
}
