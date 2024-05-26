package ru.practicum.stats.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.HitDto;
import ru.practicum.model.HitInfoDto;
import ru.practicum.stats.service.StatsService;

@RestController
@AllArgsConstructor
@Validated
public class StatsController {

  private final StatsService statsService;

  @PostMapping("/hit")
  public ResponseEntity<HitDto> saveHit(@Valid @RequestBody HitDto hitDTO) {
    statsService.saveHit(hitDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/stats")
  public List<HitInfoDto> getStats(
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
      @RequestParam(required = false) List<String> uris,
      @RequestParam(required = false, defaultValue = "false") Boolean unique
  ) {
    return statsService.findHitsByParams(start, end, uris, unique);
  }
}
