package ru.practicum.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.model.dto.*;
import ru.practicum.model.entity.Category;
import ru.practicum.model.entity.Event;

@Component
public class EventMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ModelMapper modelMapper = new ModelMapper();

    public Event toEvent(NewEventDto dto, Category category) throws JsonProcessingException {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .location(objectMapper.writeValueAsString(dto.getLocation()))
                .paid(dto.getPaid() != null ? dto.getPaid() : false)
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : 0)
                .requestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : true)
                .title(dto.getTitle())
                .views(0L)
                .confirmedRequests(0)
                .build();
    }

    public EventFullDto toEventFullDto(Event event) throws JsonProcessingException {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(modelMapper.map(event.getCategory(), CategoryDto.class))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(modelMapper.map(event.getInitiator(), UserDto.class))
                .location(modelMapper.map(objectMapper.readValue(event.getLocation(), LocationDto.class),
                        LocationDto.class))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

}
