package model;

import lombok.Data;

@Data
public class StatisticDto {
    private String app;
    private String uri;
    private Long hits;
}
