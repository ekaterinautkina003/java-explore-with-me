package service;

import model.HitDto;
import model.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void save(HitDto body);

    List<StatisticDto> get(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    );
}
