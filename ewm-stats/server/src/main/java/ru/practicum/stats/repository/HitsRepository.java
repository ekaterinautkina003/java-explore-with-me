package ru.practicum.stats.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.HitInfoDto;
import ru.practicum.stats.entity.Hit;

@Repository
public interface HitsRepository extends JpaRepository<Hit, Long> {

    @Query(value = "select h.app, h.uri, count(h.*) as hits from hits h "
            + "where timestamp between :start and :end "
            + "group by h.uri, h.app "
            + "order by count(h.*) desc", nativeQuery = true)
    List<HitInfoDto> findHits(
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end
    );

    @Query(value = "select h.app, h.uri, count(distinct h.ip) as hits from hits h "
            + "where timestamp between :start and :end "
            + "group by h.uri, h.app "
            + "order by count(distinct h.ip) desc", nativeQuery = true)
    List<HitInfoDto> findDistinctHits(
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end
    );

    @Query(value = "select h.app, h.uri, count(distinct h.ip) as hits from hits h "
            + "where timestamp between :start and :end "
            + "and h.uri in (:uris) "
            + "group by h.uri, h.app "
            + "order by count(distinct h.ip)", nativeQuery = true)
    List<HitInfoDto> findDistinctHitsByUris(
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end,
            @Param(value = "uris") List<String> uris
    );

    @Query(value = "select h.app, h.uri, count(h.*) as hits from hits h "
            + "where timestamp between :start and :end "
            + "and h.uri in (:uris) "
            + "group by h.uri, h.app "
            + "order by count(h.*) desc", nativeQuery = true)
    List<HitInfoDto> findHitsByUris(
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end,
            @Param(value = "uris") List<String> uris
    );
}
