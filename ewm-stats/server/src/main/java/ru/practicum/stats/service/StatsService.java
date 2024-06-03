package ru.practicum.stats.service;

import ru.practicum.model.HitDto;
import ru.practicum.model.HitInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void saveHit(HitDto body);

    List<HitInfoDto> findHitsByParams(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    );
}
