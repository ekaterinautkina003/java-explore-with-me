package ru.practicum.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.dto.CompilationDto;
import ru.practicum.model.dto.NewCompilationRequestDto;
import ru.practicum.model.dto.UpdateCompilationRequestDto;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CompilationService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.model.ApplicationConstant.COMPILATION_NAME;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto save(NewCompilationRequestDto body) {
        List<Event> events = null;
        if (body.getEvents() != null && !body.getEvents().isEmpty()) {
            events = eventRepository.findAllById(body.getEvents());
        }

        Compilation result = compilationRepository.save(compilationMapper.toCompilation(body, events));
        return modelMapper.map(result, CompilationDto.class);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationRequestDto body) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));

        if (body.getPinned() != null && body.getPinned() != compilation.getPinned()) {
            compilation.setPinned(body.getPinned());
        }
        if (body.getTitle() != null && !body.getTitle().equals(compilation.getTitle())) {
            compilation.setTitle(body.getTitle());
        }
        if (isEventsUpdateRequired(compilation, body)) {
            List<Event> events = eventRepository.findAllById(body.getEvents());
            compilation.setEvents(events);
        }

        Compilation saved = compilationRepository.saveAndFlush(compilation);
        return modelMapper.map(saved, CompilationDto.class);
    }

    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> findAllWithParams(Boolean pinned, Integer from, Integer size) {
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size)).stream()
                    .map(it -> modelMapper.map(it, CompilationDto.class))
                    .collect(Collectors.toList());
        }
        return compilationRepository.findAll(PageRequest.of(from, size)).stream()
                .map(it -> modelMapper.map(it, CompilationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
        return modelMapper.map(compilation, CompilationDto.class);
    }

    private boolean isEventsUpdateRequired(Compilation compilation,
                                           UpdateCompilationRequestDto body) {
        return body.getEvents() != null && !body.getEvents().stream().sorted()
                .equals(compilation.getEvents().stream()
                        .map(Event::getId)
                        .sorted()
                        .collect(Collectors.toList()));
    }
}
