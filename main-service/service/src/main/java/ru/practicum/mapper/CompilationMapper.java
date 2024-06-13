package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.model.dto.NewCompilationRequestDto;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;

import java.util.Collections;
import java.util.List;

@Component
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationRequestDto dto, List<Event> eventList) {
        return Compilation.builder()
                .events(eventList != null ? eventList : Collections.emptyList())
                .pinned(dto.getPinned() != null ? dto.getPinned() : false)
                .title(dto.getTitle())
                .build();
    }
}
