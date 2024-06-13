package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class UpdateCompilationRequestDto {

    private List<Long> events;
    private Boolean pinned;

    @Length(min = 1, max = 50)
    private String title;
}
