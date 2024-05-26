package service;

import lombok.RequiredArgsConstructor;
import mapper.HitMapper;
import model.HitDto;
import model.StatisticDto;
import org.springframework.stereotype.Service;
import repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitMapper hitMapper;
    private final HitRepository hitRepository;

    @Override
    public void save(HitDto body) {
        hitRepository.save(hitMapper.toHit(body));
    }

    @Override
    public List<StatisticDto> get(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    ) {
        if (uris != null
                && !uris.isEmpty()
        ) {
            if (unique) {
                return hitRepository.findDistinctHitsByUris(start, end, uris);
            }
            return hitRepository.findHitsByUris(start, end, uris);
        }
        if (unique) {
            return hitRepository.findDistinctHits(start, end);
        }
        return hitRepository.findHits(start, end);
    }
}
