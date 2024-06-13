package ru.practicum.stats.mapper;

import ru.practicum.model.HitDto;
import ru.practicum.stats.entity.Hit;

public class EntityMapper {

    public static Hit toHit(HitDto dto) {
        return Hit.builder()
                .app(dto.getApp())
                .ip(dto.getIp())
                .uri(dto.getUri())
                .timestamp(dto.getRequestDateTime())
                .build();
    }
}
