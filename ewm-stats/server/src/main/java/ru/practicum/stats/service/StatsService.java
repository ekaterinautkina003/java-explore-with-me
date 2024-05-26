package ru.practicum.stats.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitInfoDto;

public interface StatsService {

  void saveHit(HitDto body);

  List<HitInfoDto> findHitsByParams(
          LocalDateTime start,
          LocalDateTime end,
          List<String> uris,
          Boolean unique
  );
}
