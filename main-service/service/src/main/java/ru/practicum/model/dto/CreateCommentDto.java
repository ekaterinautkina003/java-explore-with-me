package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateCommentDto {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String text;
}
