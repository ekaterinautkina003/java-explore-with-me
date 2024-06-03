package ru.practicum.stats.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.model.HitDto;
import ru.practicum.stats.entity.Hit;

@Component
public class HitMapper {

    public Hit toHit(HitDto dto) {
        return Hit.builder()
                .app(dto.getApp())
                .ip(dto.getIp())
                .uri(dto.getUri())
                .timestamp(dto.getRequestDateTime())
                .build();
    }
}
