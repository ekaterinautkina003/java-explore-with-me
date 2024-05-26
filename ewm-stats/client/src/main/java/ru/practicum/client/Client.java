package ru.practicum.client;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class Client {

  protected final RestTemplate rest;

  public Client(RestTemplate rest) {
    this.rest = rest;
  }

  protected  <T> ResponseEntity<Object> request(
          HttpMethod method, String path,
          @Nullable Map<String, Object> parameters,
          @Nullable T body
  ) {
    HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

    ResponseEntity<Object> response;
    try {
      if (parameters != null) {
        response = rest.exchange(path, method, requestEntity, Object.class, parameters);
      } else {
        response = rest.exchange(path, method, requestEntity, Object.class);
      }
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
    }
    return response(response);
  }

  private HttpHeaders defaultHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    return headers;
  }

  private static ResponseEntity<Object> response(ResponseEntity<Object> response) {
    if (response.getStatusCode().is2xxSuccessful()) {
      return response;
    }

    ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

    if (response.hasBody()) {
      return responseBuilder.body(response.getBody());
    }

    return responseBuilder.build();
  }
}