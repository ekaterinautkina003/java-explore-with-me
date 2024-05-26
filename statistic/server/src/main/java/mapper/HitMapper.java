package mapper;

import model.Hit;
import model.HitDto;
import org.springframework.stereotype.Component;

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
