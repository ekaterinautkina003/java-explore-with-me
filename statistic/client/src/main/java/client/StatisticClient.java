package client;

import model.HitDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class StatisticClient extends Client {

    private static final String URL = "http://localhost:9090";

    public StatisticClient(RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(URL))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> hit(
            String app,
            String uri,
            String ip,
            LocalDateTime requestDateTime
    ) {
        HitDto body = new HitDto(app, uri, ip, requestDateTime);
        return request(HttpMethod.POST, "/hit", null, body);
    }

    public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Map<String, Object> parameters = Map.of(
                "start", startDateTime,
                "end", endDateTime
        );
        return request(HttpMethod.GET, "/stats?start={start}&end={end}", parameters, null);
    }

    public ResponseEntity<Object> getStats(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            List<String> uris
    ) {
        Map<String, Object> parameters = Map.of(
                "start", startDateTime,
                "end", endDateTime,
                "uris", uris
        );
        return request(HttpMethod.GET, "/stats?start={start}&end={end}&uris={uris}", parameters, null);
    }

    public ResponseEntity<Object> getStats(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            List<String> uris, boolean unique
    ) {
        Map<String, Object> parameters = Map.of(
                "start", startDateTime,
                "end", endDateTime,
                "uris", uris,
                "unique", unique
        );
        return request(
                HttpMethod.GET,
                "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                parameters ,
                null
        );
    }

    public ResponseEntity<Object> getStats(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            boolean unique
    ) {
        Map<String, Object> parameters = Map.of(
                "start", startDateTime,
                "end", endDateTime,
                "unique", unique
        );
        return request(HttpMethod.GET, "/stats?start={start}&end={end}&unique={unique}", parameters, null);
    }
}
