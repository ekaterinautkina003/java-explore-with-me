package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategoryDto {

    Long id;
    @NotBlank
    @Length(min = 1, max = 50)
    String name;
}
